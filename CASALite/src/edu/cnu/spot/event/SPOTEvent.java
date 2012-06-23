package edu.cnu.spot.event;

import edu.cnu.casaLite.event.Event;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public abstract class SPOTEvent extends Event {
	protected final SPOTAgent  spot;
	protected final MapMessage message;
	protected final MapMessage content;

	public SPOTEvent(SPOTAgent aSPOT, MapMessage aMessage) {
		this( aSPOT, false, aMessage, null );
	}
	public SPOTEvent(SPOTAgent aSPOT, boolean asynchronous, MapMessage aMessage, MapMessage aContent) {
		super( asynchronous );
		spot    = aSPOT;
		message = aMessage;
		content = aContent;
	}
	protected void onExit() {
		message.set( "performative", "done" );
		spot   .queueMessage( message );
	}
}
