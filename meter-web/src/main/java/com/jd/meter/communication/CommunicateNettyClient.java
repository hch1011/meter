package com.jd.meter.communication;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by hujintao on 2016/8/6.
 */

public class CommunicateNettyClient implements InitializingBean {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Integer port;

    private String host;

    private String threadNum;

    private static Channel channel;


    public Channel getChannel() {
        return channel;
    }


    private CommunicateNettyClient(Integer port, String host, String threadNum) {
        this.port = port;
        this.host = host;
        this.threadNum = threadNum;
    }

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new CommunicateChannelInitializer(Integer.parseInt(threadNum)));

            ChannelFuture channelFuture = b.connect(host, port).sync();

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    logger.info("the connect is successful");
                }
            });

            channel = channelFuture.channel();

            channel.closeFuture().sync();
        } catch (Exception e) {
            logger.warn("get channel wrong");
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //run();
    }

    public static void main(String[] args) {
        CommunicateNettyClient communicateNettyClient = new CommunicateNettyClient(9101, "192.168.1.108", "5");
        communicateNettyClient.run();
    }
}
