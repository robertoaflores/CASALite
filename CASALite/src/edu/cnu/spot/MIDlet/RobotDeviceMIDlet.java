package edu.cnu.spot.MIDlet;

import edu.cnu.spot.robot.RobotCylon;
import edu.cnu.spot.robot.RobotDevice;

public abstract class RobotDeviceMIDlet extends StateMIDlet {
	protected RobotDevice robot; 

	protected void onEntry() {
		robot = getRobot();
	}
	
	protected RobotDevice getRobot() {
		return new RobotCylon();
	}
}
