package org.usfirst.frc.team3602.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
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
	public static CANTalon driveLeftMotor;
	public static CANTalon driveRightMotor;
	public static CANTalon driveLeftSlaveMotor;
	public static CANTalon driveRightSlaveMotor;
	public static RobotDrive driveTrain;
	public static Encoder driveLeftEncoder;
	public static Encoder driveRightEncoder;
	public static ADXRS450_Gyro driveGyro;
	public static Spark lightSwitchController;
	public static CANTalon ballPickupMotor;
	public static CANTalon robotLiftMotor;
	
	//gear holder actuator
	public static CANTalon gearHolderScrew;
	public static DigitalInput gearHolderInSwitch;
	public static DigitalInput gearHolderOutSwitch;
	public static DigitalInput gearHolderCaptureSwitch;
	
	//shooter motor
	public static CANTalon shooterMotor;
	public static CANTalon shooterFeeder;
	public static Spark shooterBeater;
	
	
	//Assign motors and such their ports and other initial properties
	public static void init() {
		
		//reserve CAN IDs 1-3 for RIO, PDB, and PCM
		driveLeftMotor = new CANTalon(4);
		driveRightMotor = new CANTalon(5);
		
		//creates a drive motor and enslaves it to the other left side motor
		driveLeftSlaveMotor = new CANTalon(6);
		driveLeftSlaveMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveLeftSlaveMotor.set(driveLeftMotor.getDeviceID());
		
		//creates another enslaved drive motor
		driveRightSlaveMotor = new CANTalon(7);
		driveRightSlaveMotor.changeControlMode(CANTalon.TalonControlMode.Follower);
		driveRightSlaveMotor.set(driveRightMotor.getDeviceID());
		
		//creates a RobotDrive for our control, and overrides the safety
		//function to shut those stupid timing errors up
		driveTrain = new RobotDrive(driveLeftMotor, driveRightMotor);
		driveTrain.setSafetyEnabled(false);
		driveTrain.setSensitivity(0.5);
		
		//one encoder has to be reversed to ensure both output for the same direction
		driveLeftEncoder = new Encoder(3, 4, false, EncodingType.k4X);
		driveRightEncoder = new Encoder(1, 2, true, EncodingType.k4X);                                    
		
		//creates a gyro for getting the heading of the robot.
		driveGyro = new ADXRS450_Gyro();
		
		//creates our screw motor, switches, and the extra gear sensing switch
		gearHolderScrew = new CANTalon(11);
		gearHolderInSwitch = new DigitalInput(5);
		gearHolderOutSwitch = new DigitalInput(6);
		gearHolderCaptureSwitch = new DigitalInput(7);
		
		//creates the shooter and feeder motors
		shooterMotor = new CANTalon(8);
		shooterFeeder = new CANTalon(12);
		shooterBeater = new Spark(2);
		
		//creates a motor controller to use as a light ring switch
		lightSwitchController = new Spark(1);
		
		//creates the motor for the pickup
		ballPickupMotor = new CANTalon(9);
		
		//creates the motor for the lifting winch
		robotLiftMotor = new CANTalon(10);
	}

}
