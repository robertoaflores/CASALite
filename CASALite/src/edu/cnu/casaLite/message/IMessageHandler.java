package edu.cnu.casaLite.message;

public interface IMessageHandler {
	boolean handleMessage(IMessage aMessage);
	void     queueMessage(IMessage aMessage);
}
