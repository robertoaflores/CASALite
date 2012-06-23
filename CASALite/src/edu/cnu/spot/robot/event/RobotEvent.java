package edu.cnu.spot.robot.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.event.SPOTEvent;

public abstract class RobotEvent extends SPOTEvent {
	protected final IRobotCreate robot;
	
	public RobotEvent(SPOTAgent aSPOT, boolean asynchronous, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( aSPOT, asynchronous, aMessage, aContent );
		robot = aRobot;
	}
}
