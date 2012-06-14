package edu.cnu.spot.MIDlet;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public abstract class StateMIDlet extends MIDlet {

	protected void startApp() throws MIDletStateChangeException {
		try {
			onEntry();
			onState();
			onExit();
		}
		catch (RuntimeException e) {
			onException();
			throw e;
		}
	}
	// State methods
	protected void onEntry()     { System.out.println("[stateMIDlet] onEntry"); }
	protected void onState()     { System.out.println("[stateMIDlet] onState"); }
	protected void onExit()      { System.out.println("[stateMIDlet] onExit"); }
	protected void onException() { System.out.println("[stateMIDlet] onException"); }
	protected void onPause()     { System.out.println("[stateMIDlet] onPause"); }
	protected void onDestroy(boolean unconditional) { 
		System.out.println("[stateMIDlet] onDestroy <"+unconditional+">"); 
	}
	// MIDlet methods
	protected void pauseApp() { onPause(); }
	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException { onDestroy( unconditional ); }
}
