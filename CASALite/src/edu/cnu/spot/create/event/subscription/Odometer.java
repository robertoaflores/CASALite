package edu.cnu.spot.create.event.subscription;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public class Odometer extends RobotSubscription {
	private int accumulated;
	private int before;

	public Odometer(SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( aSPOT, aRobot, aMessage, aContent );
		before = robot.getAccumulatedDistance();
	}

	protected void onState() {
		int now      = robot.getAccumulatedDistance();
		int diff     = Math.abs( now - before );
		before       = now;
		accumulated += diff;

		message.set( "performative", "inform-ref" );
		message.set( "readings",     "( :accumulated " + accumulated + " :difference " + diff + " )" );
		spot   .queueMessage( message );			
	}
}
