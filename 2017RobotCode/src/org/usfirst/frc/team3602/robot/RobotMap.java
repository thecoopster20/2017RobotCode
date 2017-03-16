package org.usfirst.frc.team3602.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	//Create variables for motors, sensors, etc.	
	public static CANTalon driveLeftMotor;
	public static CANTalon driveRightMotor;
	public static CANTalon driveLeftSlaveMotor;
	public static CANTalon driveRightSlaveMotor;
	public static RobotDrive driveTrain;
	
	public static Encoder driveLeftEncoder;
	public static Encoder driveRightEncoder;
	
	public static Spark lightSwitchController;
	
	public static CANTalon ballPickupMotor;
	
	public static DigitalInput gearSwitch;
	
	public static CANTalon robotLiftMotor;
	
	public static CANTalon shooterMotor;
	public static CANTalon shooterFeeder;
	
	public static AHRS gyro;
	
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
		gyro = new AHRS(SPI.Port.kMXP);
		
		gearSwitch = new DigitalInput(5);
		
		//creates the shooter and feeder motors
		shooterMotor = new CANTalon(8);
		shooterMotor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        shooterMotor.reverseSensor(false);
        shooterMotor.configNominalOutputVoltage(+0.0f, -0.0f);
        shooterMotor.configPeakOutputVoltage(+12.0f, -12.0f);
        shooterMotor.setProfile(0);
        shooterMotor.setF(0.027);
        shooterMotor.setP(0.05);
        shooterMotor.setI(0);
		shooterFeeder = new CANTalon(11);
		
		//creates a motor controller to use as a light ring switch
		lightSwitchController = new Spark(0);
		
		//creates the motor for the pickup
		ballPickupMotor = new CANTalon(9);
		
		//creates the motor for the lifting winch
		robotLiftMotor = new CANTalon(10);
	}

}
