package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.TeleOpDrive;
import frc.util.PS4Gamepad;

public class Drivetrain extends Subsystem {

  public TalonSRX flDrive = new TalonSRX(RobotMap.flDrv);
  public TalonSRX flRot = new TalonSRX(RobotMap.flRot);

  public TalonSRX frDrive = new TalonSRX(RobotMap.frDrv);
  public TalonSRX frRot = new TalonSRX(RobotMap.frRot);

  public TalonSRX rlDrive = new TalonSRX(RobotMap.rlDrv);
  public TalonSRX rlRot = new TalonSRX(RobotMap.rlRot);

  public TalonSRX rrDrive = new TalonSRX(RobotMap.rrDrv);
  public TalonSRX rrRot = new TalonSRX(RobotMap.rrRot);

  public void JoystickControl(PS4Gamepad gp) {
    //Math
    double r = Math.sqrt (Math.pow((Constants.L),2) + (Math.pow((Constants.L),2)));
    double strafeY = gp.getLeftYAxis() * Constants.y;
    double strafeX = gp.getLeftXAxis() * Constants.x;
    double rotationX = gp.getRightXAxis() * Constants.rot;

    double a = strafeX - rotationX * (Constants.L / r);
    double b = strafeX + rotationX * (Constants.L / r);
    double c = strafeY - rotationX * (Constants.W / r);
    double d = strafeY + rotationX * (Constants.W / r);
    
    double backRightSpeed = Math.sqrt ((a * a) + (d * d));
    double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
    double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
    double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));
    
    double backRightAngle = Math.atan2 (a, d) / Math.PI;
    double backLeftAngle = Math.atan2 (a, c) / Math.PI;
    double frontRightAngle = Math.atan2 (b, d) / Math.PI;
    double frontLeftAngle = Math.atan2 (b, c) / Math.PI;

    //Power Assignment
    driveModule(flDrive, flRot, frontLeftSpeed, frontLeftAngle);
    driveModule(frDrive, frRot, frontRightSpeed, frontRightAngle);
    driveModule(rlDrive, rlRot, backLeftSpeed, backLeftAngle);
    driveModule(rrDrive, rrRot, backRightSpeed, backRightAngle);
  }

  public void driveModule(TalonSRX drive, TalonSRX rotation, double throttle, double angle) {
    drive.set(ControlMode.PercentOutput, throttle);
    turnModule(rotation, angle);
  }

  public void turnModule(TalonSRX rotation, double targetAngle) {
    rotation.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);    
    //Pulses per Rev = 7

    rotation.config_kP(0, 1);
    rotation.configSelectedFeedbackCoefficient(7, 0, 0);
    // rotation.configPeakOutputForward(.95);
    // rotation.configPeakOutputReverse(.95);
    rotation.set(ControlMode.Position, targetAngle);
  }



  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleOpDrive());
  }
}
