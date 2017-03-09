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
	private final double kPTurn = .1;
	private final double kITurn = 0;
	private final double kDTurn = 0;
	
	private double baseDriveSpeed;
	private double targetDistance;
	
	private double pidTurnValue;

	public DriveStraight(double distance, double speed) {
		Robot.driveTrain.reset();
		requires(Robot.driveTrain);
		
		baseDriveSpeed = 0;
		targetDistance = 0;
		
		baseDriveSpeed = speed;
		targetDistance = distance;
		
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
		
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state.
		Robot.driveTrain.reset();
		pidTurn.reset();
		pidTurn.enable();
	}

	@Override
	protected void execute() {
		Robot.driveTrain.manualArcadeControl(-baseDriveSpeed, -pidTurnValue);
	}
	
	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		/*
		double currentDriveDistance = Robot.driveTrain.getDriveDistance();
		boolean gearSwitch = !Robot.gearHolder.getCaptureSwitchState();
		return ((currentDriveDistance >= targetDistance) || gearSwitch);
		*/
		return (Robot.driveTrain.getDriveDistance() >= targetDistance);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		pidTurn.disable();
		Robot.driveTrain.stop();
	}
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
