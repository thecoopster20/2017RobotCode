package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallPickup extends Subsystem {
	
	private final CANTalon pickupMotor = RobotMap.ballPickupMotor;

    public void initDefaultCommand() {

    }
    
    public void runPickupIn() {
    	pickupMotor.set(1);
    }
    
    public void runPickupOut() {
    	pickupMotor.set(-1);
    }
    
    public void stopPickup() {
    	pickupMotor.set(0);
    }
}

