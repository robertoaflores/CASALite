 package edu.cnu.casaLite.event;

import java.util.Timer;
import java.util.TimerTask;

public abstract class IntervalEvent extends Event {
	private static final Timer TIMER   = new Timer();
	public  static final int   FOREVER = Integer.MAX_VALUE;

	private int       timesLeft;
	private long      interval;
	private TimerTask task;

	public IntervalEvent(int timesToRun) {
		this( 500, timesToRun );
	}
	public IntervalEvent(long timeBetweenRuns, int timesToRun) {
//		assert( timesToRun > 0 );
//		assert( timeBetweenRuns > -1 );
		task      = null;
		timesLeft = timesToRun > 0 ? timesToRun : 1;
		setInterval( timeBetweenRuns );
	}

	public final int getTimesLeft() {
		return timesLeft;
	}
	
	public final void setInterval(long timeBetweenRuns) {
//		assert( timeBetweenRuns > -1 );
		interval =     timeBetweenRuns > 0 ? timeBetweenRuns : 0;
		setRecurrent ( timesLeft       > 0 );
	}

	protected final void setRecurrent(boolean value) {
		super.setRecurrent( value );

		if (task != null) {
			task.cancel();
			task  = null;
		}
		if (value) {
			setReadyToRun( false );
			task = new TimerTask() {
				public void run() {
//					System.out.println( "Timer : " + toString() ); 
					setReadyToRun( true );
				}
			};
			TIMER.schedule( task, 0, interval );
		}
	}

	public final void run() {
		if (isRecurrent()) {
			if (timesLeft == FOREVER || timesLeft-- > 0) {
				super.run();
			}
			if (timesLeft == 0) {
				stop();
			}
		}
	}
	
	protected final void onExit() {
		super.onExit();
		setReadyToRun( false );
	}
	
	public final void stop() {
		setRecurrent ( false );
		setReadyToRun( true );
	}
}
