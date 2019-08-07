package frc.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SwerveModule {

    TalonSRX drive;
    TalonSRX rotation;
    int place;
    
    /**
     * @param drive 
     *  Controls the wheel's velocity
     * @param rotation 
     *  Controls the wheel's position
     * @param placement 
     *  Front-Left is 0, Front-Right is 1, Rear-Left is 2, and Rear-Right is 3
     **/ 
    public SwerveModule(TalonSRX drive, TalonSRX rotation, int place) { 
        this.drive = drive;
        this.rotation = rotation;
        this.place = place;
        rotationSetup(rotation);
        drive.setNeutralMode(NeutralMode.Brake);
        rotation.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * @param translationalThrottle
     *  Translational Speed (-1 to 1)
     * @param targetAngle
     *  Direction of Travel (0 to 360)
     **/
    public void set(double translationalThrottle, double targetAngle) {
        setModuleThrottle(translationalThrottle);
        setModuleAngle(targetAngle);
    }   

    /**
     * @param targetAngle
     *  Angle for the Module to Rotate to (0 to 360)
     **/
    public void setModuleAngle(double targetAngle) {
        rotation.set(ControlMode.Position, targetAngle);
    }

    /**
     * @param percentOutput
     *  Set the drive output (-1 to 1) for the Module
     */
    public void setModuleThrottle(double percentOutput) {
        drive.set(ControlMode.PercentOutput, percentOutput);
    }

    public void rotationSetup(TalonSRX talon) {
        talon.configSelectedFeedbackCoefficient(.2174); //Allows User to pass only an angle to the Rotation Motor
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder); 
        talon.setSelectedSensorPosition(0);
        talon.config_kP(0, 15);
    }
}
