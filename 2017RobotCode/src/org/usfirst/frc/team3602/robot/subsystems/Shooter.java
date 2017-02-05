package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Shooter extends Subsystem {
	
	private final CANTalon motor = RobotMap.shooterMotor;
	private double currentShootSpeed;
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setShooterSpeed(double shootSpeed) {
    	motor.set(shootSpeed);
    	currentShootSpeed = shootSpeed;
    }
    
    public void stopShooter() {
    	motor.set(0);
    }
    
    public void log() {
    	SmartDashboard.putNumber("Shooter Speed", currentShootSpeed);
    }
}

