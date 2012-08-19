package edu.cnu.casaLite;

import edu.cnu.casaLite.event.Event;
import edu.cnu.casaLite.event.EventQueue;

public abstract class EventAgent {
	public static final int INITIALIZED    = 1; // "initialized";
	public static final int STOPPED        = 2; // "stopped";

	private EventQueue eventQueue;
	private boolean    stopped;
	private Thread     thread;

	private boolean    init;
	public EventAgent() {
		init       = false;
		stopped    = false;
		eventQueue = new EventQueue();
		thread     = new Thread( new Runnable() {
			public void run() {
				onInit();
				waitUntil( INITIALIZED );
				onEntry();
				onState();
				onExit();
			}
		});
		thread.start();
	}
	// it must always be overwritten in subclasses
	protected boolean isInitialized() {
		return init;
	}
	protected void onInit() {
		init = true;
	}
	protected void onEntry() { 
//		System.out.println( "onEntry" );
	}
	protected void onState() {
//		System.out.println( "onState" ); 
		while (!stopped) {
			onLoop();
		}
	}
	protected void onLoop() {
		Event event = eventQueue.dequeue();
		if (event == null) {
			onIdle();
		}
		else {
//			System.out.println( "onLoop: " + event.toString() ); 
			event.run();
			if (event.isRecurrent()) {
				queueEvent( event );
			}
		}
	}
	protected void onIdle() {
//		System.out.println( "onIdle" ); 
		synchronized( thread ) {
			try {
				thread.wait( 1 );
			} 
			catch (InterruptedException e) { 
			}
		}
	}
	protected void onExit() { 
//		System.out.println( "onExit" ); 
	}

	public final void queueStop() {
		Event stopEvent = new Event() {
			public void onState() {
				stopped = true;
//				System.out.println( "[stop] dying of a painful death..." );
			}
		};
		queueEvent( stopEvent );
	}
	public void queueEvent(Event event) {
//		System.out.println( "queueing [" + !stopped + "]: " + event );
		if (!stopped) {
			eventQueue.queue( event );
		}
	}

	public void waitUntil(int aPhase) {
		Thread        observer = Thread.currentThread();
		synchronized( observer ) {
			boolean happened = false;
			while (!happened) {
				try {
					observer.wait( 100 ); 
					happened = hasHappened( aPhase );
				} 
				catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}
		}
	}
	protected boolean hasHappened(int aPhase) {
		if (aPhase == INITIALIZED ) return isInitialized();
		if (aPhase == STOPPED     ) return stopped;
		return true;
	}
}