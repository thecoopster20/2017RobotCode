package org.usfirst.frc.team3602.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
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
