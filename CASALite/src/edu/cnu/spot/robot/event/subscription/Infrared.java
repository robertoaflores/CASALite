package edu.cnu.spot.robot.event.subscription;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.message.MapMessage;

public class Infrared extends RobotSubscription {
	private int reading;
	private int before;

	public Infrared(MessageAgent anAgent, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( anAgent, aRobot, aMessage, aContent );
	}

	protected void onState() {
		reading = robot.getInfraredByte();

		if (reading != before) {
			before   = reading;
			message.set( "readings", "( :infrared " + reading + " )" );
			agent  .queueMessage( message );			
		}
	}
}
