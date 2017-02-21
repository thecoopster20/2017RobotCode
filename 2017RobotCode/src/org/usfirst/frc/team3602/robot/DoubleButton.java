package org.usfirst.frc.team3602.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *Turns a double button press into a button
 */
public class DoubleButton extends Trigger {
	
	private Joystick joy;
	private int buttonNumberOne;
	private int buttonNumberTwo;

	public DoubleButton(Joystick joy, int buttonNumberOne, int buttonNumberTwo) {
		this.joy = joy;
		this.buttonNumberOne = buttonNumberOne;
		this.buttonNumberTwo = buttonNumberTwo;
	}
	
	@Override
    public boolean get() {
        if (joy.getRawButton(buttonNumberOne) && joy.getRawButton(buttonNumberTwo)) {
        	return true;
        }
        else {
        	return false;
        }
    }
}
