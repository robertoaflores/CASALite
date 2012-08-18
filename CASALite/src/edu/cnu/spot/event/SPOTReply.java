package edu.cnu.spot.event;

import edu.cnu.casaLite.event.Event;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public abstract class SPOTReply extends Event {
	protected final SPOTAgent  spot;
	protected final MapMessage message;
	protected final MapMessage content;

	public SPOTReply(boolean asynchronous, SPOTAgent aSPOT, MapMessage aMessage, MapMessage aContent) {
		super( asynchronous );
		
		spot    = aSPOT;
		message = aMessage;
		content = aContent;
	}
}
