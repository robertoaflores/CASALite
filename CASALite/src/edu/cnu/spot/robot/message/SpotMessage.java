package edu.cnu.spot.robot.message;

import edu.cnu.casaLite.message.KQMLMessage;

public class SpotMessage extends KQMLMessage {
	public SpotMessage(String aPerformative, String aContent) {
		set( "performative", aPerformative );
		set( "language",     "spot" );
		set( "content",      aContent );
	}
}
