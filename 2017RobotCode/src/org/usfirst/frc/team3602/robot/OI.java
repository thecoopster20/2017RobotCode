package org.usfirst.frc.team3602.robot;

import org.usfirst.frc.team3602.robot.commands.DropGear;
import org.usfirst.frc.team3602.robot.commands.GearHolderToggle;
import org.usfirst.frc.team3602.robot.commands.PickupIn;
import org.usfirst.frc.team3602.robot.commands.PickupOut;
import org.usfirst.frc.team3602.robot.commands.ResetDriveSensors;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick gamepad = new Joystick(0);
	private Joystick auxController = new Joystick(1);
	
	public OI() {
		SmartDashboard.putData("Gear Toggle", new GearHolderToggle());
		SmartDashboard.putData("Reset Drive Sensors", new ResetDriveSensors());
		SmartDashboard.putData("Pickup In", new PickupIn());
		SmartDashboard.putData("Pickup Out", new PickupOut());
		
	}
	
	public Joystick getGamepad() {
		return gamepad;
	}
	
	public Joystick getAuxController() {
		return auxController;
	}
}

//I'm a test comment to make sure that GitHub is working for this project
