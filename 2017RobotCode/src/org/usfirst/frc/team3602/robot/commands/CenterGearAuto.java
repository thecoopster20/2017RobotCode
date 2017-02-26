package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Auto mode for dropping a gear onto the central peg.
 */
public class CenterGearAuto extends CommandGroup {

	
	
    public CenterGearAuto() {
    	addSequential(new DriveStraight(48, false));
    	addSequential(new AutoGear());
    }
}
