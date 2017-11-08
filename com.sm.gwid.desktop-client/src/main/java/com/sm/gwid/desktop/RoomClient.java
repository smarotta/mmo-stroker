package com.sm.gwid.desktop;

import java.awt.Color;

import javax.swing.JFrame;

import com.sm.gwid.desktop.ui.DrawingPanel;
import com.sm.gwid.domain.Sketch;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RoomClient implements RoomClientHandler.Listener {
	
	private final RoomClientHandler handler = new RoomClientHandler(this);
	private final RoomClientDecoder decoder = new RoomClientDecoder();
	
	private JFrame frame;
	private DrawingPanel panel;
	
	public RoomClient() {
		panel = new DrawingPanel(handler);
		panel.setBounds(0, 0, 300, 300);
		panel.setBackground(Color.gray);
		panel.addMouseMotionListener(panel);
		
		frame = new JFrame();
		frame.add(panel);
		frame.setVisible(true); 
		frame.pack();
		frame.setBounds(0, 0, 350, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void startClient(String ip, int port) throws InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
        	Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(decoder, handler);
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(ip, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
	}
	
	public void onRemoteSketch(Sketch sketch) {
		System.out.println("Adding REMOTE sketch to panel!");
		panel.addSketch(sketch);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}

	public void onLocalSketch(Sketch sketch) {
		panel.addSketch(sketch);
		frame.getContentPane().validate();
		frame.getContentPane().repaint();
	}
	
	public static void main(String [] args) throws Exception {
		new RoomClient().startClient("127.0.0.1", 60001);
	}
}
