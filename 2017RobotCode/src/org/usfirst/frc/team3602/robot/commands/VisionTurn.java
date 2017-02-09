package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class VisionTurn extends Command {
	
	double visionDifference;

    public VisionTurn() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	visionDifference = NetworkTable.getTable("GRIP").getNumber("centerX", 0) - (640 / 2);
    	Robot.driveTrain.turn(visionDifference * .005);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (visionDifference >= -2 && visionDifference <= 2) {
        	return true;
        }
        else {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
