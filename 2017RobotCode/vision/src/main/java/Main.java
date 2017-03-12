import java.util.ArrayList;



import edu.wpi.first.wpilibj.networktables.*;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.*;
import edu.wpi.cscore.*;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Main {
	
	private static double centerXOne = 0;
	private static double centerYOne = 0;
	private static double centerXTwo = 0;
	private static double centerYTwo = 0;
	private static double centerXAvg = 0;
	private static double centerYAvg = 0;
	private static double area = 0;
	
  public static void main(String[] args) {
    // Loads our OpenCV library. This MUST be included
    System.loadLibrary("opencv_java310");

    // Connect NetworkTables, and get access to the publishing table
    NetworkTable.setClientMode();
    // Set your team number here
    NetworkTable.setTeam(3602);

    NetworkTable.initialize();
    


    // This is the network port you want to stream the raw received image to
    // By rules, this has to be between 1180 and 1190, so 1185 is a good choice
    int streamPort = 1183;

    // This stores our reference to our mjpeg server for streaming the input image
    MjpegServer inputStream = new MjpegServer("MJPEG Server", streamPort);

    // Selecting a Camera
    // Uncomment one of the 2 following camera options
    // The top one receives a stream from another device, and performs operations based on that
    // On windows, this one must be used since USB is not supported
    // The bottom one opens a USB camera, and performs operations on that, along with streaming
    // the input image so other devices can see it.

    // HTTP Camera
    
    // This is our camera name from the robot. this can be set in your robot code with the following command
    // CameraServer.getInstance().startAutomaticCapture("YourCameraNameHere");
    // "USB Camera 0" is the default if no string is specified
    String cameraName = "Switcher";
    HttpCamera camera = setHttpCamera(cameraName, inputStream);
    // It is possible for the camera to be null. If it is, that means no camera could
    // be found using NetworkTables to connect to. Create an HttpCamera by giving a specified stream
    // Note if this happens, no restream will be created
    if (camera == null) {
      camera = new HttpCamera("CoprocessorCamera", "http://roborio-3602-frc.local:1183/?action=stream");
      inputStream.setSource(camera);
    }
    
    
      

    /***********************************************/

    // USB Camera
    /*
    // This gets the image from a USB camera 
    // Usually this will be on device 0, but there are other overloads
    // that can be used
    UsbCamera camera = setUsbCamera(0, inputStream);
    // Set the resolution for our camera, since this is over USB
    camera.setResolution(640,480);
    */

    // This creates a CvSink for us to use. This grabs images from our selected camera, 
    // and will allow us to use those images in opencv
    CvSink imageSink = new CvSink("CV Image Grabber");
    imageSink.setSource(camera);

    // This creates a CvSource to use. This will take in a Mat image that has had OpenCV operations
    // operations 

    // All Mats and Lists should be stored outside the loop to avoid allocations
    // as they are expensive to create
    Mat inputImage = new Mat();
    Mat hsv = new Mat();
    
    GripPipeline proc = new GripPipeline();

    // Infinitely process image
    while (true) {
      // Grab a frame. If it has a frame time of 0, there was an error.
      // Just skip and continue
      long frameTime = imageSink.grabFrame(inputImage);
      if (frameTime == 0) {
    	  //SmartDashboard.putString("Vision State", "Aquisition Error");
    	  continue;
      }

      // Below is where you would do your OpenCV operations on the provided image
      // The sample below just changes color source to HSV
      proc.process(inputImage);
      
      int targetsFound = proc.convexHullsOutput().size();
      
      if(targetsFound >= 2) {
			Rect r1 = Imgproc.boundingRect(proc.convexHullsOutput().get(0));
			Rect r2 = Imgproc.boundingRect(proc.convexHullsOutput().get(1));

			centerXOne = r1.x + (r1.width / 2);
			centerYOne = r1.y + (r1.height/2);
			centerXTwo = r2.x + (r2.width/2);
			centerYTwo = r2.y + (r2.height/2);
			centerXAvg = (centerXOne + centerXTwo)/2;
			centerYAvg = (centerYOne + centerYTwo)/2;
			area = (double)((r2.x + r2.width) - r1.x) * r1.height;
			NetworkTable.getTable("GRIP").putNumber("centerX", centerXAvg);
			NetworkTable.getTable("GRIP").putNumber("centerY", centerYAvg);
			NetworkTable.getTable("GRIP").putNumber("area", area);
			Imgproc.rectangle(inputImage, r1.tl(), r2.br(), new Scalar(0, 0, 5));
			//SmartDashboard.putString("Vision State", "Two or more targets found, processing");
		}
      else {
    	 // SmartDashboard.putString("Vision State", "Less than two targets found, not processing");
      }
      
      

      // Here is where you would write a processed image that you want to restreams
      // This will most likely be a marked up image of what the camera sees
      // For now, we are just going to stream the HSV image
    }
  }

  private static HttpCamera setHttpCamera(String cameraName, MjpegServer server) {
    // Start by grabbing the camera from NetworkTables
    NetworkTable publishingTable = NetworkTable.getTable("CameraPublisher");
    // Wait for robot to connect. Allow this to be attempted indefinitely
    while (true) {
      try {
        if (publishingTable.getSubTables().size() > 0) {
          break;
        }
        Thread.sleep(500);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    HttpCamera camera = null;
    if (!publishingTable.containsSubTable(cameraName)) {
      return null;
    }
    ITable cameraTable = publishingTable.getSubTable(cameraName);
    String[] urls = cameraTable.getStringArray("streams", null);
    if (urls == null) {
      return null;
    }
    ArrayList<String> fixedUrls = new ArrayList<String>();
    for (String url : urls) {
      if (url.startsWith("mjpg")) {
        fixedUrls.add(url.split(":", 2)[1]);
      }
    }
    camera = new HttpCamera("CoprocessorCamera", fixedUrls.toArray(new String[0]));
    server.setSource(camera);
    return camera;
  }

  private static UsbCamera setUsbCamera(int cameraId, MjpegServer server) {
    // This gets the image from a USB camera 
    // Usually this will be on device 0, but there are other overloads
    // that can be used
    UsbCamera camera = new UsbCamera("CoprocessorCamera", cameraId);
    server.setSource(camera);
    return camera;
  }
}