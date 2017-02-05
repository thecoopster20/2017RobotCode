package org.usfirst.frc.team3602.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//Create variables for motors, sensors, etc.
	
	//Drive Motors and Sensors
	public static CANTalon driveFrontLeftMotor;
	public static CANTalon driveFrontRightMotor;
	public static CANTalon driveRearLeftMotor;
	public static CANTalon driveRearRightMotor;
	public static RobotDrive driveTrain;
	public static Encoder driveLeftEncoder;
	public static Encoder driveRightEncoder;
	public static ADXRS450_Gyro driveGyro;
	public static Spark lightSwitchController;
	
	//gear holder actuator
	public static Servo gearHolderActuator;
	
	//shooter motor
	public static CANTalon shooterMotor;
	
	
	//Assign motors and such their ports and other initial properties
	public static void init() {
		
		//reserve CAN IDs 1-3 for RIO, PDB, and PCM
		driveFrontLeftMotor = new CANTalon(4);
		driveFrontRightMotor = new CANTalon(5);
		driveRearLeftMotor = new CANTalon(6);
		driveRearRightMotor = new CANTalon(7);
		driveTrain = new RobotDrive(driveFrontLeftMotor, driveRearLeftMotor, driveFrontRightMotor, driveRearRightMotor);
		driveTrain.setSensitivity(0.5);
		
		//one encoder has to be reversed to ensure both output for the same direction
		driveLeftEncoder = new Encoder(3, 4, false, EncodingType.k4X);
		driveRightEncoder = new Encoder(1, 2, true, EncodingType.k4X);
		
		driveGyro = new ADXRS450_Gyro();
		
		//set the Servo to use 1ms and 2ms PWM widths for min and max position
		gearHolderActuator = new Servo(1);
		gearHolderActuator.setBounds(2, 0, 0, 0, 1);
		
		shooterMotor = new CANTalon(8);
		
		lightSwitchController = new Spark(0);
	}

}
