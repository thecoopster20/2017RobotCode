package org.usfirst.frc.team3602.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionAngleCalculator {
	
	private final double fieldOfView = 92;
	private final double cameraWidth = 360;
	private final double focalLength = cameraWidth / (2*Math.tan(fieldOfView/2));
	private final double imageCenterX = 160;
	
	private double targetCenterX;
	
	
	public VisionAngleCalculator() {
		targetCenterX = 0;
	}
	
	public double getHorizontalAngle() {
		
		targetCenterX = NetworkTable.getTable("GRIP").getNumber("centerX", 0);
		
		double totalToAverage = 0;
		double averagedAngle = 0;
		
		for(int i = 10; i <= 0; i--) {
			totalToAverage = totalToAverage + Math.atan((targetCenterX - imageCenterX) / focalLength);
		}
		
		averagedAngle = totalToAverage / 10;
		
		return averagedAngle;		
	}

}
