package edu.cnu.spot.create.event.subscription;

import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.create.io.sonar.ParallaxUltrasound;

public class Ultrasound extends RobotSubscription {
	private ParallaxUltrasound ultra;
	private double             before;
	private double             delta;	
	
	public Ultrasound(SPOTAgent aSPOT, IRobotCreate aRobot, MapMessage aMessage, MapMessage aContent) {
		super( aSPOT, aRobot, aMessage, aContent );
		ultra = new ParallaxUltrasound( 4 );
		
		String change = content.get("delta");
		delta = (change != null) ? Double.parseDouble(change): 0.0;
	}

	protected void onState() {
		double now        = ultra.getReadingInCentimeters();
		double difference = Math.abs( now - before );
		
		if (difference > delta) {
			// sending
			message.set( "performative", "inform-ref" );
			message.set( "readings", "( :distance " + now + " )" );
			spot   .queueMessage( message );
		}
	}
}
