package com.jd.meter.communication;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jd.meter.sync.handler.MeterDecoder;
import com.jd.meter.sync.handler.MeterEncoder;

/**
 * Created by hujintao on 2016/8/5.
 */

public class CommunicateChannelInitializer extends ChannelInitializer<SocketChannel> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Integer threadNum;

    private static EventExecutorGroup eventExecutors;

    public CommunicateChannelInitializer(Integer threadNum) {
        if(threadNum > 0) {
            eventExecutors = new DefaultEventExecutorGroup(threadNum);
        }

    }


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast("decode", new MeterDecoder());
        pipeline.addLast("encode", new MeterEncoder());

        if(eventExecutors != null) {
            pipeline.addLast(eventExecutors, "handler", new CommunicateNettyHandler());
        } else {
            pipeline.addLast("handler", new CommunicateNettyHandler());
        }

    }
}
