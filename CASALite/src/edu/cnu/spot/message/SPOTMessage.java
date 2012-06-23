package edu.cnu.spot.message;

import edu.cnu.casaLite.message.KQMLMessage;

public class SPOTMessage extends KQMLMessage {
	private static int REPLY_COUNT = 1;
	
	public SPOTMessage(String aPerformative, String aContent) {
		set( "performative", aPerformative );
		set( "content",      aContent );
		set( "reply-with",   "" + REPLY_COUNT++ );
	}
}
