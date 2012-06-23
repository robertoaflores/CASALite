package edu.cnu.spot.robot.event;

import java.util.Enumeration;
import java.util.Vector;

import org.sunspotworld.create.IRobotCreate;

import com.sun.spot.util.Utils;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.robot.event.interrupt.BumpInterrupt;
import edu.cnu.spot.robot.event.interrupt.DistanceInterrupt;
import edu.cnu.spot.robot.event.interrupt.IRobotInterrupt;
import edu.cnu.spot.robot.event.interrupt.TimeInterrupt;

public class Drive extends RobotEvent {
	// "lastDrive" points to the last created Drive instance. Since a Drive command 
	// cancels the previous Drive command then we can safely terminate the previous one 
	// (if still active) by marking it "obsolete" before executing the newer Drive.
	private static  Drive   lastDrive = null;

	private   final Drive   beforeMe;
	protected final int     speed;
	private   final Vector  interrupts;
	private         boolean obsolete;
	private         boolean interrupted;

	public Drive(SPOTAgent aSPOT, boolean asynchronous, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( aSPOT, asynchronous, aRobot, aMessage, aContent );

		beforeMe  = lastDrive;
		lastDrive = this;
		
		speed        = Integer.parseInt( content.get( "speed"  ));
		interrupts   = new Vector();

		String time     = content.get( "time"     ); if (time     != null) addInterrupt( new TimeInterrupt    (        time ));
		String distance = content.get( "distance" ); if (distance != null) addInterrupt( new DistanceInterrupt( robot, distance ));
		String bump     = content.get( "bump"     ); if (bump     != null) addInterrupt( new BumpInterrupt    ( robot, bump ));
	}

	protected void addInterrupt(IRobotInterrupt interrupt) {
		interrupts.addElement( interrupt );
	}

	protected void onDrive() {
		robot.drive( speed, IRobotCreate.DRIVE_STRAIGHT );
	}

	protected void onEntry() {
		if (beforeMe != null) {
			beforeMe.obsolete = true;
		}
	}
	protected void onState() {
		onDrive();
		
		interrupted = interrupts.size() == 0;
		while (!interrupted && !obsolete) {
			Enumeration i = interrupts.elements();
			while (i.hasMoreElements()) {
				IRobotInterrupt interrupt = (IRobotInterrupt) i.nextElement();
				interrupted  |= interrupt.triggers();
			}
			if (interrupted) robot.stop();
			else             Utils.sleep( 50 );
		}
	}
	protected void onExit() {
		if (interrupted) {
			MapMessage  triggered = new MapMessage();
			Enumeration i         = interrupts.elements();
			while (i.hasMoreElements()) {
				IRobotInterrupt interrupt = (IRobotInterrupt) i.nextElement();
				if (interrupt.happenned()) {
					interrupt.addKeyValue( triggered );
				}
			}
			if (triggered.getSize() > 0) {
				message.set( "interrupts", triggered.toString() );
			}
		}
		super.onExit();
	}
}
