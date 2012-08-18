package edu.cnu.spot;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.IMessage;
import edu.cnu.casaLite.message.KQMLMessage;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.event.Bye;

public class SPOTAgent extends MessageAgent {

	public SPOTAgent(IMessageStream aStream) {
		super( aStream );
	}

	protected void handleMessage(IMessage aMessage) {
		MapMessage message      = (MapMessage) aMessage;
		String     performative = message.get( "performative" );
		MapMessage content      = KQMLMessage.fromString( message.getQuoted( "content", false ));
		String     command      = content.get( "performative" );

		if (!interpretMessage( message, performative, content, command )) {
			message.set( "performative", "not-understood" );
			queueMessage( message );
		}
	}
	
	// must be extended (overridden & called) by all subclass agents that process messages
	protected boolean interpretMessage(MapMessage message, String performative, MapMessage content, String command) {
		if (performative.equals( "request" )) {
			if (command.equals( "bye" )) {
				queueEvent( new Bye( SPOTAgent.this, message ));
				return true;
			}
		}
		if (performative.equals( "done" )) {
			if (command.equals( "bye" )) {
				queueStop();
				return true;
			}
		}
		if (performative.equals( "not-understood" )) {
//			System.out.println( "not-understood received: " + message.toString() );
			return true;
		}
		return false;
	}
}
