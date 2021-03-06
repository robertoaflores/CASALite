package edu.cnu.spot.create.state;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.casaLite.state.State;

public abstract class RobotState extends State {
	protected RobotState() {
		this( false );
	}
	protected RobotState(boolean asynchronous) {
		super( asynchronous );
	}
	public abstract boolean interpretMessage(MapMessage message, String performative, MapMessage content, String command);
	public abstract String  getName();
}
