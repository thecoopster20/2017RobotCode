package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Turn extends Command {
	
	private PIDController pidTurn;
	private double pidTurnValue;
	private double kPTurn = 0.1;
	private double kITurn = 0.005;
	private double kDTurn = 0;

    public Turn(double desiredAngle) {
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
		pidTurn.setSetpoint(desiredAngle);
		pidTurn.setInputRange(-180, 180);
		pidTurn.setOutputRange(-0.5, 0.5);
    }

    // Called just before this Command run the first time
    protected void initialize() {
    	pidTurn.reset();
    	Robot.driveTrain.reset();
    	pidTurn.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.manualArcadeControl(0, -pidTurnValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(pidTurn.getError()) <= 0.1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	pidTurn.reset();
    	SmartDashboard.putNumber("Last Command Angle", Robot.driveTrain.getDriveHeading());
    	Robot.driveTrain.reset();
    	Robot.driveTrain.stop();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
