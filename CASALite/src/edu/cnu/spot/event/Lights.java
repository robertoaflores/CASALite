package edu.cnu.spot.event;

import com.sun.spot.resources.transducers.LEDColor;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.util.ICylon;

public class Lights extends SPOTReplyEvent {
	private LEDColor color;
	private ICylon   cylon;
	
	public Lights(SPOTAgent aSPOT, ICylon aCylon, MapMessage aMessage, MapMessage aContent) {
		super( false, aSPOT, aMessage, aContent );
		cylon = aCylon;
		
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
		cylon.setColor( color );
	}
}
