package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearHolderToggle extends CommandGroup {

    public GearHolderToggle() {
        if(Robot.gearHolder.getInSwitchState() == true) {
        	addSequential(new GearHolderOut());
        }
        else if (Robot.gearHolder.getOutSwitchState() == true) {
        	addSequential(new GearHolderIn());
        }
        else {
        	System.out.println("Screw not on set position, defaulting to out");
        	addSequential(new GearHolderOut());
        }
    }
}
