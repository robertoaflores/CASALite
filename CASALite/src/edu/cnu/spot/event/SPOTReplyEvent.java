package edu.cnu.spot.event;

import edu.cnu.casaLite.message.KQMLMessage;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public abstract class SPOTReplyEvent extends SPOTEvent {
	protected final MapMessage reply;

	public SPOTReplyEvent(SPOTAgent aSPOT, MapMessage aMessage) {
		this( false, aSPOT, aMessage, null );
	}
	public SPOTReplyEvent(SPOTAgent aSPOT, MapMessage aMessage, MapMessage aContent) {
		this( false, aSPOT, aMessage, aContent );
	}
	public SPOTReplyEvent(boolean asynchronous, SPOTAgent aSPOT, MapMessage aMessage, MapMessage aContent) {
		super( asynchronous, aSPOT, aMessage, aContent );
		
		reply = new KQMLMessage();
		reply.set( "performative", "done" );
		reply.set( "in-reply-to",  aMessage.get( "reply-with" ));
	}

	protected void onExit() {
		spot.queueMessage( reply );
	}
}
