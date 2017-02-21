package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.Timer;
import org.firstinspires.ftc.teamcode.auto.CenterAlignment;
import org.firstinspires.ftc.teamcode.auto.VuforiaImaging;
import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.lasarobotics.vision.opmode.ColorImaging;
import org.lasarobotics.vision.opmode.VisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Size;

/**
 * Basic Vision Sample
 * <p/>
 * Use this in a typical op mode. A VisionOpMode allows using
 * Vision Extensions, which do a lot of processing for you. Just enable the extension
 * and set its options to your preference!
 * <p/>
 * The VisionOpMode is the base of all vision processing and other styles of OpMode
 * even extend the VisionOpMode class! Be sure to extend it if writing your own OpMode structure.
 */
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Vision Test", group ="TeleOp")
public class BasicVisionSample extends AdvOpMode {
    public BasicVisionSample() {
        msStuckDetectInit = 60000;
    }
    ColorImaging vision;
    CenterAlignment center;
    Timer timer;
    //VuforiaImaging vuforia;
    @Override
    public void init() {
        vision = new ColorImaging(hardwareMap);
        //vuforia = new VuforiaImaging();
        //vuforia.init();
        vision.init();

        /**
         * Set the camera used for detection
         * PRIMARY = Front-facing, larger camera
         * SECONDARY = Screen-facing, "selfie" camera :D
         **/
        vision.setCamera(Cameras.PRIMARY);

        /**
         * Set the frame size
         * Larger = sometimes more accurate, but also much slower
         * After this method runs, it will set the "width" and "height" of the frame
         **/
        vision.setFrameSize(new Size(720, 1280));

        /**
         * Enable extensions. Use what you need.
         * If you turn on the BEACON extension, it's best to turn on ROTATION too.
         */
        vision.enableExtension(VisionOpMode.Extensions.BEACON);         //Beacon detection
        vision.enableExtension(VisionOpMode.Extensions.ROTATION);       //Automatic screen rotation correction
        vision.enableExtension(VisionOpMode.Extensions.CAMERA_CONTROL); //Manual camera control

        /**
         * Set the beacon analysis method
         * Try them all and see what works!
         */
        vision.beacon.setAnalysisMethod(Beacon.AnalysisMethod.FAST);

        /**
         * Set color tolerances
         * 0 is default, -1 is minimum and 1 is maximum tolerance
         */
        vision.beacon.setColorToleranceRed(0);
        vision.beacon.setColorToleranceBlue(0);

        /**
         * Set analysis boundary
         * You should comment this to use the entire screen and uncomment only if
         * you want faster analysis at the cost of not using the entire frame.
         * This is also particularly useful if you know approximately where the beacon is
         * as this will eliminate parts of the frame which may cause problems
         * This will not work on some methods, such as COMPLEX
         **/
        //beacon.setAnalysisBounds(new Rectangle(new Point(width / 2, height / 2), width - 200, 200));

        /**
         * Set the rotation parameters of the screen
         * If colors are being flipped or output appears consistently incorrect, try changing these.
         *
         * First, tell the extension whether you are using a secondary camera
         * (or in some devices, a front-facing camera that reverses some colors).
         *
         * It's a good idea to disable global auto rotate in Android settings. You can do this
         * by calling disableAutoRotate() or enableAutoRotate().
         *
         * It's also a good idea to force the phone into a specific orientation (or auto rotate) by
         * calling either setActivityOrientationAutoRotate() or setActivityOrientationFixed(). If
         * you don't, the camera reader may have problems reading the current orientation.
         */
        vision.rotation.setIsUsingSecondaryCamera(false);
        vision.rotation.disableAutoRotate();
        vision.rotation.setActivityOrientationFixed(ScreenOrientation.PORTRAIT);

        /**
         * Set camera control extension preferences
         *
         * Enabling manual settings will improve analysis rate and may lead to better results under
         * tested conditions. If the environment changes, expect to change these values.
         */
        vision.cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        vision.cameraControl.setAutoExposureCompensation();
        center = new CenterAlignment();
        timer = new Timer();
    }

    @Override
    public void start() {
        timer.resetTimer();
    }
    @Override
    public void loop() {
        vision.loop();

        telemetry.addData("Beacon Color", vision.beacon.getAnalysis().getColorString());
        telemetry.addData("Beacon Center", vision.beacon.getAnalysis().getLocationString());
        telemetry.addData("Beacon Confidence", vision.beacon.getAnalysis().getConfidence());
        telemetry.addData("Beacon Buttons", vision.beacon.getAnalysis().getButtonString());
        telemetry.addData("Screen Rotation", vision.rotation.getScreenOrientationActual());
        telemetry.addData("Frame Rate", vision.fps.getFPSString() + " FPS");
        telemetry.addData("Frame Size", "Width: " + vision.width + " Height: " + vision.height);
        telemetry.addData("Timer", timer.time()/10);
        telemetry.addData("Saw Beacon",vision.beacon.getAnalysis().isBeaconFound());
        if (timer.time() < 3000) {
            double xPos = 1-(Math.abs(vision.beacon.getAnalysis().getCenter().x - 200)/25);
            if (xPos < 0) xPos = 0;
            center.inputData((int)(timer.time()/10),vision.beacon.getAnalysis().isBeaconFound(),xPos,vision.beacon.getAnalysis().getConfidence());
        } else {
            telemetry.addData("Center", center.findCenter());
        }
    }

    @Override
    public void stop() {
        vision.stop();
    }
}
