package edu.cnu.spot.robot.event.subscription;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.event.SPOTSubscription;

public abstract class RobotSubscription extends SPOTSubscription {
	protected final IRobotCreate robot;

	public RobotSubscription(SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( aSPOT, aMessage, aContent );
		robot = aRobot;
	}
}
