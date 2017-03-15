package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.*;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FireShooter extends Command {
	StringBuilder sb = new StringBuilder();
	int loops = 0;

    public FireShooter() {
    	requires(Robot.shooter);
        
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RobotMap.shooterMotor.changeControlMode(TalonControlMode.Speed);
    	RobotMap.shooterMotor.reverseOutput(false);
    	RobotMap.shooterMotor.set(5500);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.shooter.fireShooter(1);
    	if(RobotMap.shooterMotor.getSpeed() >= 5400){
    		Robot.shooter.runFeeder();
    	}
    	else {
    		Robot.shooter.stopFeeder();
    	}
    		
    	
    	double motorOutput = RobotMap.shooterMotor.getOutputVoltage() / RobotMap.shooterMotor.getBusVoltage();
    	
    	sb.append("\tout:");
    	sb.append(motorOutput);
    	sb.append("\tspd:");
    	sb.append(RobotMap.shooterMotor.getSpeed());
    	sb.append("\terr:");
    	sb.append(RobotMap.shooterMotor.getClosedLoopError());

    	
    	if(++loops >= 10) {
    		loops = 0;
    		System.out.println(sb.toString());
    	}
    	sb.setLength(0);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.shooterMotor.changeControlMode(TalonControlMode.PercentVbus);
    	Robot.shooter.stopShooter();
    	Robot.shooter.stopFeeder();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}