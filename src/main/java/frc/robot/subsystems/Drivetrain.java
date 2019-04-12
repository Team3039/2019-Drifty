package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.commands.TeleOpDrive;
import frc.util.PS4Gamepad;

public class Drivetrain extends Subsystem {

  public static double angle = 0;
  public static double throttle = 0;
  public static double rotation = 0;

  public TalonSRX flDrive = new TalonSRX(RobotMap.flDrv);
  public TalonSRX flRot = new TalonSRX(RobotMap.flRot);

  public TalonSRX frDrive = new TalonSRX(RobotMap.frDrv);
  public TalonSRX frRot = new TalonSRX(RobotMap.frRot);

  public TalonSRX rlDrive = new TalonSRX(RobotMap.rlDrv);
  public TalonSRX rlRot = new TalonSRX(RobotMap.rlRot);

  public TalonSRX rrDrive = new TalonSRX(RobotMap.rrDrv);
  public TalonSRX rrRot = new TalonSRX(RobotMap.rrRot);

  public AnalogInput ha = new AnalogInput(3);
  public void JoystickControl(PS4Gamepad gp) {
    getJoystickValues(gp);
    if(Math.abs(rotation) > .2) {
      rotate();
    }
    else {
      translateModule(flDrive, flRot, angle);
      translateModule(frDrive, frRot, angle);
      translateModule(rlDrive, rlRot, angle);
      translateModule(rrDrive, rrRot, angle);
    }
  }

  public void getJoystickValues(PS4Gamepad gp) {
    double x = gp.getLeftXAxis();
    double y = -gp.getLeftYAxis();
    rotation = -gp.getRightXAxis() * Constants.rot;

    throttle = (Math.abs(x) + Math.abs(y)) * Constants.throttle;
    angle = Math.toDegrees(Math.atan2(y,x)) - 90;
    if(angle < 0) {
      angle+=360;
    }
  } 

  public void translateModule(TalonSRX drv, TalonSRX rot, double targetAngle) {
    drv.set(ControlMode.PercentOutput, throttle);
    rot.set(ControlMode.Position, targetAngle);
    drv.setNeutralMode(NeutralMode.Brake);
    rot.setNeutralMode(NeutralMode.Brake);

  }

  public void rotate() {
    flRot.set(ControlMode.Position, -45);
    flDrive.set(ControlMode.PercentOutput, rotation);

    frRot.set(ControlMode.Position, 45);
    frDrive.set(ControlMode.PercentOutput, -rotation);

    rlRot.set(ControlMode.Position, 45);
    rlDrive.set(ControlMode.PercentOutput, rotation);

    rrRot.set(ControlMode.Position, -45);
    rrDrive.set(ControlMode.PercentOutput, -rotation);
  }

  public void startup(TalonSRX talon) {
    talon.configSelectedFeedbackCoefficient(.2174);
    talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    talon.setSelectedSensorPosition(0);
    talon.config_kP(0, 15);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleOpDrive());
  }
}
