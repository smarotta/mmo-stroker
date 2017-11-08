package com.sm.gwid.desktop;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashSet;
import java.util.Set;

import com.sm.gwid.command.to.Command;
import com.sm.gwid.command.to.input.SketchCommandInput;
import com.sm.gwid.command.to.output.SketchCommandOutput;
import com.sm.gwid.desktop.ui.DrawingPanel;
import com.sm.gwid.domain.Sketch;

public class RoomClientHandler extends ChannelInboundHandlerAdapter implements DrawingPanel.Listener {
	
	public static interface Listener {
		void onRemoteSketch(Sketch sketch);
		void onLocalSketch(Sketch sketch);
	}
	
	private DrawingPanel panel;
	private Listener listener;
	
	private static final Set<ChannelHandlerContext> contexts = new HashSet<ChannelHandlerContext>();

	public RoomClientHandler(Listener listener) {
		this.listener = listener;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		synchronized(contexts) {
			contexts.add(ctx);
		}
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		synchronized(contexts) {
			contexts.remove(ctx);
		}
	}
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		Command command = (Command) msg;
		if (command instanceof SketchCommandOutput) {
			SketchCommandOutput sc = (SketchCommandOutput) command;
			
			if (sc.getSketch() != null) {
				listener.onRemoteSketch(sc.getSketch());
			}
		}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

	public void onNewSketch(Sketch sketch) {
		synchronized(contexts) {
			SketchCommandInput command = new SketchCommandInput();
			command.setSketch(sketch);
			
			for(ChannelHandlerContext ctx:contexts) {
				ctx.write(command);
				ctx.flush();
			}
		}
		
		listener.onLocalSketch(sketch);
	}
}
