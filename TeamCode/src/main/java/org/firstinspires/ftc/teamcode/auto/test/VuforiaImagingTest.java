package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.Image;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.BoolEvent;
import org.ashebots.ftcandroidlib.complexOps.Timer;
import org.firstinspires.ftc.teamcode.auto.VuforiaImaging;

/**
 * Created by jezebelquit on 11/12/16.
 */
@Autonomous(name="VuforiaImaging",group = "Z")
public class VuforiaImagingTest extends AdvOpMode {
    public VuforiaImagingTest() {
        msStuckDetectInit = 10000;
    }
    VuforiaImaging vuforia;
    BoolEvent a = new BoolEvent();
    Timer t = new Timer();
    Timer stopwatch = new Timer();
    @Override
    public void init(){
        vuforia = new VuforiaImaging();
        vuforia.init();
    }
    public void start(){
        vuforia.start();
    }

    boolean side = false;
    public void loop() {
        if (a.parse(gamepad1.a).equals("PRESSED")) {
            Image i = vuforia.getImage();
            side = vuforia.getColorSide(i);
        }
        telemetry.addLine("Distance: "+vuforia.picDistance(0));
        telemetry.addLine("Side Distance: "+vuforia.picSide(0));
        telemetry.addLine("Angle: "+vuforia.picAngle(0));
        telemetry.addLine("Is Blue on Left: "+side);
        telemetry.addLine("Left Blue: "+vuforia.leftBlue);
        telemetry.addLine("Right Blue: "+vuforia.rightBlue);
        telemetry.addLine("Left Red: "+vuforia.leftRed);
        telemetry.addLine("Blue Score: "+vuforia.blue);
        telemetry.addLine("Red Score: "+vuforia.red);
        telemetry.addLine("Blueness: "+(vuforia.totalblue - vuforia.totalred));
    }
}
