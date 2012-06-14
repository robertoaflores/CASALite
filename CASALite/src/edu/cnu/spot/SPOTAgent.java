package edu.cnu.spot;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.KQMLMessage;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.casaLite.message.IMessage;
import edu.cnu.spot.event.Bye;

public class SPOTAgent extends MessageAgent {

	public SPOTAgent() {
		this( null );
	}
	public SPOTAgent(IMessageStream aStream) {
		super( aStream );
	}
	protected void handleMessage(IMessage aMessage) {
		MapMessage message  = (KQMLMessage) aMessage;
//		System.out.println( "[spot] received: " + incoming );
		String  performative = message.get( "performative" );
		String  language     = message.get( "language" );
		boolean handled      = false;

		if (language.equals( "spot" )) {
			String     string  = message.getQuoted( "content", false );
			MapMessage content = (MapMessage) KQMLMessage.fromString( string );
			String     command = content.get( "performative" );
			
			if (performative.equals( "request" )) {
				if (command.equals( "bye" )) {
					handled = true;
					queueEvent( new Bye( this, message ));
				}
			}
			if (performative.equals( "done" )) {
				if (command.equals( "bye" )) {
					handled = true;
					queueStop();
				}
			}
		}
		if (!handled) {
			message.set( "performative", "not-understood" );
			queueMessage( message );
		}
	}
}
