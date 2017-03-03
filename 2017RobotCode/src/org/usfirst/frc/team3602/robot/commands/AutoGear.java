package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *Turns the bot via a camera, then drives to the gear, and then drops the gear
 *onto the peg.
 */
public class AutoGear extends CommandGroup {

    public AutoGear() {
        addSequential(new VisionTurn());
        addSequential(new DriveStraight(60, false));
        addSequential(new GearHolderIn());
        addSequential(new DriveStraight(6, true));
        addSequential(new GearHolderOut());
    }
}
