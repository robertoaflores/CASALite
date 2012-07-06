package edu.cnu.spot;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.IMessage;
import edu.cnu.casaLite.message.KQMLMessage;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.event.Bye;
import edu.cnu.spot.message.ISPOTMessageProcessor;

public class SPOTAgent extends MessageAgent {

//	protected interface ISPOTProcessor extends IMessageProcessor {
//		boolean handleMessage(MapMessage message, String performative, MapMessage content, String command);
//	}
	
	public SPOTAgent() {
		this( null );
	}
	public SPOTAgent(IMessageStream aStream) {
		super( aStream );

		addProcessor( new ISPOTMessageProcessor() {
			public boolean handleMessage(MapMessage message, String performative, MapMessage content, String command) {
				if (performative.equals( "request" )) {
					if (command.equals( "bye" )) {
						queueEvent( new Bye( SPOTAgent.this, message ));
						return true;
					}
				}
				return false;
			}
		});
		addProcessor( new ISPOTMessageProcessor() {
			public boolean handleMessage(MapMessage message, String performative, MapMessage content, String command) {
				if (performative.equals( "done" )) {
					if (command.equals( "bye" )) {
						queueStop();
						return true;
					}
				}
				return false;
			}
		});
		addProcessor( new ISPOTMessageProcessor() {
			public boolean handleMessage(MapMessage message, String performative, MapMessage content, String command) {
				if (performative.equals( "not-understood" )) {
					System.out.println( "not-understood received: " + message.toString() );
					return true;
				}
				return false;
			}
		});
	}

	public final boolean handleMessage(IMessage aMessage) {
		MapMessage message      = (MapMessage) aMessage;
		String     performative = message.get( "performative" );
		MapMessage content      = KQMLMessage.fromString( message.getQuoted( "content", false ));
		String     command      = content.get( "performative" );

		if (!handleMessage(message, performative, content, command)) {
			message.set( "performative", "not-understood" );
			queueMessage( message );
		}
		return true;
	}
	
	protected boolean handleMessage(MapMessage message, String performative, MapMessage content, String command) {
		for (int i = 0; i < processors.size(); i++) {
			ISPOTMessageProcessor processor = (ISPOTMessageProcessor) processors.elementAt( i );
			if (processor.handleMessage(message, performative, content, command)) {
				System.out.println( "[handler] " + processor.toString() );
				return true;
			}
		}
		return false;
	}
}
