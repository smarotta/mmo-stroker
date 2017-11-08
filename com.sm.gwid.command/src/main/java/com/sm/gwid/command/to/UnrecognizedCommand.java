package com.sm.gwid.command.to;

public class UnrecognizedCommand extends Command {

	@Override
	public Command.Type getType() {
		return Type.INVALID;
	}

	@Override
	public Command.SubType getSubType() {
		return SubType.INVALID;
	}

	@Override
	public void deserialize(byte[] packet) {
	}

	@Override
	public byte[] serialize() {
		return null;
	}
	
}
