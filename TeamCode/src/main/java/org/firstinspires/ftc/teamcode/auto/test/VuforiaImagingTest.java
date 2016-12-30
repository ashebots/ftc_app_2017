package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.Image;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.BoolEvent;
import org.firstinspires.ftc.teamcode.auto.VuforiaImaging;

/**
 * Created by jezebelquit on 11/12/16.
 */
@Autonomous(name="VuforiaImaging")
public class VuforiaImagingTest extends AdvOpMode {
    int tick = 0;
    public VuforiaImagingTest() {
        msStuckDetectInit = 10000;
    }
    VuforiaImaging vuforia;
    BoolEvent a = new BoolEvent();
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
            tick++;
            side = vuforia.getColorSide(i);
        }
        telemetry.addLine("Tick: "+tick);
        telemetry.addLine("Distance: "+vuforia.picDistance(0));
        telemetry.addLine("Side Distance: "+vuforia.picSide(0));
        telemetry.addLine("Angle: "+vuforia.picAngle(0));
        telemetry.addLine("Is Blue on Left:"+side);
    }
}
