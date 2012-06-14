package edu.cnu.spot.robot.event.subscription;

import java.util.Enumeration;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.casaLite.message.Message;

public class Bumper extends RobotSubscription {
	private static final String[] BUMPERS  = { "frontLeft", "frontRight" };
	
    public  static final int FRONT_LEFT  =  0;
    public  static final int FRONT_RIGHT =  1;

    private final boolean[] monitoring;
	private final boolean[] reading;
	private final boolean[] before;

	public Bumper(MessageAgent anAgent, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( anAgent, aRobot, aMessage, aContent );

		monitoring = new boolean[ BUMPERS.length ];
		reading    = new boolean[ BUMPERS.length ];
		before     = new boolean[ BUMPERS.length ];
		
		String       string = content.getQuoted( "name", false );
		Message      names  = Message.fromString( string );
		Enumeration  i      = names.getValues();
		while (i.hasMoreElements()) {
			String name = (String) i.nextElement();
			if      (name.equals( BUMPERS[ FRONT_LEFT  ] )) monitoring[ FRONT_LEFT  ] = true;
			else if (name.equals( BUMPERS[ FRONT_RIGHT ] )) monitoring[ FRONT_RIGHT ] = true;
		}
	}

	protected void onState() {
		boolean sendMessage = false;
		for (int i = 0; i < BUMPERS.length; i++) {
			if (monitoring[ i ]) {
				before[ i ] = reading[ i ];
				switch (i) {
				case FRONT_LEFT  : reading[ i ] = robot.isBumpLeft();  break;
				case FRONT_RIGHT : reading[ i ] = robot.isBumpRight(); break;
				}
				if (reading[ i ] != before[ i ]) {
					sendMessage = true;
				}
			}
		}
		if (sendMessage) {
			// populating
			MapMessage readings = new MapMessage();
			for (int i = 0; i < BUMPERS.length; i++) {
				if (monitoring[ i ]) {
					readings.set( BUMPERS[ i ], "" + reading[ i ] );
				}
			}
			// sending
			message.set( "readings", readings.toString() );
			agent  .queueMessage( message );			
		}
	}
}
