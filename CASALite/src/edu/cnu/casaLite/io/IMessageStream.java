package edu.cnu.casaLite.io;

import edu.cnu.casaLite.message.IMessage;

public interface IMessageStream {
	void          sendMessage(IMessage aMessage);
	IMessage      getNextMessage();
	
	int           getSize();
	String        getScheme();
	int           getPort();
	String        getHost();
	URLDescriptor getURL();
	
	boolean isInitialized();
	void    open();
	void    close();
}
