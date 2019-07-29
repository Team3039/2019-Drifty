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
     *  Translational Speed (-1 to 1) - Combined Joystick Input
     * @param rotationalThrottle
     *  Rotational Speed (-1 to 1) - Single Joystick Input
     * @param targetAngle
     *  Direction of Travel (0 to 360) - For Field-Orientated Control take this number and subtract your current angle
     **/
    public void set(double translationalThrottle, double rotationalThrottle, double targetAngle) {
    // TODO: Add Ether Swerve Math    
    }

    public void rotationSetup(TalonSRX talon) {
        talon.configSelectedFeedbackCoefficient(.2174);
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder); 
        talon.setSelectedSensorPosition(0);
        talon.config_kP(0, 15);
    }
}
