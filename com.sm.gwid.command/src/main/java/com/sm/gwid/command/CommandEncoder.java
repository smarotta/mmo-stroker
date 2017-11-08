package com.sm.gwid.command;

import com.sm.gwid.command.to.Command;
import com.sm.gwid.command.to.CommandFactory;

public class CommandEncoder {

	public static byte [] encode(Command command) {
		return CommandFactory.serializeCommand(command);
	}

}
