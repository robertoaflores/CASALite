package edu.cnu.spot.event;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public class Bye extends SPOTReply {
	
	public Bye(SPOTAgent aSPOT, MapMessage aMessage) {
		super( false, aSPOT, aMessage, null );
	}

	protected void onState() {
//		System.out.println( "[SPOT] waiting until all events are handled..." );
		spot.queueStop();
	}
}
