package edu.cnu.spot.robot;

import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.robot.state.RobotState;

public abstract class RobotController extends SPOTAgent {
	private RobotState state;

	public RobotController() {
		this( null );
	}
	public RobotController(IMessageStream aStream) {
		super( aStream );
	}

	// must be extended (overridden & called) by all subclass agents that process messages
	protected boolean interpretMessage(MapMessage message, String performative, MapMessage content, String command) {
			RobotState state = getState();
			return state.interpretMessage( message, performative, content, command ) || 
				   super.interpretMessage( message, performative, content, command );
	}

	public abstract void setInitialState();
	
	protected void setState(RobotState aState) {
		state = aState;
		state.run();
	}
	public RobotState getState() {
		return state;
	}
}
