 package edu.cnu.casaLite.event;

import java.util.Timer;
import java.util.TimerTask;

public abstract class IntervalEvent extends Event {
	protected static final Timer TIMER     = new Timer();
	public    static final int   INFINITE  = Integer.MAX_VALUE;

	private int       timesLeft;
	private long      interval;
	private TimerTask task;

	public IntervalEvent(long timeBetweenRuns) {
		this( timeBetweenRuns, 1 );
	}
	public IntervalEvent(long timeBetweenRuns, int timesToRun) {
//		assert( timesToRun > 0 );
//		assert( timeBetweenRuns > -1 );
		timesLeft = timesToRun > 0 ? timesToRun : 1;
		interval  = timeBetweenRuns > 0 ? timeBetweenRuns : 0;
		task      = null;
		setRecurrent( timesLeft > 0 );
	}

	public int getTimesLeft() {
		return timesLeft;
	}
	
	public void setInterval(long timeBetweenRuns) {
//		assert( timeBetweenRuns > -1 );
		interval = timeBetweenRuns > 0 ? timeBetweenRuns : 0;
		setRecurrent ( true );
	}

	public void run() {
		if (isRecurrent()) {
			if (timesLeft == INFINITE || timesLeft-- > 0) {
				super.run();
			}
			if (timesLeft == 0) {
				stop();
			}
		}
	}

	protected void setRecurrent(boolean recurrent) {
		if (task != null) {
			task.cancel();
			task  = null;
		}
		if (recurrent) {
			setReadyToRun( false );
			task = new TimerTask() {
				public void run() {
					setReadyToRun( true );
				}
			};
			TIMER.schedule( task, interval, interval );
		}
	}
	public boolean isRecurrent() {
		return task != null;
	}
	
	protected void onExit() {
		super.onExit();
		setReadyToRun( false );
	}
	
	public void stop() {
		setRecurrent ( false );
		setReadyToRun( true );
	}
}
