package edu.cnu.spot.message;

import edu.cnu.casaLite.message.KQMLMessage;

public class SPOTReply extends KQMLMessage {
	
	public SPOTReply(String aPerformative, String aReply) {
		set( "performative", aPerformative );
		set( "in-reply-to",  aReply );
	}
}
