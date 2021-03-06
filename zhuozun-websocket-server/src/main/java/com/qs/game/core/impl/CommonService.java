package com.qs.game.core.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qs.game.cache.CacheKey;
import com.qs.game.common.ERREnum;
import com.qs.game.common.game.KunGold;
import com.qs.game.common.netty.Global;
import com.qs.game.config.game.GameManager;
import com.qs.game.core.ICommonService;
import com.qs.game.model.base.RespEntity;
import com.qs.game.model.game.*;
import com.qs.game.service.IRedisService;
import com.qs.game.service.IUserKunGoldService;
import com.qs.game.service.IUserKunPoolService;
import com.qs.game.utils.IntUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by zun.wei on 2018/9/10 19:17.
 * Description: 公共业务接口
 */
@Slf4j
@Service
public class CommonService implements ICommonService {


    @Autowired
    private IRedisService redisService;

    @Autowired
    private IUserKunGoldService userKunGoldService;

    @Autowired
    private IUserKunPoolService userKunPoolService;

    @Autowired
    private GameManager gameManager;

    @Autowired
    private Global global;

    @Override
    public Pool updatePoolByKuns(String mid, int cellNo, Kuns updateKuns) {
        //查询玩家鲲池
        Pool pool = this.getPlayerKunPool(mid);
        return this.updateSrcPoolByKuns(mid, pool, cellNo, updateKuns);
    }

    @Override
    public Pool updatePoolByPoolCell(String mid, PoolCell poolCell) {
        return this.updatePoolByKuns(mid, poolCell.getNo(), poolCell.getKuns());
    }

    @Override
    public Pool updatePoolByOldPool(String mid, Pool srcPool, PoolCell poolCell) {
        return this.updateSrcPoolByKuns(mid, srcPool, poolCell.getNo(), poolCell.getKuns());
    }

    @Override
    public Pool updateSrcPoolByKuns(String mid, Pool srcPool, int cellNo, Kuns updateKuns) {
        Kuns kuns = new Kuns();
        BeanUtils.copyProperties(updateKuns, kuns);
        return Optional.ofNullable(srcPool).map(e -> {
            List<PoolCell> poolCells = e.getPoolCells()
                    .stream()
                    .peek(poolCell -> {
                        if (poolCell.getNo() == cellNo)
                            poolCell.setKuns(kuns);
                    }).collect(toList());
            e.setPoolCells(poolCells);
            //把更新后的鲲池保存
            this.savePool2CacheAndMemory(mid, e);
            return e;
        }).orElse(srcPool);
    }

    @Override
    public long getPlayerGold(String mid) {
        String goldKey = CacheKey.RedisPrefix.USER_KUN_GOLD.KEY + mid;
        String goldStr = redisService.get(goldKey);
        return Optional.ofNullable(goldStr)
                .map(Long::parseLong)
                .orElseGet(() -> {
                    UserKunGold userKunGold = userKunGoldService.selectByPrimaryKey(Long.valueOf(mid));
                    return Optional.ofNullable(userKunGold).map(ukg -> {
                        Long gold = ukg.getGold();
                        redisService.set(goldKey, gold);
                        return gold;
                    }).orElse(0L);
                });

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long addPlayerGold(String mid, long addGold) {
        String goldKey = CacheKey.RedisPrefix.USER_KUN_GOLD.KEY + mid;
        if (addGold == 0) {
            String nowGold = redisService.get(goldKey);
            if (StringUtils.isNotBlank(nowGold)) {
                return Long.parseLong(nowGold);
            } else {
                UserKunGold userKunGold = userKunGoldService.selectByMid(Integer.valueOf(mid));
                if (Objects.nonNull(userKunGold) && userKunGold.getGold() > 0L) {
                    return userKunGold.getGold();
                } else {
                    return 0L;
                }
            }
        }
        Long newGold = redisService.incr(goldKey, addGold);
        // 重复索引则更新
        userKunGoldService.insertSelective
                (new UserKunGold().setGold(newGold).setMid(Integer.parseInt(mid)));
        //如果缓存丢失，查询数据库
        if (addGold == newGold) {
            UserKunGold userKunGold = userKunGoldService.selectByMid(Integer.valueOf(mid));
            if (Objects.nonNull(userKunGold) && userKunGold.getGold() > 0L) {
                newGold = userKunGold.getGold();
            }
        }
        return newGold;
    }

    @Override
    public Pool getPlayerKunPool(String mid) {
        //查看内存中是否已经保存了该玩家的鲲池数据下标
        Integer index = gameManager.getUserKunPoolPosition().get(Integer.valueOf(mid));
        return Optional.ofNullable(index)
                //从内存中取出玩家鲲池信息
                .map(e -> {
                    String json = gameManager.getMemoryKunPool(mid, e);
                    return JSONObject.parseObject(json, Pool.class);
                })
                .orElseGet(() -> {
                    String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
                    //缓存中的鲲池
                    String poolJson = redisService.get(kunKey);
                    return Optional.ofNullable(poolJson)
                            .map(e -> JSONObject.parseObject(poolJson, Pool.class))
                            .orElseGet(() -> {
                                //如果缓存中没有数据查询数据库
                                List<PoolCell> poolCells = this.getPoolCellsFromDB(mid);
                                return new Pool().setPoolCells(poolCells);
                            });
                });
    }

    @Override
    public List<PoolCell> getPoolCellsFromDB(String mid) {
        List<UserKunPool> UserKunPools = userKunPoolService.queryListByMid(Integer.valueOf(mid));
        return Optional.ofNullable(UserKunPools)
                .map(j -> {
                    if (j.isEmpty()) {
                        return this.getInitPool(mid).getPoolCells();
                    } else {
                        return j.stream().map(m ->
                                new PoolCell().setNo(m.getPosition())
                                        .setKuns(new Kuns().setTime(m.getRunTime())
                                                .setWork(m.getIsRun()).setType(m.getType())))
                                .collect(toList());
                    }
                }).orElseGet(() -> {
                    Pool initKunPool = this.getInitPool(mid);
                    return initKunPool.getPoolCells();
                });
    }

    //获取初始化鲲池
    @Override
    public Pool getInitPool(String mid) {
        String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
        //如果数据库也不存在则，初始化鲲池
        Pool initKunPool = gameManager.getInitKunPool();
        String initPoolJson = JSONObject.toJSONString(initKunPool, SerializerFeature.DisableCircularReferenceDetect);
        //保存到缓存中
        redisService.set(kunKey, initPoolJson);
        //保存到内存中
        gameManager.storageOnMemory(mid, initPoolJson);
        return initKunPool;
    }

    @Override
    public boolean savePool2CacheAndMemory(String mid, Pool pool) {
        String kunKey = CacheKey.RedisPrefix.USER_KUN_POOL.KEY + mid;
        String poolJson = JSONObject.toJSONString(pool, SerializerFeature.DisableCircularReferenceDetect);
        //保存到缓存中
        boolean b = redisService.set(kunKey, poolJson);
        //保存到内存中
        gameManager.storageOnMemory(mid, poolJson);
        return b;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean persistenceUserKunInfos(String mid) {
        //获取当前的玩家当前的池
        Pool pool = this.getPlayerKunPool(mid);
        List<PoolCell> poolCells = pool.getPoolCells();
        long nowTime = new Date().getTime() / 1000;
        //截止目前为止生产出的金币
        GoldDto goldDto = this.getPeriodTimeAndSrcGold(mid, poolCells, nowTime);

        //更改每只鲲的工作时间
        poolCells = this.getPoolCellsAndResetWorkTime(poolCells, nowTime);

        //持久化到缓存中
        this.savePool2CacheAndMemory(mid, new Pool().setPoolCells(poolCells));

        //持久化到db
        userKunPoolService.insertBatch(mid, poolCells);

        return true;
    }

    @Override
    public List<PoolCell> getPoolCellsAndResetWorkTime(List<PoolCell> poolCells, long nowTime) {
        poolCells = Optional.ofNullable(poolCells)
                //重置工作时间为当前时间
                .map(e -> e.stream().peek(i -> {
                    //只更新存在的类型和正在工作的鲲
                    if (i.getKuns().getType() > 0 && i.getKuns().getWork() > 0) {
                        Kuns kuns = new Kuns();
                        BeanUtils.copyProperties(i, kuns);
                        i.setKuns(kuns.setTime(nowTime));
                    }
                }).collect(toList()))
                .orElseGet(ArrayList::new);
        return poolCells;
    }

    @Override
    public GoldDto getPeriodTimeAndSrcGold(String mid, List<PoolCell> poolCells, long nowTime) {
        long productGold = Optional.ofNullable(poolCells)
                .map(pcs -> pcs.stream().map(PoolCell::getKuns)
                        //筛选类型存在并且在工作的
                        .filter(e -> e.getType() > 0 && e.getWork() > 0)
                        //根据间隔时间和类型计算出没种鲲产的金币数
                        .map(e -> (nowTime - e.getTime()) * KunGold.goldByType(e.getType()))
                        //累加所有类型
                        .reduce((e1, e2) -> e1 + e2).orElse(0L)
                ).orElse(0L);
        //添加金币,并持久化（redis 、 db）
        return new GoldDto().setAddGold(productGold).setNewGold(this.addPlayerGold(mid, productGold));
    }

    @Override
    public long updateGoldByNo(String mid, Pool pool, Integer noIndex) {
        List<PoolCell> poolCells = pool.getPoolCells();
        //截止目前为止生产出的金币
        return updateGoldByNo(mid, poolCells, noIndex);
    }

    @Override
    public long updateGoldByNo(String mid, Integer noIndex) {
        //获取玩家的鲲池
        Pool pool = this.getPlayerKunPool(mid);
        return updateGoldByNo(mid, pool, noIndex);
    }

    @Override
    public long updateGoldByNo(String mid, List<PoolCell> poolCells, Integer noIndex) {
        long nowTime = new Date().getTime() / 1000;
        //截止目前为止生产出的金币
        long productGold = Optional.ofNullable(poolCells)
                .map(pcs -> pcs.stream()
                        .filter(e -> Objects.equals(e.getNo(), noIndex))
                        .map(PoolCell::getKuns)
                        //筛选类型存在并且在工作的
                        .filter(e -> e.getType() > 0 && e.getWork() > 0)
                        //根据间隔时间和类型计算出没种鲲产的金币数
                        .map(e -> (nowTime - e.getTime()) * KunGold.goldByType(e.getType()))
                        //累加所有类型
                        .reduce((e1, e2) -> e1 + e2).orElse(0L)
                ).orElse(0L);
        //添加金币,并持久化（redis 、 db）
        return this.addPlayerGold(mid, productGold);
    }

    @Override
    public Integer getAndCheckKunIndex(Class clzz, Map<String, Object> params, String paramName) {
        String className = clzz.getSimpleName();
        if (Objects.isNull(params)) {
            log.info("{} execute params is null !", className);
            return null;
        }
        String no = Objects.isNull(params.get(paramName)) ? null : params.get(paramName).toString();
        //校验参数是否为空
        if (Objects.isNull(no)) {
            log.info("{} execute {} is null !", className, paramName);
            return null;
        }

        Integer noIndex = IntUtils.str2Int(no);
        if (Objects.isNull(noIndex)) {
            log.info("{} execute noIndex is null !", className);
            return null;
        }

        //判断下标不能小于零
        if (noIndex < 0) {
            log.info("{} execute noIndex < 0 !", className);
            return null;
        }

        //判断下标不能超过鲲池限制大小
        if (noIndex > GameManager.POOL_CELL_NUM - 1) {
            log.info("{} execute noIndex > {} !", className, GameManager.POOL_CELL_NUM - 1);
            return null;
        }
        return noIndex;
    }

    @Override
    public Pool getAndCheckPool(Class clzz, Integer cmd, String mid) {
        Pool pool = this.getPool(this.getClass(), mid);
        if (Objects.isNull(pool)) {
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.ILLEGAL_REQUEST_5).buildJsonStr();
            global.sendMsg2One(resultStr, mid);
            return null;
        }
        return pool;
    }

    @Override
    public Integer getAndCheckRequestNo(Class clzz, String paramName, Integer cmd, String mid, Map<String, Object> params) {
        Integer noIndex = this.getAndCheckKunIndex(clzz.getClass(), params, paramName);
        if (Objects.isNull(noIndex)) {
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.ILLEGAL_REQUEST_4).buildJsonStr();
            global.sendMsg2One(resultStr, mid);
            return null;
        }
        //如果传过来的下标不在范围内
        boolean isIndex = (0 <= noIndex) && (noIndex < GameManager.POOL_CELL_NUM);
        if (!isIndex) {
            String resultStr = RespEntity.getBuilder().setCmd(cmd).setErr(ERREnum.ILLEGAL_REQUEST_6).buildJsonStr();
            global.sendMsg2One(resultStr, mid);
            return null;
        }
        return noIndex;
    }

    @Override
    public Pool getPool(Class clzz, String mid) {
        String className = clzz.getSimpleName();
        Pool pool = this.getPlayerKunPool(mid);
        if (Objects.isNull(pool)) {
            log.info("{} execute pool is null !", className);
            return null;
        }
        if (Objects.isNull(pool.getPoolCells()) || pool.getPoolCells().isEmpty()) {
            log.info("{} execute pool getPoolCells() or empty is null !", className);
            return null;
        }
        if (pool.getPoolCells().size() != GameManager.POOL_CELL_NUM) {
            log.info("{} execute pool getPoolCells() size not equals {} !", className, GameManager.POOL_CELL_NUM);
            return null;
        }
        return pool;
    }

   /* public static void main(String[] args) throws ParseException {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        long nowTime = fastDateFormat.parse("2018-09-10 23:57:01").getTime() / 1000;
        long beginTime = fastDateFormat.parse("2018-09-10 23:57:00").getTime() / 1000;
        System.out.println("nowTime = " + (nowTime - beginTime));

    }*/

}
