package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.hardware.Servo;

import org.ashebots.ftcandroidlib.complexOps.*;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

public class PressButton extends AutoRoutine {
    ChassisMechanum chassis;
    Servo servo;
    VuforiaImaging vuforia = new VuforiaImaging();

    Scaler foot;

    int target;

    public PressButton(ChassisMechanum c, Scaler s, Servo srv, int t) {
        vuforia.startup();
        chassis = c;
        foot = s;
        servo = srv;
        target = t;
    }
    @Override
    public boolean states(int step) {
        //Calculate distances and angles
        double distanceAway = vuforia.picDistance(target);
        double distanceToSide = vuforia.picSide(target);
        double angleFromPicture = vuforia.picAngle(target);
        //State Machine
        switch (step) {
            case (0): //turn to approx angle (IMU)
                break;
            case (1): //turn to precise angle (VUF)
                break;
            case (2): //move to center line
                break;
            case (3): //scan
                break;
            case (4): //move left
                break;
            case (5): //move right
                break;
            case (6): //press button
                break;
            case (7): //move back
                break;
            case (8): //recenter
                break;
        }
        return false;
    }
    @Override
    public void stop() {
        chassis.stop();
    }
    @Override
    public void between() {
        chassis.resetEncs();
    }
}
