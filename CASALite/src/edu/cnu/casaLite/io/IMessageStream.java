package edu.cnu.casaLite.io;

import edu.cnu.casaLite.message.IMessage;

public interface IMessageStream {
	void           sendMessage(IMessage aMessage);
	IMessage       getNextMessage();
	
	IURLDescriptor getURL();
	
	boolean        isInitialized();
	void           open();
	void           close();
}
