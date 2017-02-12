package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Turn extends Command {
	private PIDController pid;
	
	private final double kP = .05;
	private final double kI = 0;
	private final double kD = 0;

    public Turn(double angle) {
    	requires(Robot.driveTrain);
    	pid = new PIDController(kP, kI, kP, new PIDSource() {
    		PIDSourceType m_sourceType = PIDSourceType.kDisplacement;
    		
    		@Override
    		public double pidGet() {
    			return Robot.driveTrain.getDriveHeading();
    		}
    		
    		@Override
    		public void setPIDSourceType(PIDSourceType pidSource) {
    			m_sourceType = pidSource;
    		}
    		
    		@Override
    		public PIDSourceType getPIDSourceType() {
    			return m_sourceType;
    		}
    	}, new PIDOutput() {
    		
    		@Override
    		public void pidWrite(double d) {
    			
    			if(angle > 0) {
    				Robot.driveTrain.turn(d);
    			}
    			
    			else {
    				Robot.driveTrain.turn(-d);
    			}
    				
    		}
    	});
    	pid.setOutputRange(-0.75, 0.75);
    	pid.setAbsoluteTolerance(1);
    	pid.setSetpoint(angle);
    }

    // Called just before this Command runs the first time
    
    @Override
    protected void initialize() {
    	Robot.driveTrain.reset();
    	pid.reset();
    	pid.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return pid.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	pid.disable();
    	Robot.driveTrain.manualControl(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
