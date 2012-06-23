package edu.cnu.spot.robot.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public class Stop extends RobotEvent {

	public Stop(SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage) {
		super( aSPOT, false, aRobot, aMessage, null );
	}

	protected void onState() {
		robot.stop();
	}
}
