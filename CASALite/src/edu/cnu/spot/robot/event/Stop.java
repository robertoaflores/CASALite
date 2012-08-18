package edu.cnu.spot.robot.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public class Stop extends RobotReply {

	public Stop(SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage) {
		super( false, aSPOT, aRobot, aMessage, null );
	}

	protected void onState() {
		robot.stop();
	}
}
