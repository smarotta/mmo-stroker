package com.sm.gwid.command;

import com.sm.gwid.command.to.Command;
import com.sm.gwid.command.to.CommandFactory;

import io.netty.buffer.ByteBuf;

public class CommandDecoder {

	public static boolean isReadyToDecode(ByteBuf buffer) {
		if (buffer != null && buffer.readableBytes() > 1) {
			int numberOfBytesDescribingLength = getNumberOfBytesDescribingLength(buffer);
			if (buffer.readableBytes() >= 1 + numberOfBytesDescribingLength + 1) {
				int length = getPackageSize(buffer, numberOfBytesDescribingLength);
				return buffer.readableBytes() >= length;
			}
		}
		return false;
	}
	
	public static Command decode(ByteBuf buffer) {
		//read to a byte array
		byte [] bucket = readPackageFromBuffer(buffer);
		
		//read the type
		Command.Type type = Command.Type.getTypeForValue(bucket[0]);
		
		//call the factory with the actual type to give a hint to it of wth this package is about
		return CommandFactory.deserializeCommand(type, bucket);
	}
	
	private static int getNumberOfBytesDescribingLength(ByteBuf buffer) {
		return (int) Math.round(Math.pow(2, (buffer.getUnsignedByte(0) - 0xC0)));
	}
	
	private static byte [] readPackageFromBuffer(ByteBuf buffer) {
		int length = getPackageSize(buffer, getNumberOfBytesDescribingLength(buffer));
		byte [] array = new byte [length];
		buffer.readBytes(array);
		return array;
	}	
	
	private static int getPackageSize(ByteBuf buffer, int numberOfBytesDescribingLength) {
		int size = 0;
		for(int i=0; i < numberOfBytesDescribingLength; i++) {
			int shift = 8 * (numberOfBytesDescribingLength - 1 - i);
			size += buffer.getByte(1 + i) << shift;
		}
		return size;
	}
}
