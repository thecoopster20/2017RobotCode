package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;
import org.usfirst.frc.team3602.robot.commands.DriverControl;

import com.kauailabs.navx.frc.AHRS;

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
	
	//give the actuators/sensors from RobotMap a shorthand name
	private final RobotDrive drive = RobotMap.driveTrain;
	private final Encoder leftEncoder = RobotMap.driveLeftEncoder;
	private final Encoder rightEncoder = RobotMap.driveRightEncoder;
	private final AHRS gyro = RobotMap.gyro;
	
	private final double driveWheelDiameter = 4;
	private final double encoderCountsPerRev = 360;
	private final double pi = Math.PI;
	private final double distPerEncoderRev = (driveWheelDiameter * pi) / encoderCountsPerRev;
	
	private double moveAxis;
	private double rotateAxis;
	private double moveAxisSpeed;
	private double rotateAxisSpeed;
	private final double deadband = 0.1;
	private double avgDistance;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriverControl());
    }
    
    public DriveTrain() {
    	leftEncoder.setDistancePerPulse(distPerEncoderRev);
    	rightEncoder.setDistancePerPulse(distPerEncoderRev);
    	
    }
    
    public void driverControl(Joystick joy) {
    	//control the drive with a joystick and
    	//invert controls if the rear camera is active
    	
    	moveAxis = joy.getRawAxis(1);
    	rotateAxis = joy.getRawAxis(4);
    	
    	if(Math.abs(moveAxis) >= deadband) {
    		moveAxisSpeed = moveAxis;
    	}
    	else {
    		moveAxisSpeed = 0;
    	}
    	
    	if(Math.abs(rotateAxis) >= deadband) {
    		rotateAxisSpeed = rotateAxis;
    	}
    	else {
    		rotateAxisSpeed = 0;
    	}
   		drive.arcadeDrive(moveAxisSpeed, -rotateAxisSpeed);
    }
    
    public void manualControl(double leftSpeed, double rightSpeed) {
    	//manually set the drive for autonomous use
    	drive.tankDrive(leftSpeed, rightSpeed);
    }
    
    public void manualArcadeControl(double moveSpeed, double turnSpeed) {
    	drive.arcadeDrive(moveSpeed, turnSpeed);
    }
    
    public void turn(double turnSpeed) {
    	drive.arcadeDrive(0, turnSpeed);
    }
    
    public void straight(double straightSpeed) {
    	drive.arcadeDrive(straightSpeed, 0);
    }
    
    public double getLeftEnc() {
    	return Math.abs(leftEncoder.getDistance());
    }
    
    public double getRightEnc() {
    	return Math.abs(rightEncoder.getDistance());
    }
    
    public double getDriveDistance() {
    	//return the average of the two encoder's distances
    	avgDistance = (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
    	return avgDistance;		
    }
    
    public double getDriveRate() {
    	//return the average of the two encoder's rates in feet per second
    	//return the absolute value to ensure no negative values
    	double avgRate = (leftEncoder.getRate() + rightEncoder.getRate()) / 2;
    	return Math.abs(avgRate);
    }
    
    public double getDriveHeading() {
    	return gyro.getAngle();
    }
    
    public void reset() {
    	//zero encoders and make sure robot is stationary prior to the reset
    	drive.tankDrive(0, 0);
    	leftEncoder.reset();
    	rightEncoder.reset();
    	gyro.zeroYaw();
    }
    
    public void stop() {
    	drive.arcadeDrive(0, 0);
    }
    
    public void log() {
    	//write sensor values and other exciting information to the dash
    	SmartDashboard.putNumber("Left Encoder Distance(in)", leftEncoder.getDistance());
    	SmartDashboard.putNumber("Right Encoder Distance(in)", rightEncoder.getDistance());
    	SmartDashboard.putNumber("Average Encoder Distance", getDriveDistance());
    	SmartDashboard.putNumber("Left Encoder Rate(in/s)", leftEncoder.getRate());
    	SmartDashboard.putNumber("Right Encoder Rate(in/s)", rightEncoder.getRate());
    	SmartDashboard.putNumber("Average Encoder Rate", getDriveRate());
    }
}

