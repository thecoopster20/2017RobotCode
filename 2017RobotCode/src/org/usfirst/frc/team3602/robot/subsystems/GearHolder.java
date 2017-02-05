package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GearHolder extends Subsystem {
	
	private final Servo actuator = RobotMap.gearHolderActuator;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void gearHolderOut() {
    	actuator.setSpeed(1);
    }
    
    public void gearHolderIn() {
    	actuator.setSpeed(-1);
    }
    
    public double getHolderPos() {
    	return actuator.getPosition();
    }
    
    public void log() {
    	SmartDashboard.putNumber("Gear Holder Position", actuator.getPosition());
    }
}

