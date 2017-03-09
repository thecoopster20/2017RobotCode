package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightGearAuto extends CommandGroup {

	private final double distanceUntilTurn = 42;
	private final double turnTilVisionAngle = -20;

    public RightGearAuto() {
        
    	addSequential(new DriveStraight(distanceUntilTurn, 0.75));
    	addSequential(new Turn(turnTilVisionAngle, false));
    	addSequential(new AutoGear());
    }
}
