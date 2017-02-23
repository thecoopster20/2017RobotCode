package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class LightOn extends InstantCommand {

    public LightOn() {
        super();
        requires(Robot.lightSwitch);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.lightSwitch.lightOn();
    }

}
