package com.qs.game.service.impl;

import com.alibaba.fastjson.JSON;
import com.qs.game.constant.StrConst;
import com.qs.game.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *  redis service
 */
@Service
public class RedisService implements IRedisService {


    @Autowired
    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final RedisTemplate redisTemplate;


    @Override
    public long del(final String... keys) {
        RedisCallback<Long> redisCallback = connection -> {
            Long result = 0L;
            for (String key : keys) {
                result += connection.del(key.getBytes());
            }
            return result;
        };
        return (long) redisTemplate.execute(redisCallback);

    }

    @Override
    public boolean set(String key, Object value) {
        String strValue = JSON.toJSONString(value);
        return this.set(key, strValue);
    }

    @Override
    public boolean set(String key, Object value, long liveTime) {
        String strValue = JSON.toJSONString(value);
        return this.set(key, strValue, liveTime);
    }

    @Override
    public boolean set(final byte[] key, final byte[] value, final long liveTime) {
        RedisCallback<Boolean> redisCallback = connection -> {
            Boolean b = connection.set(key, value);
            if (liveTime > 0 && Objects.nonNull(b) && b) {
                connection.expire(key, liveTime);
            }
            return b;
        };
        return (boolean) redisTemplate.execute(redisCallback);
    }

    @Override
    public boolean set(String key, String value, long liveTime) {
        return this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    @Override
    public boolean set(String key, String value) {
        return this.set(key, value, 0L);
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        return this.set(key, value, 0L);
    }

    @Override
    public String get(final String key) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] result = connection.get(key.getBytes());
            try {
                return Objects.isNull(result) ? null : new String(result, StrConst.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new String(result);
            }
        };
        return (String) redisTemplate.execute(redisCallback);
    }


    @Override
    public boolean exists(final String key) {
        RedisCallback<Boolean> redisCallback = connection -> connection.exists(key.getBytes());
        return (boolean) redisTemplate.execute(redisCallback);
    }

    @Override
    public boolean flushDB() {
        RedisCallback<Boolean> redisCallback = connection -> {
            connection.flushDb();
            return true;
        };
        return (boolean) redisTemplate.execute(redisCallback);
    }

    @Override
    public boolean flushAll() {
        RedisCallback<Boolean> redisCallback = connection -> {
            connection.flushAll();
            return true;
        };
        return (boolean) redisTemplate.execute(redisCallback);
    }


    @Override
    public long dbSize() {
        RedisCallback<Long> redisCallback = RedisServerCommands::dbSize;
        return (long) redisTemplate.execute(redisCallback);
    }

    @Override
    public String ping() {
        RedisCallback<String> redisCallback = RedisConnectionCommands::ping;
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public Boolean hSet(String key, String field, String value) {
        RedisCallback<Boolean> redisCallback = connection ->
                connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
        return (Boolean) redisTemplate.execute(redisCallback);
    }


    @Override
    public Boolean hSet(String key, String field, String value, Long liveTime) {
        RedisCallback<Boolean> redisCallback = connection -> {
            Boolean isSuccess = connection.hSet(key.getBytes(), field.getBytes(), value.getBytes());
            if (liveTime > 0) {
                connection.expire(key.getBytes(), liveTime);
            }
            return isSuccess;
        };
        return (Boolean) redisTemplate.execute(redisCallback);
    }

    @Override
    public String hGet(String key, String field) {
        RedisCallback<String> redisCallback = connection -> {
            byte[] result = connection.hGet(key.getBytes(), field.getBytes());
            if (Objects.isNull(result)) return null;
            if (exists(key)) {
                return new String(result);
            } else {
                return null;
            }
        };
        return (String) redisTemplate.execute(redisCallback);
    }

    @Override
    public Map<String, String> hGetAll(String key) {
        RedisCallback<Map<String, String>> redisCallback = connection -> {
            if (exists(key)) {
                Map<byte[], byte[]> map = connection.hGetAll(key.getBytes());
                Map<String, String> resMap = new HashMap<>();
                if (Objects.isNull(map)) return resMap;
                map.forEach((kkey, value) -> resMap.put(new String(kkey), new String(value)));
                return resMap;
            } else {
                return new HashMap<>();
            }
        };
        return (Map<String, String>) redisTemplate.execute(redisCallback);
    }


}