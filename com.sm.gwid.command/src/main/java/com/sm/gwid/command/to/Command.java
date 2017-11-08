package com.sm.gwid.command.to;

public abstract class Command {

	public enum Type {
		C1((byte)0xc1), //2 bytes to represent the size of the package (short)
		C2((byte)0xc2), //4 bytes to represent the size of the package (int)
		C3((byte)0xc3), //8 bytes to represent the size of the package (long) NOT SUPPORTED FOR NOW
		
		INVALID((byte)0x00);
		
		private byte value;
		
		public byte getByteValue() {
			return value;
		}
		
		private Type(byte value) {
			this.value = value;
		}
		
		public static Type getTypeForValue(byte value) {
			for(Type type:Type.values()) {
				if (type.getByteValue() == value) {
					return type;
				}
			}
			return Type.INVALID;
		}
	}
	
	public enum SubType {
		//BX = system related (ex: login)
		B0((byte)0xB0), 
		
		//DX = action related 
		D0((byte)0xD0), //sketch input
		D1((byte)0xD1), //sketch output
		
		INVALID((byte)0x00);
		
		private byte value;
		
		public byte getByteValue() {
			return value;
		}
		
		private SubType(byte value) {
			this.value = value;
		}
		
		public static SubType getTypeForValue(byte value) {
			for(SubType type:SubType.values()) {
				if (type.getByteValue() == value) {
					return type;
				}
			}
			return SubType.INVALID;
		}
	}
	
	public abstract Type getType();
	
	public abstract SubType getSubType();
	
	public abstract void deserialize(byte [] packet);
	
	public abstract byte [] serialize();
}
