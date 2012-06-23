package edu.cnu.spot.robot;

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

		addProcessor( new ISPOTProcessor() {
			public boolean handleMessage(MapMessage message, String performative, MapMessage content, String command) {
				if (performative.equals( "request" )) {
					Event   event = null;

					if (command.equals( "lights" )) event = new Lights( RobotCylon.this, RobotCylon.this, message, content );
					
					if (event != null) {
						queueEvent( event );
						return true;
					}
				}
				return false;
			}
		});
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
/*	
	protected boolean handleMessage(IMessage aMessage) {
//		System.out.println( "[iCylon] received: " + aMessage );
		MapMessage message      = (MapMessage) aMessage;
		String     performative = message.get( "performative" );
		String     language     = message.get( "language" );
		boolean    handled      = false;

		if (language.equals( "spot" )) {
			MapMessage content = KQMLMessage.fromString( message.getQuoted( "content", false ));
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
*/
}
