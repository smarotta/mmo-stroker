package com.sm.gwid.room;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sm.gwid.command.to.Command;
import com.sm.gwid.room.domain.ConnectedPlayer;

public class RoomServerBroadcaster implements Runnable {

	private static class EnqueuedCommand {
		Set<ConnectedPlayer> target;
		List<Command> commands;
		EnqueuedCommand(Set<ConnectedPlayer> target, List<Command> commands) {
			this.target = target;
			this.commands = commands;
		}
		EnqueuedCommand(ConnectedPlayer target, List<Command> commands) {
			this.target = new HashSet<ConnectedPlayer>();
			this.target.add(target);
			this.commands = commands;
		}
		EnqueuedCommand(Set<ConnectedPlayer> target, Command command) {
			this.target = target;
			this.commands = new ArrayList<Command>();
			this.commands.add(command);
		}
		EnqueuedCommand(ConnectedPlayer target, Command command) {
			this.target = new HashSet<ConnectedPlayer>();
			this.target.add(target);
			this.commands = new ArrayList<Command>();
			this.commands.add(command);
		}
	}
	
	private RoomServerHandler serverHandler;
	
	private BlockingQueue<EnqueuedCommand> queue = new LinkedBlockingQueue<RoomServerBroadcaster.EnqueuedCommand>();
	
	public RoomServerBroadcaster(RoomServerHandler serverHandler) {
		this.serverHandler = serverHandler;
	}
	
	public void run() {
		while(true) {
			EnqueuedCommand command = null;
			
			try {
				command = queue.take();
			} catch (InterruptedException e) {
			}
			
			if (command != null) {
				serverHandler.writeToConnectedPlayers(command.target, command.commands);
			}
		}
	}

	public void addCommandToQueue(Set<ConnectedPlayer> target, List<Command> command) {
		synchronized(queue) {
			queue.add(new EnqueuedCommand(target, command));
		}
	}
	
	public void addCommandToQueue(ConnectedPlayer target, List<Command> command) {
		synchronized(queue) {
			queue.add(new EnqueuedCommand(target, command));
		}
	}
	
	public void addCommandToQueue(Set<ConnectedPlayer> target, Command command) {
		synchronized(queue) {
			queue.add(new EnqueuedCommand(target, command));
		}
	}
	
	public void addCommandToQueue(ConnectedPlayer target, Command command) {
		synchronized(queue) {
			queue.add(new EnqueuedCommand(target, command));
		}
	}
}
