package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class RobotLifter extends Subsystem {
	
	private final CANTalon liftMotor = RobotMap.robotLiftMotor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void yeahMyBotLifts() {
    	liftMotor.set(-1);
    }
    
    public void takeABreakBro() {
    	liftMotor.set(0);
    }
}

