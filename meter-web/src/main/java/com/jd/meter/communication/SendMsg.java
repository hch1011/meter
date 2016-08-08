package com.jd.meter.communication;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hujintao on 2016/8/6.
 */
@Component
public class SendMsg {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CommunicateNettyClient communicateNettyClient;

    public void send(Object msg) {
        final String sendMsg = (String)msg;
        try {
            ByteBuf buf = Unpooled.copiedBuffer(sendMsg.getBytes());
            Channel channel = communicateNettyClient.getChannel();
            ChannelFuture f = channel.writeAndFlush(buf);
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    logger.info("消息发送成功[" + sendMsg + "]");
                }
            });
        } catch (Exception e) {
            logger.warn("消息发送失败[" + sendMsg + "]");
        }
    }
}
