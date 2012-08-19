package edu.cnu.spot.create.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.create.event.interrupt.AngleInterrupt;

public class Spin extends Drive {
	private final boolean toTheLeft;
	
	public Spin(boolean asynchronous, SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( asynchronous, aSPOT, aRobot, aMessage, aContent );
		
		String direction = content.get( "direction" );
		toTheLeft = direction != null && direction.equals( "left" );
		
		String degrees = content.get( "degrees" );
		if    (degrees != null) addInterrupt( new AngleInterrupt( robot, degrees ));
	}

	protected void onDrive() {
		int rotation = toTheLeft ? IRobotCreate.DRIVE_TURN_IN_PLACE_COUNTER_CLOCKWISE : IRobotCreate.DRIVE_TURN_IN_PLACE_CLOCKWISE;
		robot.drive( speed, rotation );
	}
}
