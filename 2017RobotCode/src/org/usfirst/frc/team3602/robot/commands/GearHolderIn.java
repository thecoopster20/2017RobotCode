package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearHolderIn extends Command {

    public GearHolderIn() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearHolder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.gearHolder.gearHolderIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.gearHolder.getInSwitchState();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.gearHolder.stopScrew();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
