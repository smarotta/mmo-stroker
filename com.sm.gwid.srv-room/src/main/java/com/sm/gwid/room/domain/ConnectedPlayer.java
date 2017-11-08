package com.sm.gwid.room.domain;

import io.netty.channel.ChannelHandlerContext;

import com.sm.gwid.domain.Player;

public class ConnectedPlayer extends Player {
	
	private ChannelHandlerContext context;
	
	public ConnectedPlayer(ChannelHandlerContext context) {
		this.context = context;
	}

	public ChannelHandlerContext getContext() {
		return context;
	}

	public void setContext(ChannelHandlerContext context) {
		this.context = context;
	}
}
