package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class LightSwitch extends Subsystem {
	
	private final Spark controller = RobotMap.lightSwitchController;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void lightOn() {
    	controller.set(1);
    }
    
    public void lightOff() {
    	controller.stopMotor();
    }
}

