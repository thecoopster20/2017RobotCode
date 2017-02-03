package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private final RobotDrive drive = RobotMap.driveTrain;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void driverControl(Joystick joy) {
    	drive.arcadeDrive(joy.getRawAxis(1), joy.getRawAxis(4), false);
    }
    
    public void manualControl(double leftSpeed, double rightSpeed) {
    	drive.tankDrive(leftSpeed, rightSpeed);
    }
    
    public void reset() {
    	drive.tankDrive(0, 0);
    	//add sensor zeroing code
    }
    
    public void log() {
    	//write sensor values and other exciting information to the dash
    }
}

