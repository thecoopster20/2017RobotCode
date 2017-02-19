
package org.usfirst.frc.team3602.robot;

import edu.wpi.cscore.CvSink;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3602.robot.commands.CenterGearAuto;
import org.usfirst.frc.team3602.robot.commands.LeftGearAuto;
import org.usfirst.frc.team3602.robot.commands.RightGearAuto;
import org.usfirst.frc.team3602.robot.subsystems.BallPickup;
import org.usfirst.frc.team3602.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3602.robot.subsystems.GearHolder;
import org.usfirst.frc.team3602.robot.subsystems.LightSwitch;
import org.usfirst.frc.team3602.robot.subsystems.RobotLifter;
import org.usfirst.frc.team3602.robot.subsystems.Shooter;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static DriveTrain driveTrain;
	public static OI oi;
	public static GearHolder gearHolder;
	public static Shooter shooter;
	public static LightSwitch lightSwitch;
	public static BallPickup ballPickup;
	public static RobotLifter robotLifter;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	Preferences prefs;
	
	public static boolean rearCameraAllowed;
	public Point bullseyeCenter;
	public int bullseyeRadius;
	public int bullseyeHeight;
	public int bullseyeWidth;
	public Scalar red = new Scalar(0, 0, 255);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		RobotMap.init();
		driveTrain = new DriveTrain();
		gearHolder = new GearHolder();
		shooter = new Shooter();
		lightSwitch = new LightSwitch();
		ballPickup = new BallPickup();
		robotLifter = new RobotLifter();
		
		oi = new OI();
		
		bullseyeCenter = new Point();
		
		//creates an auto selector button and adds the modes
		SmartDashboard.putData("Auto Mode", chooser);
		chooser.addObject("Center Gear", new CenterGearAuto());
		chooser.addObject("Left Gear", new LeftGearAuto());
		chooser.addObject("Right Gear", new RightGearAuto());
		
		prefs = Preferences.getInstance();
		bullseyeCenter.x = prefs.getDouble("BullseyeCenterX", 160);
		bullseyeCenter.y = prefs.getDouble("BullseyeCenter Y", 120);
		bullseyeRadius = prefs.getInt("BullseyeRadius", 100);
		
		
		//outputs all of the subsystems to the dash
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData(driveTrain);
		SmartDashboard.putData(gearHolder);
		SmartDashboard.putData(shooter);
		SmartDashboard.putData(lightSwitch);
		SmartDashboard.putData(ballPickup);
		SmartDashboard.putData(robotLifter);
		
		//creates a separate thread for the camera switcher to run on
		Thread t = new Thread(() -> {
			
    			rearCameraAllowed = false;
    			boolean bullseyeOn = false;
    		
    			//creates a camera object and sets the resolution and FPS
    			UsbCamera frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
    			frontCamera.setResolution(320, 240);
    			frontCamera.setFPS(30);
            
    			UsbCamera rearCamera = CameraServer.getInstance().startAutomaticCapture(1);
    			rearCamera.setResolution(320, 240);
    			rearCamera.setFPS(30);
            
    			//creates two sinks that allow us to grab the images from each camera
    			//then creates a switcher stream that we feed whatever image we want to
    			CvSink cvSink1 = CameraServer.getInstance().getVideo(rearCamera);
    			CvSink cvSink2 = CameraServer.getInstance().getVideo(frontCamera);
    			CvSource outputStream = CameraServer.getInstance().putVideo("Switcher", 320, 240);
            
    			//creates a new mat for image capture
    			Mat image = new Mat();
            
    			//switches camera feed on a button press or the manual setting of the rearCameraAllowed boolean
    			while(!Thread.interrupted()) {
            	
    				if(oi.getGamepad().getRawButton(7)) {
    					rearCameraAllowed = !rearCameraAllowed;
    					Timer.delay(0.5);
    				}
            	
    				if(rearCameraAllowed){
    					cvSink2.setEnabled(false);
    					cvSink1.setEnabled(true);
    					cvSink1.grabFrame(image);
    				} else{
    					cvSink1.setEnabled(false);
    					cvSink2.setEnabled(true);
    					cvSink2.grabFrame(image);     
    				}
    				
    				if(oi.getGamepad().getRawButton(8)) {
    					bullseyeOn = !bullseyeOn;
    					Timer.delay(0.5);
    				}
    				
    				//Draws a bullseye from the SmartDash preferences as long as the shooting camera is active
    				
    				if(bullseyeOn) {
    					Imgproc.circle(image, bullseyeCenter, bullseyeRadius, red, 2);
    					Imgproc.line(image, bullseyeCenter, new Point(bullseyeCenter.x + bullseyeRadius, bullseyeCenter.y), red, 2);
    					Imgproc.line(image, bullseyeCenter, new Point(bullseyeCenter.x - bullseyeRadius, bullseyeCenter.y), red, 2);
    					Imgproc.line(image, bullseyeCenter, new Point(bullseyeCenter.x,  bullseyeCenter.y + bullseyeRadius), red, 2);
    					Imgproc.line(image, bullseyeCenter, new Point(bullseyeCenter.x, bullseyeCenter.y - bullseyeRadius), red, 2);
    				}
    				
    				//outputs the desired image to the switcher
    				outputStream.putFrame(image);
    				SmartDashboard.putBoolean("Rear Camera On?", rearCameraAllowed);
    				
    			}
            
        	});
        	t.start();
        	
        	
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		log();
		lightSwitch.lightOff();
		driveTrain.reset();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();
		
		/*
		 String autoSelected = SmartDashboard.getString("Auto Selector",
		 "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		  = new MyAutoCommand(); break; case "Default Auto": default:
		 autonomousCommand = new ExampleCommand(); break; }
		 */
		 

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		log();
		lightSwitch.lightOn();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		log();
		lightSwitch.lightOn();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	//calls each subsystem's log function for dash writing functionality
	public void log() {
		driveTrain.log();
		gearHolder.log();
		shooter.log();
	}
}
