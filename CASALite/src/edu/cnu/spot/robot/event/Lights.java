package edu.cnu.spot.robot.event;

import com.sun.spot.resources.transducers.LEDColor;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.robot.RobotCylon;

public class Lights extends RobotEvent {
	private LEDColor   color;
	private RobotCylon cylon;

	public Lights(RobotCylon anAgent, MapMessage aMessage, MapMessage aContent) {
		super( anAgent, false, null, aMessage, aContent );
		cylon = anAgent;
		
		String name = content.get( "color" );
		if      (name.equals( "blue"      )) color = LEDColor.BLUE;
		else if (name.equals( "cyan"      )) color = LEDColor.CYAN;
		else if (name.equals( "green"     )) color = LEDColor.GREEN;
		else if (name.equals( "magenta"   )) color = LEDColor.MAGENTA;
		else if (name.equals( "mauve"     )) color = LEDColor.MAUVE;
		else if (name.equals( "orange"    )) color = LEDColor.ORANGE;
		else if (name.equals( "puce"      )) color = LEDColor.PUCE;
		else if (name.equals( "red"       )) color = LEDColor.RED;
		else if (name.equals( "turquoise" )) color = LEDColor.TURQUOISE;
		else if (name.equals( "white"     )) color = LEDColor.WHITE;
		else                                 color = LEDColor.YELLOW;
	}

	protected void onState() {
		cylon.setLights( color );
	}
}
