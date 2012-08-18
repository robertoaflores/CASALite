/**
 * Event is the abstract class that allows the modification of the state of each event. An event object has  
 * two main states:
 * <ul>
 * <li>Run</li>
 * <li>Recurrent</li>
 * </ul>
 * <p>
 * These two states determines how the Event Manager handles the event. 
 * 
 * @author Dr. Roberto Flores
 * @author Justin Ruger
 */

package edu.cnu.casaLite.event;

import edu.cnu.casaLite.state.State;

public abstract class Event extends State implements Runnable {
	private boolean ready;
	private boolean recurrent;

	public Event() {
		this( false );
	}
	public Event(boolean asynchronous) {
		super( asynchronous );
		ready     = true;
		recurrent = false;
	}

	protected void setReadyToRun(boolean value) {
		ready = value;
		if (value) System.out.println( "readyToRun  : " + toString() ); 
	}
	public boolean isReadyToRun() {
		return ready;
	}

	protected void setRecurrent(boolean value) {
		recurrent = value;
		if (value) System.out.println( "setRecurrent: " + toString() ); 
	}
	public boolean isRecurrent() {
		return recurrent;
	}
}