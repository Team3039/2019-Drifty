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

  public static double targetAngle = 0;
  public static double throttle = 0;
  public static double rotation = 0;

  public static double x = 0;
  public static double y = 0;

  public static double[] gyroArray = new double[3];
  public TalonSRX flDrv = new TalonSRX(RobotMap.flDrv);
  public TalonSRX flRot = new TalonSRX(RobotMap.flRot);

  public TalonSRX frDrv = new TalonSRX(RobotMap.frDrv);
  public TalonSRX frRot = new TalonSRX(RobotMap.frRot);

  public TalonSRX rlDrv = new TalonSRX(RobotMap.rlDrv);
  public TalonSRX rlRot = new TalonSRX(RobotMap.rlRot);

  public TalonSRX rrDrv = new TalonSRX(RobotMap.rrDrv);
  public TalonSRX rrRot = new TalonSRX(RobotMap.rrRot);

  public PigeonIMU gyro = new PigeonIMU(frDrv);

  public SwerveModule frontleft = new SwerveModule(flDrv, flRot, 0);
  public SwerveModule frontright = new SwerveModule(frDrv, frRot, 1);
  public SwerveModule rearleft = new SwerveModule(rlDrv, rlRot, 2);
  public SwerveModule rearright = new SwerveModule(rrDrv, rrRot, 3);

  public void JoystickControl(PS4Controller gp) {
    getJoystickValues(gp);
    updateGyro();
    double currentHeading  = getGyro();

    //Ether Swerve Calculations
    double Fwd = y*Math.cos(currentHeading) + x*Math.sin(currentHeading);
    double Str = -y*Math.sin(currentHeading) + x*Math.cos(currentHeading);

    double a = Str - rotation*(Constants.Legnth/Constants.R);
    double b = Str + rotation*(Constants.Legnth/Constants.R);
    double c = Fwd - rotation*(Constants.Width/Constants.R);
    double d = Fwd + rotation*(Constants.Width/Constants.R);

    //wsX = Wheel Speed
    //waX = Wheel Angle 
    double ws0 = Math.sqrt(Math.pow(b,2)+Math.pow(d,2));          
    double wa0 = Math.atan2(b,d)*180/Math.PI; 

    double ws1 = Math.sqrt(Math.pow(b,2)+Math.pow(c,2));           
    double wa1 = Math.atan2(b,c)*180/Math.PI;

    double ws2 = Math.sqrt(Math.pow(a,2)+Math.pow(d,2));          
    double wa2 = Math.atan2(a,d)*180/Math.PI;
 
    double ws3 = Math.sqrt(Math.pow(a,2)+Math.pow(c,2));          
    double wa3 = Math.atan2(a,c)*180/Math.PI;

    //Normalize Speeds
    double max = ws0; 
    if(ws1>max) {
      max=ws1;
    } 

    if(ws2>max) {
    max=ws2;
    } 

    if(ws3>max) {
    max=ws3;
    } 

    if(max>1){
      ws0/=max; 
      ws1/=max; 
      ws2/=max; 
      ws3/=max;
    } 

    //Module Control
    frontleft.set(ws0, wa0);
    frontright.set(ws1, wa1);
    rearleft.set(ws2, wa2);
    rearright.set(ws3, wa3);
  }

  public void getJoystickValues(PS4Controller gp) {
    x = gp.getLeftXAxis();
    y = -gp.getLeftYAxis();
    rotation = gp.getRightXAxis() * Constants.rot;

    throttle = (Math.abs(x) + Math.abs(y)) * Constants.throttle;
    targetAngle = Math.toDegrees(Math.atan2(y,x)) - 90;
    if(targetAngle < 0) {
      targetAngle+=360;
    }
  } 

  public void reset() {
    flRot.setSelectedSensorPosition(0);
    frRot.setSelectedSensorPosition(0);
    rlRot.setSelectedSensorPosition(0);
    rrRot.setSelectedSensorPosition(0);
    gyro.setYaw(0);
  }

  public void updateGyro() {
    gyro.getYawPitchRoll(gyroArray);
  }

  public double getGyro() {
    updateGyro();
    return gyroArray[0];
  }
  
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleOpDrive());
  }
}
