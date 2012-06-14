package edu.cnu.spot.robot.event.interrupt;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;

public class BumpInterrupt implements IRobotInterrupt {
	private static final int LEFT  = 0;
	private static final int RIGHT = 1;
	private static final int BOTH  = 2;
	private static final int NONE  = 3;
	private static final int ANY   = 4;

	private final IRobotCreate robot;
	private       int          bumps;
	private       boolean      triggered;
	private       boolean      left;
	private       boolean      right;

	public BumpInterrupt(IRobotCreate aRobot, String aBump) {
		robot     = aRobot;
		triggered = false;
		
		if      (aBump.equals( "left"    )) bumps = LEFT;
		else if (aBump.equals( "right"   )) bumps = RIGHT;
		else if (aBump.equals( "both"    )) bumps = BOTH;
		else if (aBump.equals( "none"    )) bumps = NONE;
		else if (aBump.equals( "any"     )) bumps = ANY;
	}

	public boolean triggers() {
		left  = robot.isBumpLeft();
		right = robot.isBumpRight();

		switch (bumps) {
		case LEFT  : triggered =  left;           break;
		case RIGHT : triggered =           right; break;
		case BOTH  : triggered =  left &&  right; break;
		case NONE  : triggered = !left && !right; break;
		case ANY   : triggered =  left ||  right; break;
		}
		return triggered;
	}
 
	public boolean happenned() {
		return triggered;
	}

	public void addKeyValue(MapMessage message) {
		String value = "";
		switch (bumps) {
		case LEFT   : value = "left";  break;
		case RIGHT  : value = "right"; break;
		case BOTH   : value = "both";  break;
		case NONE   : value = "none";  break;
		case ANY    : if (left && right) value = "both";
		              else               value = left ? "left" : "right";   break;
		}
		message.set( "bump", value );
	}
}
