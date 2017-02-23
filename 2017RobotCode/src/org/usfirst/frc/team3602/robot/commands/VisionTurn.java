package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionTurn extends CommandGroup {

    public VisionTurn() {
       addSequential(new LightOn());
       addSequential(new Turn(0, true));
       addSequential(new LightOff());
    }
}
