package edu.cnu.spot.create.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.event.SPOTReply;

public abstract class RobotReply extends SPOTReply {
	protected final IRobotCreate robot;
	
	public RobotReply(boolean asynchronous, SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( asynchronous, aSPOT, aMessage, aContent );
		robot = aRobot;
	}
}
