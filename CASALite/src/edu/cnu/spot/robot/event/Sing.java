package edu.cnu.spot.robot.event;

import java.util.Enumeration;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.casaLite.message.Message;
import edu.cnu.spot.SPOTAgent;

public class Sing extends RobotEvent {
	private int[] song;

	public Sing(SPOTAgent aSPOT, boolean asynchronous, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( aSPOT, asynchronous, aRobot, aMessage, aContent );

		String      string = content.getQuoted( "song", false  );
		Message     notes  = Message.fromString( string );
		Enumeration e      = notes.getValues();
		
		song = new int[ notes.getSize() ];
		for (int i = 0; i < song.length; i++) {
			song[ i ] = Integer.parseInt((String) e.nextElement());
		}
	}

	protected void onState() {
		robot.song    ( 1, song );
		robot.playSong( 1 );
	}
}
