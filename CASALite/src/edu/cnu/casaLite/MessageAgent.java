package edu.cnu.casaLite;

import edu.cnu.casaLite.event.MessageEvent;
import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.io.IURLDescriptor;
import edu.cnu.casaLite.message.IMessage;

public abstract class MessageAgent extends EventAgent {
	private IMessageStream stream;
	private boolean        init;
	
	protected MessageAgent(IMessageStream aStream) {
		init = false;
		setStream( aStream );
	}
	// it must always be overwritten in subclasses
	protected boolean isInitialized() {
		return super.isInitialized() && init && stream.isInitialized();
	}
	protected void onInit() {
		super.onInit();
		stream.open();
		init = true;
	}
	protected void onLoop() {
		super.onLoop();
		IMessage message = stream.getNextMessage();
		if (message != null) {
			handleMessage( message );
		}
	}
	protected void onExit() {
		super.onExit();
		stream.close();
	}

	public void setStream(IMessageStream aStream) {
		stream = aStream;
	}
	
	public IURLDescriptor getURL() {
		return stream.getURL();
	}

	protected abstract void handleMessage(IMessage aMessage);
	
	// used by all who wish to send a message through this agent
	public void queueMessage(IMessage aMessage) {
		MessageEvent event = new MessageEvent( aMessage, stream );
		queueEvent(  event );
	}
}
