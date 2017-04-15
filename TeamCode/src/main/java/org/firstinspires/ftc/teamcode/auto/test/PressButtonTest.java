package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.Chassis;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.PressButton;
import org.firstinspires.ftc.teamcode.auto.Ramp;
import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.lasarobotics.vision.opmode.ColorImaging;
import org.lasarobotics.vision.opmode.VisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Size;


@Autonomous(name = "PBTest", group = "Z")
public class PressButtonTest extends AdvOpMode
{
    public PressButtonTest() {
        msStuckDetectInit = 60000;
    }
    ColorImaging color;
    @Override
    public void init () {
        Chassis chassis = imuchassis("Left","Right","IMU");
        Scaler foot = new Scaler();
        foot.setTicksPer(615);

        color = new ColorImaging(hardwareMap);
        color.init();
        color.setCamera(Cameras.PRIMARY);
        color.setFrameSize(new Size(720, 1280));
        color.enableExtension(VisionOpMode.Extensions.BEACON);         //Beacon detection
        color.enableExtension(VisionOpMode.Extensions.ROTATION);       //Automatic screen rotation correction
        color.enableExtension(VisionOpMode.Extensions.CAMERA_CONTROL); //Manual camera control
        color.beacon.setAnalysisMethod(Beacon.AnalysisMethod.FAST);
        color.beacon.setColorToleranceRed(0);
        color.beacon.setColorToleranceBlue(0);
        color.rotation.setIsUsingSecondaryCamera(false);
        color.rotation.disableAutoRotate();
        color.rotation.setActivityOrientationFixed(ScreenOrientation.PORTRAIT);
        color.cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        color.cameraControl.setAutoExposureCompensation();
    }

    @Override
    public void loop () {
        telemetry.addData("Left",color.beacon.getAnalysis().getStateLeft().toString());
        telemetry.addData("Right",color.beacon.getAnalysis().getStateRight().toString());
    }

    @Override
    public void stop () {
    }
}
