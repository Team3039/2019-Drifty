package frc.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import frc.controllers.PS4Controller;
import frc.robot.commands.ResetPose;

public class OI {
	//Calls the Gamepad Classes: Defines gp and cp for the robot
	private PS4Controller driverPad = new PS4Controller(RobotMap.driver);
	private PS4Controller operatorPad = new PS4Controller(RobotMap.operator);
	
	//Returns Controller Data for use with certain Methods
	public PS4Controller getGamepad() {
		return driverPad;
	}
	
	public PS4Controller getCopad() {
		return operatorPad;
	}

	public OI() {
		//Driver Buttons
		Button driverX = operatorPad.getButtonX();
		//Operator Buttons

		// driver Controls
		driverX.whenPressed(new ResetPose());
		//Operator Controls

	}
}