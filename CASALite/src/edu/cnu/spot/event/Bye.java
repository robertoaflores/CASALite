package edu.cnu.spot.event;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public class Bye extends SPOTEvent {
	
	public Bye(SPOTAgent aSPOT, MapMessage aMessage) {
		super( aSPOT, aMessage );
	}

	protected void onEntry() {
		message.set( "performative", "done" );
		spot   .queueMessage( message );
	}
	protected void onState() {
//		System.out.println( "[SPOT] waiting until all events are handled..." );
		spot.queueStop();
	}
}
