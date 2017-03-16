package org.usfirst.frc.team3602.robot.commands;

import org.usfirst.frc.team3602.robot.VisionAngleCalculator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class VisionTurn extends CommandGroup {
	
	private double targAngle = 0;
	private VisionAngleCalculator calc;
	
    public VisionTurn() {
    	
    	targAngle = 0;
    	calc = new VisionAngleCalculator();
    	targAngle = calc.getHorizontalAngle();
    	
    	addSequential(new LightOn());
    	addSequential(new WaitCommand(2));
    	addSequential(new Turn(targAngle));
    	addSequential(new LightOff());
    }	
}
