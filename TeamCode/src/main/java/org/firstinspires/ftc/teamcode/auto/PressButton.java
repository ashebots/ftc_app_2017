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
    Chassis chassis;
    Servo servo;
    VuforiaImaging vuforia = new VuforiaImaging();

    Scaler foot;

    int target;

    public PressButton(Chassis c, Scaler s, int t) {
        vuforia.startup();
        chassis = c;
        foot = s;
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
                double angle = 0;
                if (target > 1) angle = 180;
                angle = chassis.r(angle - chassis.angle()); //angle difference
                double spd = 0.375;
                if (angle<0) {
                    chassis.turnMotors(0.375);
                } else {
                    chassis.turnMotors(-0.375);
                }
                state.state(Math.abs(angle)<5,1);
                break;
            case (1): //turn to precise angle (VUF)
                angle = 0;
                if (target < 2) angle = 180;
                angle = chassis.r(angle - angleFromPicture); //angle difference
                if (angle<0) {
                    chassis.turnMotors(0.05);
                } else {
                    chassis.turnMotors(-0.05);
                }
                state.state(Math.abs(angle)<1,2);
                break;
            case (2): //move to center line
                if (distanceToSide<0) {
                    chassis.setMotors(0.1);
                } else chassis.setMotors(-0.1);
                state.state(Math.abs(distanceToSide)<5,3);
                break;
            case (3): //scan
                break;
            case (4): //move left
                break;
            case (5): //move right
                break;
            case (6): //press button //REQUIRES MECHANUM
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
        chassis.stop();
    }
}
