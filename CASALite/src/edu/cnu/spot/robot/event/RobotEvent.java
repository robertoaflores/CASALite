package edu.cnu.spot.robot.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.event.Event;
import edu.cnu.casaLite.message.MapMessage;

public abstract class RobotEvent extends Event {
	protected final MessageAgent agent;
	protected final IRobotCreate robot;
	protected final MapMessage   message;
	protected final MapMessage   content;
	
	public RobotEvent(MessageAgent anAgent, boolean asynchronous, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( asynchronous );
		agent   = anAgent;
		robot   = aRobot;
		message = aMessage;
		content = aContent;
	}
	protected void onExit() {
		message.set( "performative", "done" );
		agent  .queueMessage( message );
	}
}
