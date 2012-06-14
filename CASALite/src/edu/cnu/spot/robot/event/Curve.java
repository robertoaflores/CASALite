package edu.cnu.spot.robot.event;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.message.MapMessage;

public class Curve extends Drive {
	// a | negative | radius: turns robot | right
	//   | positive |                     | left
	private final int radius;
	
	public Curve(MessageAgent anAgent, boolean asynchronous, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( anAgent, asynchronous, aRobot, aMessage, aContent );
		
		String  direction = content.get( "direction" );
		boolean toTheLeft = direction != null && direction.equals( "left" );

		String string = content.get( "radius" );
		if (string == null) radius = IRobotCreate.DRIVE_STRAIGHT;
		else                radius = Math.abs( Integer.parseInt( string )) * (toTheLeft ? 1 : -1);
	}

	protected void onDrive() {
		robot.drive( speed, radius );
	}
}
