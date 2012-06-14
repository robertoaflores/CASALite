package edu.cnu.spot.robot.event.subscription;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.message.MapMessage;

public class Odometer extends RobotSubscription {
	private int accumulated;
	private int before;

	public Odometer(MessageAgent anAgent, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( anAgent, aRobot, aMessage, aContent );
		before = robot.getAccumulatedDistance();
	}

	protected void onState() {
		int now      = robot.getAccumulatedDistance();
		int diff     = Math.abs( now - before );
		before       = now;
		accumulated += diff;

		message.set( "readings", "( :accumulated " + accumulated + " :difference " + diff + " )" );
		agent  .queueMessage( message );			
	}
}
