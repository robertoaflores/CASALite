package edu.cnu.casaLite.io;

import edu.cnu.casaLite.message.AbstractParser;
import edu.cnu.casaLite.message.IMessage;
import edu.cnu.casaLite.message.MessageQueue;

public abstract class AbstractMessageStream implements IMessageStream {
	private AbstractParser parser;
	private MessageQueue   queue;
	private IURLDescriptor url;
	
	public AbstractMessageStream(AbstractParser aParser) {
		parser = aParser;
		queue  = new MessageQueue();
		url    = null;
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

	public IURLDescriptor getURL() {
		if (url == null) {
			url = new URLDescriptor( getScheme() + "://" + getHost() + ":" + getPort() );
		}
		return url;
	}
	
	protected abstract String getScheme();
	protected abstract String getHost();
	protected abstract int    getPort();

//	public URLDescriptor getURL() {
//		String scheme = getScheme();
//		int    port   = getPort();
//		String host   = getHost();
//		return new URLDescriptor( scheme + "://" + host + ":" + port );
//	}
}
