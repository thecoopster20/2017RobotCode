package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RobotClimb extends Command {
	
	private double speed;

    public RobotClimb(double speed) {
    	this.speed = speed;
        requires(Robot.robotLifter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.robotLifter.yeahMyBotLifts(speed);
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

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
