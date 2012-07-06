package edu.cnu.casaLite.event;

import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.IMessage;

public class MessageEvent extends Event {
	private IMessage       message;
	private IMessageStream stream;

	public MessageEvent(IMessage aMessage, IMessageStream aStream) {
		this( false, aMessage, aStream );
	}
	public MessageEvent(boolean asynchronous, IMessage aMessage, IMessageStream aStream) {
		super( asynchronous );
		setMessage( aMessage );
		setStream ( aStream  );
	}
	
	protected final void setMessage(IMessage aMessage) {
		message = aMessage;
	}
	public final IMessage getMessage() {
		return message;
	}

	protected final void setStream(IMessageStream anStream) {
		stream = anStream;
	}
	
	public void onState() {
		stream.sendMessage( message );
	}
}