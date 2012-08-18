package edu.cnu.spot.robot;

import java.util.Enumeration;
import java.util.Vector;

import org.sunspotworld.create.Create;
import org.sunspotworld.create.IRobotCreate;

import edu.cnu.casaLite.event.Event;
import edu.cnu.casaLite.io.IMessageStream;
import edu.cnu.casaLite.message.MapMessage;
import edu.cnu.spot.SPOTAgent;
import edu.cnu.spot.robot.event.Curve;
import edu.cnu.spot.robot.event.Drive;
import edu.cnu.spot.robot.event.Sing;
import edu.cnu.spot.robot.event.Spin;
import edu.cnu.spot.robot.event.Stop;
import edu.cnu.spot.robot.event.subscription.Bumper;
import edu.cnu.spot.robot.event.subscription.Infrared;
import edu.cnu.spot.robot.event.subscription.Odometer;
import edu.cnu.spot.robot.event.subscription.RobotSubscription;
import edu.cnu.spot.robot.event.subscription.Sensor;

public class RobotDevice extends SPOTAgent {
	private   final Vector       subscriptions; 
	protected       IRobotCreate robot;
	private         boolean      init;

//	public RobotDevice() {
//		this( null );
//	}
	public RobotDevice(IMessageStream aStream) {
		super( aStream );
		init          = false;
		subscriptions = new Vector();
	}

	protected boolean isInitialized() {
		return super.isInitialized() && init;
	}
	protected void onInit() {
		super.onInit();
		robot = Create.makeIRobotCreate();
		init  = true;
	}

	// must be extended (overridden & called) by all subclass agents that process messages
	protected boolean interpretMessage(MapMessage message, String performative, MapMessage content, String command) {
		if (performative.equals( "request" )) {
			String  string       = content.get( "asynchronous" );
			boolean asynchronous = (string != null) && string.equals( "true" );
			Event   event        = null;

			if      (command.equals( "curve" )) event = new Curve( asynchronous, RobotDevice.this, robot, message, content );
			else if (command.equals( "drive" )) event = new Drive( asynchronous, RobotDevice.this, robot, message, content );
			else if (command.equals( "sing"  )) event = new Sing ( asynchronous, RobotDevice.this, robot, message, content );
			else if (command.equals( "spin"  )) event = new Spin ( asynchronous, RobotDevice.this, robot, message, content );
			else if (command.equals( "stop"  )) event = new Stop (               RobotDevice.this, robot, message          );

			if (event != null) {
				queueEvent( event );
				
				System.out.println( " [RobotDevice] <"+ performative +">:<" + command + "> REQUEST" );
				return true;
			}
		}
		if (performative.equals( "subscribe" )) {
			Event event = null;

			if      (command.equals( "sensor"   )) event = new Sensor  ( RobotDevice.this, robot, message, content );
			else if (command.equals( "odometer" )) event = new Odometer( RobotDevice.this, robot, message, content );
			else if (command.equals( "infrared" )) event = new Infrared( RobotDevice.this, robot, message, content );
			else if (command.equals( "bumper"   )) event = new Bumper  ( RobotDevice.this, robot, message, content );

			if (event != null) {
//				Debug.println( " [iRobot] +subscribe: " + content.toString() );
				subscriptions.addElement( event );
				queueEvent              ( event );
				
				System.out.println( " [RobotDevice] <"+ performative +">:<" + command + "> SUBSCRIBE" );
				return true;
			}
		}
		if (performative.equals( "unsubscribe" )) {
			Enumeration       i     = subscriptions.elements();
			RobotSubscription event = null;

			while (i.hasMoreElements()) {
				RobotSubscription now = (RobotSubscription) i.nextElement();
				if (now.equalsContent( content )) {
					event = now;
					break;
				}
			}
			if (event != null) {
				event.stop();
				subscriptions.removeElement( event );
				message.set( "performative", "done" );
				queueMessage( message );

				System.out.println( " [RobotDevice] <"+ performative +">:<" + command + "> UNSUBSCRIBE" );
				return true;
			}
		}
		System.out.println( " [RobotDevice] <"+ performative +">:<" + command + "> SUPER" );
		return super.interpretMessage(message, performative, content, command);
	}
}