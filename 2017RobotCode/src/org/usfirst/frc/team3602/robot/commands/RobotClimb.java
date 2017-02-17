package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class RobotClimb extends TimedCommand {

    public RobotClimb(double timeout) {
        super(timeout);
        requires(Robot.robotLifter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.robotLifter.yeahMyBotLifts();
    }

    // Called once after timeout
    protected void end() {
    	Robot.robotLifter.takeABreakBro();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
