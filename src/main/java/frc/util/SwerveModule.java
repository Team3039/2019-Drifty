package frc.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class SwerveModule {

    TalonSRX drive;
    TalonSRX rotation;
    
    /**
     * @param drive 
     *  Controls the wheel's velocity
     * @param rotation 
     *  Controls the wheel's position
     **/ 
    public SwerveModule(TalonSRX drive, TalonSRX rotation) { 
        this.drive = drive;
        this.rotation = rotation;
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
     **/
    public void setModuleThrottle(double percentOutput) {
        drive.set(ControlMode.PercentOutput, percentOutput);
    }

    /**
     * @param inverted
     *  Reverses Normal Output of Motor if "true"
     **/
    public void setDriveInverted(boolean inverted) {
        drive.setInverted(InvertType.InvertMotorOutput);
    }

    public void rotationSetup(TalonSRX talon) {
        talon.configSelectedFeedbackCoefficient(.2174); //Allows User to pass only an angle to the Rotation Motor
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder); 
        talon.setSelectedSensorPosition(0);
        talon.config_kP(0, 15);
    }
    
    public void zeroSensors() {
        rotation.setSelectedSensorPosition(0);
        drive.setSelectedSensorPosition(0);
    }

    //Limit Ouputs to [0-360)
    public double modulate360(double units){
        return units %= 360;
    }

    public double getModuleAngle() {
        return modulate360(rotation.getSelectedSensorPosition());
    }
}
