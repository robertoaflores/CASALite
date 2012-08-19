package edu.cnu.spot.create.event.interrupt;

import edu.cnu.casaLite.message.MapMessage;

public class TimeInterrupt implements IRobotInterrupt {
	private long    length;
	private long    starting;
	private boolean triggered;

	public TimeInterrupt(String aLength) {
		length    = Long.parseLong( aLength );
		starting  = System.currentTimeMillis();
		triggered = false;
	}
	
	public boolean triggers() {
		long current    = System.currentTimeMillis();
		long difference = current - starting;
		if  (difference > length) {
			triggered = true;
		}
		return triggered;
	}

	public boolean happenned() {
		return triggered;
	}

	public void addKeyValue(MapMessage message) {
		message.set( "time", "" + length );
	}
}
