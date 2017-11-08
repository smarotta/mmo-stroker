package com.sm.gwid.room;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RoomServer implements Runnable {

	private int port = 60001;
	
	public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	final RoomServerHandler handler = new RoomServerHandler();
        	final RoomServerDecoder decoder = new RoomServerDecoder();
        	
        	ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(decoder, handler);
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
            
        } catch(Exception e) {
        	e.printStackTrace();
        	
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            
        }
    }
	
	public static void main(String [] args) throws Exception {
		Thread st = new Thread(new RoomServer());
		st.start();
		
		while(true) {
			Thread.sleep(60000l);
		}
	}
	
}