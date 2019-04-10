
package frc.robot;

public class SwerveDrive {
    public final double L = 22;
    public final double W = 18;
    private WheelDrive backRight;
    private WheelDrive backLeft;
    private WheelDrive frontRight;
    private WheelDrive frontLeft;

    public SwerveDrive (WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft) {
        this.backRight = backRight;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
    }
    
    public void drive (double strafeX, double strafeY, double rotationX) {
        double r = Math.sqrt ((L * L) + (W * W));
        strafeY *= -1;
        double a = strafeX - rotationX * (L / r);
        double b = strafeX + rotationX * (L / r);
        double c = strafeY - rotationX * (W / r);
        double d = strafeY + rotationX * (W / r);
        
        double backRightSpeed = Math.sqrt ((a * a) + (d * d));
        double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
        double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
        double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));
        
        double backRightAngle = Math.atan2 (a, d) / Math.PI;
        double backLeftAngle = Math.atan2 (a, c) / Math.PI;
        double frontRightAngle = Math.atan2 (b, d) / Math.PI;
        double frontLeftAngle = Math.atan2 (b, c) / Math.PI;
        
        backRight.drive (backRightSpeed, backRightAngle);
        backLeft.drive (backLeftSpeed, backLeftAngle);
        frontRight.drive (frontRightSpeed, frontRightAngle);
        frontLeft.drive (frontLeftSpeed, frontLeftAngle);
    }
}
