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

	protected boolean handleMessage(MapMessage message, String performative, MapMessage content, String command) {
			RobotState state = getState();
			return state.handleMessage( message, performative, content, command ) || 
				   super.handleMessage( message, performative, content, command );
	}
	
//	protected void handleMessage(IMessage aMessage) {
//		MapMessage message  = (MapMessage) aMessage;
//		String     language = message.get( "language" );
//
//		if (language.equals( "spot" )) {
//			String     performative = message.get( "performative" );
//			MapMessage content      = KQMLMessage.fromString( message.getQuoted( "content", false ));
//			String     command      = content.get( "performative" );
//
//			RobotState state        = getState();
//			state.handleRobotMessage( performative, command, message, content );
//		}
//		else {
//			super.handleMessage( message );
//		}
//	}
	
	public abstract void setInitialState();
	
	protected void setState(RobotState aState) {
		state = aState;
		state.run();
	}
	public RobotState getState() {
		return state;
	}
}
