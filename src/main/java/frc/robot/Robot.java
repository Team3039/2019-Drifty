package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.util.PS4Gamepad;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {

    private static final String kDefaultAuto = "Default";
    private static final String kCustomAuto = "My Auto";
    private String m_autoSelected;
    private SendableChooser<String> m_chooser = new SendableChooser<>();
    
    //angle motor, drive motor, encoder port
    private WheelDrive frontLeft = new WheelDrive (1, 0, 0);
    private WheelDrive frontRight = new WheelDrive (5, 4, 1);
    private WheelDrive backLeft = new WheelDrive (3, 2, 2);
    private WheelDrive backRight = new WheelDrive (7, 6, 3);

    private PS4Gamepad gp = new PS4Gamepad(0);
    private SwerveDrive swerveDrive = new SwerveDrive (backRight, backLeft, frontRight, frontLeft);
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

    @Override
    public void robotInit() {
        // m_chooser.addDefault("Default Auto", kDefaultAuto);
        // m_chooser.addObject("My Auto", kCustomAuto);
        // SmartDashboard.putData("Auto choices", m_chooser);
    }

    @Override
    public void robotPeriodic() {
    }
    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {
        m_autoSelected = m_chooser.getSelected();
        // autoSelected = SmartDashboard.getString("Auto Selector",
        // defaultAuto);
        System.out.println("Auto selected: " + m_autoSelected);
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
        switch (m_autoSelected) {
            case kCustomAuto:
                // Put custom auto code here
                break;
            case kDefaultAuto:
            default:
                // Put default auto code here
                break;
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        swerveDrive.drive (gp.getLeftXAxis() * -.5, gp.getLeftYAxis()*.5, gp.getRightXAxis());
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}

