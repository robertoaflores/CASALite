package edu.cnu.spot.create.event.interrupt;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;

public class DistanceInterrupt implements IRobotInterrupt {
	private IRobotCreate robot;
	private long         distance;
	private long         starting;
	private boolean      triggered;

	public DistanceInterrupt(IRobotCreate aRobot, String aDistance) {
		robot     = aRobot;
		distance  = Long.parseLong( aDistance );
		starting  = robot.getAccumulatedDistance();
		triggered = false;
	}

	public boolean triggers() {
		long current    = robot.getAccumulatedDistance();
		long difference = Math.abs( current - starting );
		if  (difference > distance) {
			triggered = true;
		}
		return triggered;
	}

	public boolean happenned() {
		return triggered;
	}

	public void addKeyValue(MapMessage message) {
		message.set( "distance", "" + distance );
	}
}
