package org.usfirst.frc.team3602.robot.triggers;

import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;

/**
 *Turns an axis trigger into a button
 */
public class TriggerToButton extends Trigger {
	
	private Joystick joy;
	private int axis;
	
	public TriggerToButton(Joystick joy, int axis) {
		this.joy = joy;
		this.axis = axis;
	}

	@Override
    public boolean get() {
        if (joy.getRawAxis(axis) >= 0.5 || joy.getRawAxis(axis) <= -0.5) {
        	return true;
        }
        else {
        	return false;
        }
    }
}
