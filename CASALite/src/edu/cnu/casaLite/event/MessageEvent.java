package edu.cnu.casaLite.event;

import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.IMessage;

public class MessageEvent extends Event {
	private final IMessage       message;
	private final IMessageStream stream;

	public MessageEvent(IMessage aMessage, IMessageStream anStream) {
		message = aMessage;
		stream  = anStream;
	}

	public IMessage getMessage() {
		return message;
	}

	public void onState() {
		stream.sendMessage( message );
	}
}