package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class GearActuatorTimed extends TimedCommand {

    public GearActuatorTimed(double timeout) {
        super(timeout);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.gearHolder);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.gearHolder.getHolderPos() == 0){
    		Robot.gearHolder.gearHolderOut();
    	}
    	else if (Robot.gearHolder.getHolderPos() == 1) {
    		Robot.gearHolder.gearHolderIn();
    	}
    	else {
    		System.out.println("Error, actuator no worky");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Called once after timeout
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
