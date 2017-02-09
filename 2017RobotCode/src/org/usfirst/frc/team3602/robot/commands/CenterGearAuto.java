package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CenterGearAuto extends CommandGroup {
	
	private final double distanceToDrop = 36;
	private final double backupDistance = 36;

    public CenterGearAuto() {
    	
    	//drives straight to the center peg from the wall
    	addSequential(new DriveStraight(distanceToDrop));
    	
    	//drops the gear onto the peg
    	addSequential(new DropGear());
    	
    	//backs away from the peg for whatever distance we want
    	addSequential(new DriveStraight(backupDistance));
    }
}
