package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraight extends Command {
	
	private double distance;
	private double pidTurnValue;
	private double pidDistValue;
	private PIDController pidTurn;
	private PIDController pidDist;
	private final double kPTurn = .1;
	private final double kITurn = 0;
	private final double kDTurn = 0;
	private final double kPDist = .1;
	private final double kIDist = 0;
	private final double kDDist = 0;

    public DriveStraight(double distance) {
        this.distance = distance;
        requires(Robot.driveTrain);
        
        pidTurn = new PIDController(kPTurn, kITurn, kDTurn, new PIDSource() {
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
			public void pidWrite(double t) {
				pidTurnValue = t;
			}
		});
		pidTurn.setAbsoluteTolerance(0.1);
		pidTurn.setSetpoint(0);
		pidTurn.setOutputRange(-0.75, 0.75);
		
		pidDist = new PIDController(kPDist, kIDist, kDDist, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return Robot.driveTrain.getDriveDistance();
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
			public void pidWrite(double t) {
				pidDistValue = t;
			}
		});
		pidTurn.setAbsoluteTolerance(0.1);
		pidTurn.setSetpoint(distance);
		pidTurn.setOutputRange(-0.75, 0.75);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	pidTurn.reset();
    	pidDist.reset();
    	Robot.driveTrain.reset();
    	pidTurn.enable();
    	pidDist.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.manualArcadeControl(pidDistValue, -pidTurnValue);
    }
    
    protected boolean isFinished() {
    	return Math.abs(pidDist.getError()) <= 0.1;
    }

    // Called once after timeout
    protected void end() {
    	pidTurn.disable();
    	pidDist.disable();
    	SmartDashboard.putNumber("Last Command Distance", Robot.driveTrain.getDriveDistance());
    	Robot.driveTrain.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
