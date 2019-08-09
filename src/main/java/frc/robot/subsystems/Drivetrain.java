package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.controllers.PS4Controller;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.TeleOpDrive;
import frc.util.SwerveModule;

public class Drivetrain extends Subsystem {  
  public TalonSRX flDrv = new TalonSRX(RobotMap.flDrv);
  public TalonSRX flRot = new TalonSRX(RobotMap.flRot);

  public TalonSRX frDrv = new TalonSRX(RobotMap.frDrv);
  public TalonSRX frRot = new TalonSRX(RobotMap.frRot);

  public TalonSRX rlDrv = new TalonSRX(RobotMap.rlDrv);
  public TalonSRX rlRot = new TalonSRX(RobotMap.rlRot);

  public TalonSRX rrDrv = new TalonSRX(RobotMap.rrDrv);
  public TalonSRX rrRot = new TalonSRX(RobotMap.rrRot);

  public PigeonIMU gyro = new PigeonIMU(frDrv);

  public SwerveModule frontLeft = new SwerveModule(flDrv, flRot);
  public SwerveModule frontRight = new SwerveModule(frDrv, frRot);
  public SwerveModule rearLeft = new SwerveModule(rlDrv, rlRot);
  public SwerveModule rearRight = new SwerveModule(rrDrv, rrRot);

  public SwerveModule[] drivetrain = {frontRight, frontLeft, rearLeft, rearRight};

  public double[] wheelSpeeds = new double[4];
  public double[] wheelAngles = new double[4];

  public void JoystickControl(PS4Controller gp) {
    //Do the Math, Save the World!
    swerveCalculations(getFwd(gp), getStr(gp), getRot(gp));

    //Sets Calculated Speeds and Angles for each Module in Series
    for(int i = 0; i<4; i++) {
      drivetrain[i].set(wheelSpeeds[i], wheelAngles[i]);
    }
  }

  public void swerveCalculations(double joystickFwd, double joystickStr, double joystickRot) {

    /*  
    *   1 d     c 0
    *   b-|-----|-b
    *     |  ^  | 
    *     |  |  |
    *   a-|-----|-a
    *   2 d     c 3
    */

    //Ether Swerve Calculations
    double angleRadians = Math.toRadians(getGyro());
    double temp = joystickFwd * Math.cos(angleRadians) + joystickStr * Math.sin(angleRadians);
    double Str  = -joystickFwd * Math.sin(angleRadians) + joystickStr * Math.cos(angleRadians);
    double Fwd = temp;

    double a = Str - joystickRot*(Constants.Legnth/Constants.Diameter);
    double b = Str + joystickRot*(Constants.Legnth/Constants.Diameter);
    double c = Fwd - joystickRot*(Constants.Width/Constants.Diameter);
    double d = Fwd + joystickRot*(Constants.Width/Constants.Diameter); 

    wheelSpeeds[0] = Math.sqrt((b*b)+(c*c));           
    wheelAngles[0] = Math.atan2(b,c)*180/Math.PI;

    wheelSpeeds[1] = Math.sqrt((b*b)+(d*d));          
    wheelAngles[1] = Math.atan2(b,d)*180/Math.PI; 
 
    wheelSpeeds[2] = Math.sqrt((a*a)+(d*d));          
    wheelAngles[2] = Math.atan2(a,d)*180/Math.PI;

    wheelSpeeds[3] = Math.sqrt((a*a)+(c*c));          
    wheelAngles[3] = Math.atan2(a,c)*180/Math.PI;
  }

  //Set Each Module to their Original Position
  public void resetPose() {
    for(int i = 0; i < 4; i++) {
      drivetrain[i].set(0, 0);
    }
  }

  //Zero the Sensors on each Module
  public void zeroSensors() {
    for(int i = 0; i < 4; i++) {
      drivetrain[i].zeroSensors();
    }
  }

  //Set Gyro to 0
  public void resetGyro() {
    gyro.setFusedHeading(0);
  }

  //Return Gyro Output [0-360)
  public double getGyro() {
    double angle = gyro.getFusedHeading();
    angle %= 360;
    if (angle < 0) {
      angle += 360;
    }
    return 360 - angle;
  }

  public void normalizeSpeeds() {
    double max = wheelSpeeds[0]; 
   
    for(int i = 0; i < 4; i++) {
      if(wheelSpeeds[i] > max) {
        max = wheelSpeeds[i];
      }
    }

    if(max>1){
      for(int i = 0; i < 4; i++) {
        wheelSpeeds[i] /= max;
      }
    } 

  }
  
  public double getFwd(PS4Controller gp) {
    return gp.getLeftYAxis();
  } 

  public double getStr(PS4Controller gp) {
    return gp.getLeftXAxis();
  } 

  public double getRot(PS4Controller gp) {
    return gp.getRightXAxis();
  } 
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleOpDrive());
  }
}
