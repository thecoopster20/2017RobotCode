package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class VisionDistance extends Command {
	
	private double visionDifference;
	private final double targetArea = 7;
	private final double kP = .005;

    public VisionDistance() {
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	visionDifference = NetworkTable.getTable("GRIP").getNumber("area", 0) - targetArea; 
    	Robot.driveTrain.manualControl(visionDifference * kP, visionDifference * kP);
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
    	Robot.driveTrain.manualControl(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
