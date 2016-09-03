package com.jd.meter.sync.pkg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jd.meter.sync.handler.MetterDispatcherHander;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


@Service
public class MetterServer {

	private String serverIp = "*";
	private boolean running = false;
	@Value("$(metter.server.port:9101)")
    private int port = 9101;
	
 

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // (6)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                            		new DelimiterBasedFrameDecoder(PackageBasic.maxPackageLength, Unpooled.copyInt(PackageBasic.delimiter) )
                            		);
                            ch.pipeline().addLast(new MetterDispatcherHander());
                        }
                    });
 
            ChannelFuture f = b.bind(port).sync();
 
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
    	MetterServer server = new MetterServer();
        if (args.length > 0) {
        	int port = Integer.parseInt(args[0]);
        	if(port > 1024){
        		server.setPort(port);
        	}
        }
        new MetterServer().run();
    }

    public void initAndStart(){
    	
    }
    
    
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
