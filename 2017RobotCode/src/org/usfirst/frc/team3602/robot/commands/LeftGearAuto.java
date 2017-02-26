package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftGearAuto extends CommandGroup {
	
	private final double distanceUntilTurn = 42;
	private final double turnTilVisionAngle = 20;
	private final double backupDistance = -20;

    public LeftGearAuto() {
        
    	addSequential(new DriveStraight(distanceUntilTurn, false));
    	addSequential(new Turn(turnTilVisionAngle, false));
    	addSequential(new AutoGear());
    }
}
