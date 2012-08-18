package edu.cnu.spot.event;

import edu.cnu.casaLite.event.IntervalEvent;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;

public abstract class SPOTSubscription extends IntervalEvent {
	protected final SPOTAgent  spot;
	protected final MapMessage message;
	protected final MapMessage content;

	public SPOTSubscription(SPOTAgent aSPOT, MapMessage aMessage, MapMessage aContent) {
		super( FOREVER );

		spot    = aSPOT;
		message = aMessage;
		content = aContent;
	
		// how often are we triggering this event?
		String string   = content.get( "interval" );
		int    interval = (string != null) ? Integer.parseInt( string ) : 100;
		setInterval( interval );
	}

	public boolean equalsContent(MapMessage aContent) {
		String yours = aContent.toString();
		String mine  =  content.toString();

		return mine.equals( yours );
	}
}
