package edu.cnu.spot.event;

import edu.cnu.casaLite.MessageAgent;
import edu.cnu.casaLite.event.Event;
import edu.cnu.casaLite.message.MapMessage;

public class Bye extends Event {
	private MessageAgent agent;
	private MapMessage   message;
	
	public Bye(MessageAgent anAgent, MapMessage aMessage) {
		agent   = anAgent;
		message = aMessage;
	}

	protected void onEntry() {
		message.set( "performative", "done" );
		agent  .queueMessage( message );
	}
	protected void onState() {
//		System.out.println( "[SPOT] waiting until all events are handled..." );
		agent.queueStop();
	}
}
