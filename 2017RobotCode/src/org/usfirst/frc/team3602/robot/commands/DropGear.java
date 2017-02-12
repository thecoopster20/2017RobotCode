package org.usfirst.frc.team3602.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DropGear extends CommandGroup {

    public DropGear() {
        addSequential(new GearHolderIn());
        Timer.delay(1);
        addSequential(new GearHolderOut());
    }
}
