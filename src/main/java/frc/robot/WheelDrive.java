
package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;

public class WheelDrive {
    private WPI_TalonSRX drvMotor;
    private WPI_TalonSRX rotMotor;
    private PIDController pidCtrl;
    private final double MAX_VOLTS = 4.95;
    
    public WheelDrive (int angleMotor, int drvMotor, int encoder) {
        this.rotMotor = new WPI_TalonSRX (angleMotor);
        this.drvMotor = new WPI_TalonSRX (drvMotor);

        pidCtrl = new PIDController (1, 0, 0, new AnalogInput (encoder), this.rotMotor);

        pidCtrl.setOutputRange (-.9, .9);
        pidCtrl.enable();
    }
    
    public void drive (double speed, double angle) {
        drvMotor.set(speed);

        double setpoint = angle * (MAX_VOLTS * 0.5) + (MAX_VOLTS * 0.5); // Optimization offset can be calculated here.
        if (setpoint < 0) {
            setpoint = MAX_VOLTS + setpoint;
        }
        if (setpoint > MAX_VOLTS) {
            setpoint = setpoint - MAX_VOLTS;
        }

        pidCtrl.setSetpoint (setpoint);
    }
}



