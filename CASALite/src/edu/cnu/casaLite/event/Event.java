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

public abstract class Event extends State {
	private boolean ready;
	private boolean recurrent;

	/**
	 * Constructor that automatically sets the event to synchronous.
	 */
	public Event() {
		this( false );
	}
	/**
	 * Constructor that sets the Event asynchronous. Most events will hold the Event Agents attention
	 * until the event agent is finished. An asynchronous event will be initialized by the Event agent 
	 * and then will run while the Event agent gathers the next event. The event will be set to ready to 
	 * run and not recurrent by default.
	 * 
	 * @param asynchronous sets whether it is asynchronous or synchronous.
	 */
	public Event(boolean asynchronous) {
		super( asynchronous );
		ready     = true;
		recurrent = false;
	}

	/**
	 * Sets the Event ready to run. The Event Agent will not be able to access the 
	 * event unless this value is true. A true value allows access to the Event while a 
	 * false value denies access.
	 * 
	 * @param value determines whether the event is ready to be accessed by the Event Agent.
	 */
	protected final void setReadyToRun(boolean value) {
		ready = value;
	}

	/**
	 * Checks to see if the Event is ready to Run. This determines if it is passed to the Event Agent
	 * or not.
	 * 
	 * @return <code>True</code> if the Event is ready to Run and <code>False</code> otherwise.
	 */
	public final boolean isReadyToRun() {
		return ready;
	}

	/**
	 * Sets the event to recurrent. This is used when the event is required more than once. An example is 
	 * gathering data from an sensor every thirty seconds. A true value will tell the EventQueue that it is
	 * needed again and a false value remove the event from queue.
	 * 
	 * @param value determines whether the event is recurrent
	 */
	protected void setRecurrent(boolean value) {
		recurrent = value;
	}

	/**
	 * Checks to see if the event is recurrent. This determines if it is removed from the Queue after being 
	 * ran or placed back into the Queue.
	 * 
	 * @return <code>True</code> if the Event is recurrent and <code>False</code> otherwise.
	 */
	public final boolean isRecurrent() {
		return recurrent;
	}
}