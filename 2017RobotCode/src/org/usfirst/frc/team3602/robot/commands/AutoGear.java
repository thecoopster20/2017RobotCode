package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGear extends CommandGroup {

    public AutoGear() {
        addSequential(new VisionTurn());
        addSequential(new DriveStraight(60));
        addSequential(new DropGear());
    }
}
