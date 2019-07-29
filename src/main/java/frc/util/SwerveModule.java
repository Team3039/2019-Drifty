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
     * @param throttle
     *  Movement Speed (-1 to 1)
     * @param targetAngle
     *  Desired drving direction
     **/
    public void set(double throttle, double targetAngle) {
        drive.set(ControlMode.PercentOutput, throttle);
        rotation.set(ControlMode.Position, targetAngle);
    }

    /**
     * @param throttle
     *  Rotational Speed (-1 to 1) 
     */
    public void rotate(double throttle) {
        if(place == 0) {
            set(throttle, -45);
        }
        else if(place == 1) {
            set(-throttle, 45);
        }
        else if(place == 2) {
            set(throttle, 45);
        }
        else if(place == 3) {
            set(-throttle, -45);
        }
        else {
            set(0, 0);
        }
    }

    public void rotationSetup(TalonSRX talon) {
        talon.configSelectedFeedbackCoefficient(.2174);
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        talon.setSelectedSensorPosition(0);
        talon.config_kP(0, 15);
    }
}
