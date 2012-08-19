package edu.cnu.spot.create.event.subscription;

import java.util.Enumeration;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.casaLite.message.Message;
import edu.cnu.spot.SPOTAgent;

public class Sensor extends RobotSubscription {
	private static final String[] SENSORS  = { "wall", "cliffLeft", "cliffFrontLeft", "cliffFrontRight", "cliffRight" };
	
    public  static final int WALL              =  0;
    public  static final int CLIFF_LEFT        =  1;
    public  static final int CLIFF_FRONT_LEFT  =  2;
    public  static final int CLIFF_FRONT_RIGHT =  3;
    public  static final int CLIFF_RIGHT       =  4;

	private final boolean[] monitoring;
	private final int[]     reading;
	private final int[]     before;
	private final int       delta;

	public Sensor(SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( aSPOT, aRobot, aMessage, aContent );

		monitoring = new boolean[ SENSORS.length ];
		reading    = new int    [ SENSORS.length ];
		before     = new int    [ SENSORS.length ];
		
		String       string = content.getQuoted( "name", false );
		Message      names  = Message.fromString( string );
		Enumeration  i      = names.getValues();
		while (i.hasMoreElements()) {
			String name = (String) i.nextElement();
			if      (name.equals( SENSORS[ WALL              ] )) monitoring[ WALL              ] = true;
			else if (name.equals( SENSORS[ CLIFF_LEFT        ] )) monitoring[ CLIFF_LEFT        ] = true;
			else if (name.equals( SENSORS[ CLIFF_FRONT_LEFT  ] )) monitoring[ CLIFF_FRONT_LEFT  ] = true;
			else if (name.equals( SENSORS[ CLIFF_FRONT_RIGHT ] )) monitoring[ CLIFF_FRONT_RIGHT ] = true;
			else if (name.equals( SENSORS[ CLIFF_RIGHT       ] )) monitoring[ CLIFF_RIGHT       ] = true;
		}
		String   change = content.get( "delta" );
		delta = (change != null) ? Integer.parseInt( change ) : 0;
	}

	protected void onState() {
		boolean sendMessage = false;
		for (int i = 0; i < SENSORS.length; i++) {
			if (monitoring[ i ]) {
				before[ i ] = reading[ i ];
				switch (i) {
				case WALL              : reading[ i ] = robot.getWallSignal();            break;
				case CLIFF_LEFT        : reading[ i ] = robot.getCliffLeftSignal();       break;
				case CLIFF_FRONT_LEFT  : reading[ i ] = robot.getCliffFrontLeftSignal();  break;
				case CLIFF_FRONT_RIGHT : reading[ i ] = robot.getCliffFrontRightSignal(); break;
				case CLIFF_RIGHT       : reading[ i ] = robot.getCliffRightSignal();      break;
				}
				int difference = Math.abs( reading[ i ] - before[ i ] );
				if (difference > delta || (reading[ i ] == 0 && before[ i ] != 0)) {
//				if (difference > delta) {
					sendMessage = true;
				}
			}
		}
		if (sendMessage) {
			// populating
			MapMessage readings = new MapMessage();
			for (int i = 0; i < SENSORS.length; i++) {
				if (monitoring[ i ]) {
					readings.set( SENSORS[ i ], Integer.toString( reading[ i ] ));
				}
			}
			// sending
			message.set( "performative", "inform-ref" );
			message.set( "readings",     readings.toString() );
			spot   .queueMessage( message );			
		}
	}
}
