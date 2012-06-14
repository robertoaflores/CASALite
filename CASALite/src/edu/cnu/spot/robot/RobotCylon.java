package edu.cnu.spot.robot;

import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.sensorboard.EDemoBoard;

import edu.cnu.casaLite.event.Event;
import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.KQMLMessage;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.casaLite.message.IMessage;
import edu.cnu.spot.MIDlet.util.CylonLights;
import edu.cnu.spot.robot.event.Lights;

public class RobotCylon extends RobotDevice {
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
		lights = new CylonLights( EDemoBoard.getInstance() );
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
	
	public void setLights(LEDColor aColor) {
		lights.setColor( aColor );
	}
	
	protected void handleMessage(IMessage aMessage) {
//		System.out.println( "[iCylon] received: " + aMessage );
		MapMessage message      = (MapMessage) aMessage;
		String     performative = message.get( "performative" );
		String     language     = message.get( "language" );
		boolean    handled      = false;

		if (language.equals( "spot" )) {
			MapMessage content = (MapMessage) KQMLMessage.fromString( message.getQuoted( "content", false ));
			String     command = content.get( "performative" );

			if (performative.equals( "request" )) {
				Event event;
				if      (command.equals( "lights" )) event = new Lights( this, message, content );
				else                                 event = null;
				if (event != null) {
					queueEvent( event );
					handled = true;
				}
			}
		}
		if (!handled) {
			super.handleMessage( aMessage );
		}
	}
}
