package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {

  //Subsytems
  public static OI oi;
  public static Drivetrain drivetrain = new Drivetrain();

  //Choosers
  Command autoCommand;
  SendableChooser<Command> autoChooser = new SendableChooser<>();

  @Override
  public void robotInit() {
    oi = new OI();
    drivetrain.resetGyro();
    drivetrain.zeroSensors();
    System.out.println("Only True Led-Gends Will Know");
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("RL", drivetrain.rearLeft.getModuleAngle());
    SmartDashboard.putNumber("RR", drivetrain.rearRight.getModuleAngle());
    SmartDashboard.putNumber("FR", drivetrain.frontRight.getModuleAngle());
    SmartDashboard.putNumber("FL", drivetrain.frontLeft.getModuleAngle());

    SmartDashboard.putNumber("Gyro", drivetrain.getGyro());
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    autoCommand = autoChooser.getSelected();

    if (autoCommand != null) {
      autoCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    if (autoCommand != null) {
      autoCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}