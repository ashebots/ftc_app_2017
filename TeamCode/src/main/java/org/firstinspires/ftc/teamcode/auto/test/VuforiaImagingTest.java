package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.auto.VuforiaImaging;

/**
 * Created by jezebelquit on 11/12/16.
 */
@Autonomous(name="VuforiaImaging")
public class VuforiaImagingTest extends OpMode {
    VuforiaImaging vuforia;
    @Override
    public void init(){
        vuforia = new VuforiaImaging();
        vuforia.startup();
    }

    public void loop(){
        telemetry.addLine("Distance: "+vuforia.picDistance(0));
        telemetry.addLine("Side Distance: "+vuforia.picSide(0));
        telemetry.addLine("Angle: "+vuforia.picAngle(0));
        telemetry.addLine("Image: "+vuforia.getImage().getHeight());
        telemetry.update();
    }

}
