package edu.cnu.spot.robot.event.subscription;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public class Infrared extends RobotSubscription {
	private int reading;
	private int before;

	public Infrared(SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( aSPOT, aRobot, aMessage, aContent );
	}

	protected void onState() {
		reading = robot.getInfraredByte();

		if (reading != before) {
			before   = reading;
			message.set( "performative", "inform-ref" );
			message.set( "readings",     "( :infrared " + reading + " )" );
			spot   .queueMessage( message );			
		}
	}
}
