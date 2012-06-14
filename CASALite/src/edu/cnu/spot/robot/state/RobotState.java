package edu.cnu.spot.robot.state;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.casaLite.state.State;

public abstract class RobotState extends State {
	protected RobotState() {
		this( false );
	}
	protected RobotState(boolean asynchronous) {
		super( asynchronous );
	}
	public abstract void   handleRobotMessage(String performative, String command, MapMessage message, MapMessage content);
	public abstract String getName();
}
