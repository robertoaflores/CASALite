package edu.cnu.spot.create;

import com.sun.spot.resources.transducers.LEDColor;

import edu.cnu.casaLite.event.Event;
import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.event.Lights;
import edu.cnu.spot.util.CylonLights;
import edu.cnu.spot.util.ICylon;

public class RobotCylon extends RobotDevice implements ICylon {
	private CylonLights lights;
	private boolean     init;
	
	public RobotCylon() {
		this( null );
	}
	public RobotCylon(IMessageStream aStream) {
		super( aStream );
		init = false;
	}

	protected boolean isInitialized() {
		return super.isInitialized() && init;
	}
	protected void onInit() {
		lights = new CylonLights();
		lights.setColor( LEDColor.GREEN );
		super.onInit();
		init = true;
	}
	protected void onState() {
		lights.setColor( LEDColor.BLUE );
		super.onState();
	}
	protected void onExit() {
		lights.setColor( LEDColor.YELLOW );
		super.onExit();
	}
	
	public void setColor(LEDColor aColor) {
		lights.setColor( aColor );
	}

	// must be overridden by all agents that process messages
	protected boolean interpretMessage(MapMessage message, String performative, MapMessage content, String command) {
		if (performative.equals( "request" )) {
			if (command.equals( "lights" )) {
				Event event = new Lights( RobotCylon.this, RobotCylon.this, message, content );
				queueEvent( event );
				return true;
			}
		}
		return super.interpretMessage(message, performative, content, command);
	}
}
