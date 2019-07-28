package frc.robot;

import frc.controllers.PS4Controller;

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

		//Operator Buttons

		//driver Controls


		//Operator Controls

	}
}