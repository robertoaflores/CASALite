package edu.cnu.spot.robot.event.interrupt;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;

public class AngleInterrupt implements IRobotInterrupt {
	private IRobotCreate robot;
	private long         degrees;
	private long         starting;
	private boolean      triggered;

	public AngleInterrupt(IRobotCreate aRobot, String aDegrees) {
		robot     = aRobot;
		degrees   = Long.parseLong( aDegrees );
		starting  = robot.getAccumulatedAngle();
		triggered = false;
	}

	public boolean triggers() {
		long current    = robot.getAccumulatedAngle();
		long difference = Math.abs( current - starting );
		if  (difference > degrees) {
			triggered = true;
		}
		return triggered;
	}

	public boolean happenned() {
		return triggered;
	}

	public void addKeyValue(MapMessage message) {
		message.set( "degrees",  "" + degrees );
	}
}
