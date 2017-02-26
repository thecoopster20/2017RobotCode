package org.usfirst.frc.team3602.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *Turns a POV into a button
 */
public class PovToButton extends Trigger {
	
	private Joystick joy;
	private int PovAngle;
	
	public PovToButton(Joystick joy, int PovAngle) {
		this.joy = joy;
		this.PovAngle = PovAngle;
	}

    public boolean get() {
        if (joy.getPOV() == PovAngle) {
        	return true;
        }
        else {
        	return false;
        }
    }
}