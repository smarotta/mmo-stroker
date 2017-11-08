package com.sm.gwid.command.to;

import com.sm.gwid.command.to.input.SketchCommandInput;
import com.sm.gwid.command.to.output.SketchCommandOutput;

public class CommandFactory {

	public static Command deserializeCommand(Command.Type type, byte [] bucket) {
		Command command = null;
		
		switch(type) {
			case C1:
				command = deserializeLightCommand(bucket);
				break;
				
			case C2:
			case C3:
			default:
				command = new UnrecognizedCommand();
		}
		
		return command;
	}
	
	private static Command deserializeLightCommand(byte [] bucket) {
		Command command = null;
		
		switch(Command.SubType.getTypeForValue(bucket[3])) {
			
			case D0:
				SketchCommandInput sketchCommand = new SketchCommandInput();
				sketchCommand.deserialize(bucket);
				command = sketchCommand;
			break;
			
			case D1:
				SketchCommandOutput sketchCommandOutput = new SketchCommandOutput();
				sketchCommandOutput.deserialize(bucket);
				command = sketchCommandOutput;
			break;
		
			case B0:
			default:
				command = new UnrecognizedCommand();
				break;
		}
		
		return command;
	}
	
	public static byte [] serializeCommand(Command command) {
		return command.serialize();
	}
	
}
