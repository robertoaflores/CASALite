package edu.cnu.casaLite.event;

import java.util.Enumeration;
import java.util.Vector;

public class EventQueue {
	private Vector list;

	public EventQueue() {
		list = new Vector();
	}

	public synchronized Event dequeue() {
		Enumeration i = list.elements();
		while (i.hasMoreElements()) {
			Event event = (Event) i.nextElement();
			if (event.isReadyToRun()) {
				list.removeElement( event );
				return event;
			}
		}
		return null;
	}

	public synchronized void queue(Event event) {
//		System.out.println( "added: " + event );
		list.addElement( event );
	}

	public boolean contains(Event event) {
		return list.contains( event );
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}

	public int getSize() {
		return list.size();
	}
}
