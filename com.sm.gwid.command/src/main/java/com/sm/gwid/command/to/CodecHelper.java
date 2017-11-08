package com.sm.gwid.command.to;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.UUID;

public class CodecHelper {
	
	private final static int LONG_SIZE = Long.SIZE / 8;
	private final static int INT_SIZE = Integer.SIZE / 8;
	private final static int SHORT_SIZE = Short.SIZE / 8;
	public final static Charset CHARSET_UTF8 = Charset.forName("UTF-8");
	
	public static byte [] writeLong(long v) {
		ByteBuffer buffer = ByteBuffer.allocate(LONG_SIZE);
		buffer.putLong(v);
		return buffer.array();
	}
	
	public static void writeLong(long v, byte [] dest, int start) {
		ByteBuffer buffer = ByteBuffer.allocate(LONG_SIZE);
		buffer.putLong(v);
		byte [] data = buffer.array();
		for(int i=0; i < data.length && i + start < dest.length; i++) {
			dest[start + i] = data[i];
		}
	}
	
	public static long readLong(byte [] data) {
		ByteBuffer buffer = ByteBuffer.allocate(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer.getLong();
	}
	
	public static long readLong(byte [] data, int start) {
		ByteBuffer buffer = ByteBuffer.allocate(LONG_SIZE);
		buffer.put(data, start, LONG_SIZE);
		buffer.flip();
		return buffer.getLong();
	}
	
	public static byte [] writeInt(int v) {
		ByteBuffer buffer = ByteBuffer.allocate(INT_SIZE);
		buffer.putInt(v);
		return buffer.array();
	}
	
	public static void writeInt(int v, byte [] dest, int start) {
		ByteBuffer buffer = ByteBuffer.allocate(INT_SIZE);
		buffer.putInt(v);
		byte [] data = buffer.array();
		for(int i=0; i < data.length && i + start < dest.length; i++) {
			dest[start + i] = data[i];
		}
	}
	
	public static int readInt(byte [] data) {
		ByteBuffer buffer = ByteBuffer.allocate(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer.getInt();
	}
	
	public static int readInt(byte [] data, int start) {
		ByteBuffer buffer = ByteBuffer.allocate(INT_SIZE);
		buffer.put(data, start, INT_SIZE);
		buffer.flip();
		return buffer.getInt();
	}
	
	public static byte [] writeShort(short v) {
		ByteBuffer buffer = ByteBuffer.allocate(SHORT_SIZE);
		buffer.putShort(v);
		return buffer.array();
	}
	
	public static void writeShort(short v, byte [] dest, int start) {
		ByteBuffer buffer = ByteBuffer.allocate(SHORT_SIZE);
		buffer.putShort(v);
		byte [] data = buffer.array();
		for(int i=0; i < data.length && i + start < dest.length; i++) {
			dest[start + i] = data[i];
		}
	}
	
	public static int readShort(byte [] data) {
		ByteBuffer buffer = ByteBuffer.allocate(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer.getShort();
	}
	
	public static short readShort(byte [] data, int start) {
		ByteBuffer buffer = ByteBuffer.allocate(SHORT_SIZE);
		buffer.put(data, start, SHORT_SIZE);
		buffer.flip();
		return buffer.getShort();
	}
	
	public static void writeUUID(UUID uuid, byte [] dest) {
		long msb = uuid.getMostSignificantBits();
	    long lsb = uuid.getLeastSignificantBits();
	   
	    for (int i = 0; i < 8; i++) {
	    	dest[i] = (byte) (msb >>> 8 * (7 - i));
	    }
	    
	    for (int i = 8; i < 16; i++) {
	    	dest[i] = (byte) (lsb >>> 8 * (7 - i));
	    }
	}
	
	public static void writeUUID(UUID uuid, byte [] dest, int start) {
		long msb = uuid.getMostSignificantBits();
	    long lsb = uuid.getLeastSignificantBits();
	   
	    for (int i = 0; i < 8; i++) {
	    	dest[start + i] = (byte) (msb >>> 8 * (7 - i));
	    }
	    
	    for (int i = 8; i < 16; i++) {
	    	dest[start + i] = (byte) (lsb >>> 8 * (7 - i));
	    }
	}
	
	public static UUID readUUID(byte [] data) {
		long msb = CodecHelper.readLong(data, 0);
		long lsb = CodecHelper.readLong(data, 8);
		
		return new UUID(msb, lsb);
	}
	
	public static UUID readUUID(byte [] data, int start) {
		long msb = CodecHelper.readLong(data, start);
		long lsb = CodecHelper.readLong(data, start + 8);
		
		return new UUID(msb, lsb);
	}
	
	public static byte [] writeStringUTF8(String v) {
		return v.getBytes(CHARSET_UTF8);
	}
	
	public static void writeStringUTF8(String v, byte [] dest, int start) {
		byte [] data = writeStringUTF8(v);
		for(int i=0; i < data.length && i + start < dest.length; i++) {
			dest[start + i] = data[i];
		}
	}
	
	public static String readStringUTF8(byte [] data) {
		return new String(data, CHARSET_UTF8);
	}
	
	public static String readStringUTF8(byte [] data, int start, int length) {
		return new String(data, start, length, CHARSET_UTF8);
	}
}
