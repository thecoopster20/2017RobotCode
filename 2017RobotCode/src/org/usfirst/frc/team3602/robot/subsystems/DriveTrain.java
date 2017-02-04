package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private final RobotDrive drive = RobotMap.driveTrain;
	private final Encoder leftEncoder = RobotMap.driveLeftEncoder;
	private final Encoder rightEncoder = RobotMap.driveRightEncoder;
	private final ADXRS450_Gyro gyro = RobotMap.driveGyro;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void driverControl(Joystick joy) {
    	//control the drive with a joystick
    	drive.arcadeDrive(joy.getRawAxis(1), joy.getRawAxis(4), false);
    }
    
    public void manualControl(double leftSpeed, double rightSpeed) {
    	//manually set the drive for autonomous use
    	drive.tankDrive(leftSpeed, rightSpeed);
    }
    
    public double getDriveDistance() {
    	//return the average of the two encoder's distances
    	double avgDistance = (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
    	return avgDistance;		
    }
    
    public double getDriveRate() {
    	//return the average of the two encoder's rates
    	double avgRate = (leftEncoder.getRate() + rightEncoder.getRate()) / 2;
    	return avgRate;
    }
    
    public double getDriveHeading() {
    	return gyro.getAngle();
    }
    
    public void reset() {
    	//zero encoders and make sure robot is stationary prior to the reset
    	drive.tankDrive(0, 0);
    	leftEncoder.reset();
    	rightEncoder.reset();
    	gyro.reset();
    }
    
    public void log() {
    	//write sensor values and other exciting information to the dash
    	SmartDashboard.putNumber("Left Encoder Distance", leftEncoder.getDistance());
    	SmartDashboard.putNumber("Right Encoder Distance", rightEncoder.getDistance());
    	SmartDashboard.putNumber("Left Encoder Rate", leftEncoder.getRate());
    	SmartDashboard.putNumber("Right Encoder Rate", rightEncoder.getRate());
    	SmartDashboard.putNumber("Gyro Angle", gyro.getAngle());
    }
}

