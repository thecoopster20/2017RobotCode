package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Auto mode for dropping a gear onto the central peg.
 */
public class CenterGearAuto extends CommandGroup {
	
	private final double backupDistance = 36;

    public CenterGearAuto() {
    	
    	//self explanatory
    	addSequential(new AutoGear());
    	
    	//backs away from the peg for whatever distance we want
    	addSequential(new DriveStraight(backupDistance));
    }
}
