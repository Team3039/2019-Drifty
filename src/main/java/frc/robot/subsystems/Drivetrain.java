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
    double r = Math.sqrt (Constants.L * Constants.L) + (Constants.W * Constants.W);
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
    drive.set(ControlMode.PercentOutput, -throttle);
    rotation.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);    
    rotation.config_kP(0, 15);
    rotation.config_kI(0, 0);
    rotation.config_kD(0, 0);
    rotation.config_kF(0, 0);
    //Pulses per Rev = 7 for Hall Effect Encoder
    //1656 Ticks per Rev on Swerve Module
    rotation.configSelectedFeedbackCoefficient(.2174); //1656 / 360 degrees

    //angle value is returned from -1 to 1
    double targetPosition = angle*360;


    System.out.println(targetPosition);
    rotation.set(ControlMode.Position, targetPosition); 
   }

  public void startup() {
    flRot.setSelectedSensorPosition(0);
    frRot.setSelectedSensorPosition(0);
    rlRot.setSelectedSensorPosition(0);
    rrRot.setSelectedSensorPosition(0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleOpDrive());
  }
}
