package edu.cnu.spot.MIDlet;

import edu.cnu.spot.io.KQMLBridge;
import edu.cnu.spot.robot.RobotController;

public abstract class AutonomousRobotMIDlet extends RobotDeviceMIDlet {
	private   final KQMLBridge      bridge;
	protected       RobotController controller;

	public AutonomousRobotMIDlet() {
		bridge = new KQMLBridge();
	}
	protected void onEntry() {
		super.onEntry();
		controller = getController();
		robot     .setStream( bridge.getStream() );
		controller.setStream( bridge.getStream() );
	}
	protected void onState() {
		controller.setInitialState();
	}

	protected abstract RobotController getController();
}
