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
public class DriveStraight extends Command {
	private PIDController pidTurn;
	private PIDController pidDist;
	
	private final double kPTurn = .005;
	private final double kITurn = 0;
	private final double kDTurn = 0;
	private final double kPDist = .005;
	private final double kIDist = 0;
	private final double kDDist = 0;
	
	private double pidTurnValue;
	private double pidDistValue;

	public DriveStraight(double distance) {
		requires(Robot.driveTrain);
		pidTurn = new PIDController(kPTurn, kITurn, kDTurn, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return (Robot.driveTrain.getLeftEnc() - Robot.driveTrain.getRightEnc());
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
		pidTurn.setAbsoluteTolerance(0.01);
		pidTurn.setSetpoint(0);
		
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
			public void pidWrite(double d) {
				pidDistValue = d;
			}
		});
		pidDist.setAbsoluteTolerance(0.01);
		pidDist.setSetpoint(distance);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state.
		Robot.driveTrain.reset();
		pidTurn.reset();
		pidTurn.enable();
		pidDist.reset();
		pidDist.enable();
	}

	@Override
	protected void execute() {
		Robot.driveTrain.manualControl((pidTurnValue + pidDistValue), (pidTurnValue + pidDistValue));
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (pidDist.onTarget() || !Robot.gearHolder.getCaptureSwitchState());
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		pidTurn.disable();
		pidDist.disable();
		Robot.driveTrain.manualControl(0, 0);
	}
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
