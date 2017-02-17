package org.usfirst.frc.team3602.robot.subsystems;

import org.usfirst.frc.team3602.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GearHolder extends Subsystem {
	
	private final CANTalon screw = RobotMap.gearHolderScrew;
	private final DigitalInput inSwitch = RobotMap.gearHolderInSwitch;
	private final DigitalInput outSwitch = RobotMap.gearHolderOutSwitch;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void gearHolderOut() {
    	screw.set(-1);
    }
    
    public void gearHolderIn() {
    	screw.set(1);
    }
    
    public boolean getInSwitchState() {
    	return inSwitch.get();
    }
    
    public boolean getOutSwitchState() {
    	return outSwitch.get();
    }
    
    public void stopScrew() {
    	screw.set(0);
    }
    
    public void log() {
    	SmartDashboard.putBoolean("Gear Out Switch", getOutSwitchState());
    	SmartDashboard.putBoolean("Gear In Switch", getInSwitchState());
    }
}

