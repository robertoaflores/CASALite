package edu.cnu.spot.robot.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.message.MapMessage;

public class Stop extends RobotEvent {

	public Stop(MessageAgent anAgent, IRobotCreate aRobot, MapMessage aMessage) {
		super( anAgent, false, aRobot, aMessage, null );
	}

	protected void onState() {
		robot.stop();
	}
}
