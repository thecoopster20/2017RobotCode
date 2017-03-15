package org.usfirst.frc.team3602.robot;

import org.usfirst.frc.team3602.robot.commands.DriveStraight;
import org.usfirst.frc.team3602.robot.commands.PickupIn;
import org.usfirst.frc.team3602.robot.commands.PickupOut;
import org.usfirst.frc.team3602.robot.commands.ResetDriveSensors;
import org.usfirst.frc.team3602.robot.commands.RobotClimb;
import org.usfirst.frc.team3602.robot.commands.Turn;
import org.usfirst.frc.team3602.robot.triggers.PovToButton;
import org.usfirst.frc.team3602.robot.triggers.TriggerToButton;
import org.usfirst.frc.team3602.robot.commands.FireShooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	//creates the joysticks that will be used
	private Joystick gamepad = new Joystick(0);
	private Joystick auxController = new Joystick(1);
	
	public OI() {
		
		//creates buttons to call commands from the SmartDash
		SmartDashboard.putData("Reset Drive Sensors", new ResetDriveSensors());
		SmartDashboard.putData("Pickup In", new PickupIn());
		SmartDashboard.putData("Pickup Out", new PickupOut());
		SmartDashboard.putData("Lift That Bot", new RobotClimb(5));
		//SmartDashboard.putData("Vision Turn", new VisionTurn());
		
		//creates buttons to call commands from the joysticks
		JoystickButton FIRE = new JoystickButton(gamepad, 4);
		JoystickButton PickupIn = new JoystickButton(gamepad, 2);
		JoystickButton PickupOut = new JoystickButton(gamepad, 3);
		JoystickButton Climb = new JoystickButton(gamepad, 5);
		//assigns each button a command and when to execute the command
		
		FIRE.toggleWhenPressed(new FireShooter());
		PickupIn.toggleWhenPressed(new PickupIn());
		PickupOut.toggleWhenPressed(new PickupOut());
		Climb.whileHeld(new RobotClimb(-1));
		//Vision.whenActive(new VisionTurn());
		
		//assigns custom triggers a command and when to execute the command
		new PovToButton(gamepad, 0).whenActive(new DriveStraight(60));
		new PovToButton(gamepad, 180).whenActive(new DriveStraight(-60));
		//new PovToButton(gamepad, 0).whenActive(new VisionTurn());
	}
	
	public Joystick getGamepad() {
		return gamepad;
	}
	
	public Joystick getAuxController() {
		return auxController;
	}
}

//I'm a test comment to make sure that GitHub is working for this project
 