package edu.cnu.casaLite.io;

import edu.cnu.casaLite.message.AbstractParser;
import edu.cnu.casaLite.message.IMessage;
import edu.cnu.casaLite.message.MessageQueue;

public abstract class AbstractMessageStream implements IMessageStream {
	private AbstractParser parser;
	private MessageQueue   queue;
	
	public AbstractMessageStream(AbstractParser aParser) {
		parser = aParser;
		queue  = new MessageQueue();
	}

	public int getSize() {
		return queue.getSize();
	}
	
	protected void queueMessage(String aMessage) {
		IMessage message = parser.fromString( aMessage );
		queue.enqueue( message );
	}

	public IMessage getNextMessage() {
		return queue.dequeue();
	}

	public URLDescriptor getURL() {
		String scheme = getScheme();
		int    port   = getPort();
		String host   = getHost();
		return new URLDescriptor( scheme + "://" + host + ":" + port );
	}
}
