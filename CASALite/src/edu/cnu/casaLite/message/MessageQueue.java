package edu.cnu.casaLite.message;

import java.util.Vector;

public class MessageQueue {
	private Vector list;

	public MessageQueue() {
		list = new Vector();
	}

	public synchronized IMessage dequeue() {
		IMessage result = null;
		if (!list.isEmpty()) {
			result = (IMessage) list.elementAt( 0 );
			list.removeElementAt( 0 );
//			System.out.println("[queue] outgoing [" + getSize() + "]: " + result );
		}
		return result;
	}

	public synchronized void enqueue(IMessage message) {
		list.addElement( message );
//		System.out.println( "[queue] incoming [" + getSize() + "]: " + message );
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}

	public int getSize() {
		return list.size();
	}
}
