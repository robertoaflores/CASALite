package edu.cnu.spot.robot.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.event.SPOTReplyEvent;

public abstract class RobotEvent extends SPOTReplyEvent {
	protected final IRobotCreate robot;
	
	public RobotEvent(boolean asynchronous, SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( asynchronous, aSPOT, aMessage, aContent );
		robot = aRobot;
	}
}
