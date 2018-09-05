package com.qs.game.handler;

import com.qs.game.business.BusinessThreadUtil;
import com.qs.game.common.Global;
import com.qs.game.model.base.ReqEntity;
import com.qs.game.model.base.ReqErrEntity;
import com.qs.game.utils.AccessUtils;
import com.qs.game.utils.HandlerUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * binary handler
 *
 */
@Slf4j
@ChannelHandler.Sharable
@Component
@Qualifier("businessHandler")
public class BusinessHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private Global global;

    @Autowired
    private BusinessThreadUtil businessThreadUtil;

//    public BusinessHandler(Global global) {
//        this.global = global;
//    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("BusinessHandler channelRead0 {},{}", ctx, msg);
        String msgText = msg.text();
        ReqEntity reqEntity = AccessUtils.getReqEntity(msgText);
        businessThreadUtil.doBusiness(ctx, msg, reqEntity);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("BusinessHandler ctx = [" + ctx + "]");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.warn("Client: {} 异常,{}", incoming.remoteAddress(), cause.toString());
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

}
