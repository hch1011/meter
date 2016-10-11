package com.jd.meter.sync.server;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jd.meter.sync.handler.MeterDecoder;
import com.jd.meter.sync.handler.MeterEncoder;
import com.jd.meter.sync.handler.MetterDispatcherHander;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


//@Service
public class MeterServer {
	private static Logger LOGGER = LoggerFactory.getLogger(MeterServer.class);
    @Autowired
    MetterDispatcherHander metterDispatcherHander;

	//private String serverIp = "*";
	private boolean running = false;
	@Value("${meter.centerServer.syncdata.port:9101}")
	public int port = 9101;
	@Value("${meter.centerServer.syncdata:false}")
	public boolean syncdata = false;
	
	
	
	 EventLoopGroup bossGroup;
	 EventLoopGroup workerGroup;
	 
    //@PostConstruct
    public void initAndStart() throws Exception{
    	start();
    }
 
    
    public void start() throws Exception {
    	if(running){
    		return;
    	}
    	running = true;
    	LOGGER.info("metterServer starting...");
        bossGroup = new NioEventLoopGroup(); // (1)
        workerGroup = new NioEventLoopGroup(); 
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
                            ch.pipeline()
                             .addLast(new MeterDecoder())
                             .addLast(new MeterEncoder())
                             .addLast(metterDispatcherHander);
                        }
                    });
            b.bind(port).sync();
            //ChannelFuture f = b.bind(ServerConfig.instence.centerServerPort).sync(); 
            //f.channel().closeFuture().sync();
		} catch (Exception e) {
			shutDown();
			throw e;
		}
        LOGGER.info("metterServer started");
    }
    
    //@PreDestroy
    public void shutDown(){
    	LOGGER.info("metterServer shutDown()");
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
    	MeterServer server = new MeterServer();
        if (args.length > 0) {
        	int port = Integer.parseInt(args[0]);
        	if(port > 1024){
        		server.setPort(port);
        	}
        }
        new MeterServer().start();
    }

    
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
    
}
