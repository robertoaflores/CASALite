package edu.cnu.casaLite.state;

public abstract class State implements Runnable {
	private final boolean inThread;
	
	protected State() {
		this( false );
	}
	protected State(boolean asynchronous) {
		inThread = asynchronous;
	}
	
	protected          void onEntry() { };
	protected abstract void onState();
	protected          void onExit()  { };

	public void run() {
		if (inThread) {
			new Thread( new Runnable() {
				public void run() {
					onEntry();
					onState();
					onExit();
				}
			}).start();
		}
		else {
			onEntry();
			onState();
			onExit();
		}
	}
}