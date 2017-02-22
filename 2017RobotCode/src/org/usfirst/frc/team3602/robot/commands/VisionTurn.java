package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class VisionTurn extends Command {
private PIDController pid;
	
	private final double kP = .00525;
	private final double kI = 0;
	private final double kD = 0;

	public VisionTurn() {
		requires(Robot.driveTrain);
		pid = new PIDController(kP, kI, kD, new PIDSource() {
			PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

			@Override
			public double pidGet() {
				return (NetworkTable.getTable("GRIP").getNumber("centerX", 0) - (320/2));
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
				Robot.driveTrain.turn(d);
			}
		});
		pid.setPercentTolerance(5);
		pid.setSetpoint(0);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Get everything in a safe starting state.
		Robot.driveTrain.reset();
		pid.reset();
		pid.enable();
	}
	
	@Override
	protected void execute() {
		Robot.lightSwitch.lightOn();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (pid.onTarget() || !Robot.gearHolder.getCaptureSwitchState());
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Stop PID and the wheels
		pid.disable();
		Robot.driveTrain.manualControl(0, 0);
	}
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
	
}
