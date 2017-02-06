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
    double timeToRun;
    public void start(){
        vuforia.start();
    }

    boolean side = false;
    int trues = 0;
    public void loop() {
        if (a.parse(gamepad1.a).equals("PRESSED")) {
            trues = 0;
            t.resetTimer();
            int scans = 0;
            while (scans < 10) {
                if (t.tRange(100)) {
                    scans++;
                    t.resetTimer();
                    Image i = vuforia.getImage();
                    if (vuforia.getColorSide(i)) {
                        trues++;
                    }
                }
            }
            side = trues < 5;
        }
        telemetry.addLine("Distance: "+vuforia.picDistance(0));
        telemetry.addLine("Side Distance: "+vuforia.picSide(0));
        telemetry.addLine("Angle: "+vuforia.picAngle(0));
        telemetry.addLine("Is Blue on Left:"+side);
    }
}
