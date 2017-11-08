package com.sm.gwid.desktop;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

import com.sm.gwid.command.CommandDecoder;
import com.sm.gwid.command.CommandEncoder;
import com.sm.gwid.command.to.Command;

public class RoomClientDecoder extends ByteToMessageCodec<Command>{
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (CommandDecoder.isReadyToDecode(in)) {
			out.add(CommandDecoder.decode(in));
		}
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Command msg, ByteBuf out) throws Exception {
		out.writeBytes(CommandEncoder.encode(msg));
	}
	
}
