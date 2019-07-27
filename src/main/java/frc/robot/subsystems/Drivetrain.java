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

  public static double angle = 0;
  public static double throttle = 0;
  public static double rotation = 0;

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
    double heading  = gyroArray[0];

    if(Math.abs(rotation) > .15) {
      frontleft.rotate(rotation);
      frontright.rotate(rotation);
      rearleft.rotate(rotation);
      rearright.rotate(rotation);
    }

    else {
      frontleft.set(throttle, angle-heading);
      frontright.set(throttle, angle-heading);
      rearleft.set(throttle, angle-heading);
      rearright.set(throttle, angle-heading);
    }
  }

  public void getJoystickValues(PS4Controller gp) {
    double x = gp.getLeftXAxis();
    double y = -gp.getLeftYAxis();
    rotation = -gp.getRightXAxis() * Constants.rot;

    throttle = (Math.abs(x) + Math.abs(y)) * Constants.throttle;
    angle = Math.toDegrees(Math.atan2(y,x)) - 90;
    if(angle < 0) {
      angle+=360;
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
