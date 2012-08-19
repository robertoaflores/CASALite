package edu.cnu.spot.MIDlet;

import edu.cnu.spot.create.RobotCylon;
import edu.cnu.spot.create.RobotDevice;

public abstract class RobotDeviceMIDlet extends StateMIDlet {
	protected RobotDevice robot; 

	protected void onEntry() {
		robot = getRobot();
	}
	
	protected RobotDevice getRobot() {
		return new RobotCylon();
	}
}
