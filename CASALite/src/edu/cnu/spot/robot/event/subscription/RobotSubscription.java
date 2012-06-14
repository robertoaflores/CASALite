package edu.cnu.spot.robot.event.subscription;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.event.IntervalEvent;
import edu.cnu.casaLite.message.MapMessage;

public abstract class RobotSubscription extends IntervalEvent {
	protected final MessageAgent agent;
	protected final IRobotCreate robot;
	protected final MapMessage   message;
	protected final MapMessage   content;

	public RobotSubscription(MessageAgent anAgent, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( INFINITE );

		agent   = anAgent;
		robot   = aRobot;
		message = aMessage;
		content = aContent;
	
		// how often are we triggering this event?
		String string   = content.get( "interval" );
		int    interval = (string != null) ? Integer.parseInt( string ) : 100;
		setInterval( interval );
		
		message.set( "performative", "inform-ref" );
	}

	public boolean equalsContent(MapMessage aContent) {
		String yours = aContent.toString();
		String mine  =  content.toString();

		return mine.equals( yours );
	}
}
