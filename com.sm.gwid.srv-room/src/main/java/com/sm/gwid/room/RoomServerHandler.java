package com.sm.gwid.room;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.sm.gwid.command.to.Command;
import com.sm.gwid.command.to.input.SketchCommandInput;
import com.sm.gwid.command.to.output.SketchCommandOutput;
import com.sm.gwid.room.domain.ConnectedPlayer;

@Sharable
public class RoomServerHandler extends ChannelInboundHandlerAdapter {
	
	private static final Map<ChannelHandlerContext, ConnectedPlayer> contextClientMap = new ConcurrentHashMap<ChannelHandlerContext, ConnectedPlayer>();
	
	private RoomServerBroadcaster broadcaster;
	
	public RoomServerHandler() {
		super();
		this.broadcaster = new RoomServerBroadcaster(this);
		new Thread(this.broadcaster).start();
	}
	
	private void removeConnectedPlayer(ChannelHandlerContext ctx) {
		ConnectedPlayer player = contextClientMap.get(ctx);
		if (player != null) {
			contextClientMap.remove(ctx);
		}
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		removeConnectedPlayer(ctx);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		ConnectedPlayer player = new ConnectedPlayer(ctx);
		player.setId(UUID.randomUUID());
		contextClientMap.put(ctx, player);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object objMsg) {
		
		if (objMsg instanceof SketchCommandInput) {
			ConnectedPlayer player = contextClientMap.get(ctx);
			if (player != null) {
				Set<ConnectedPlayer> players = new HashSet<ConnectedPlayer>();
				players.addAll(contextClientMap.values());
				players.remove(player);
								
				SketchCommandInput in = (SketchCommandInput) objMsg;
				SketchCommandOutput out = new SketchCommandOutput();
				out.setPlayerId(player.getId());
				out.setSketch(in.getSketch());
				broadcaster.addCommandToQueue(players, out);
			} else {
				System.err.println("command recieved on an unknown player, ignored!");
			}
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		removeConnectedPlayer(ctx);
	}
	
	public void writeToConnectedPlayers(Set<ConnectedPlayer> players, List<Command> commands) {
		for(ConnectedPlayer player:players) {
			for(Command command:commands) {
				player.getContext().write(command);
			}
			player.getContext().flush();
		}
	}
}
