package org.firstinspires.ftc.teamcode.auto;

import android.graphics.Camera;
import android.graphics.Color;
import android.hardware.camera2.CameraDevice;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.vuforia.Vuforia;

import org.ashebots.ftcandroidlib.complexOps.*;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.lasarobotics.vision.opmode.ColorImaging;

public class PressButton extends AutoRoutine {
    Chassis chassis;
    VuforiaImaging vuforia;
    ColorImaging color;

    Scaler foot;

    int target;

    boolean colorEqualsBlue;

    int encTicsToCenter;

    public boolean wallBash = false; // on second beacon, keep moving forward to make sure you press button (we won't have time to do anything else)

    public PressButton(Chassis c, Scaler s, int t, boolean isBlue, boolean wallBash, ColorImaging color) {
        chassis = c;
        foot = s;
        target = t;
        colorEqualsBlue = isBlue;
        this.vuforia = vuforia;
        this.color = color;
        this.wallBash = wallBash;
    }

    @Override
    public boolean states(int step) {
        color.loop();
        //Calculate distances and angles
        //double distanceAway = vuforia.picDistance(target);
        double angle = -90;
        if (colorEqualsBlue){
            angle = 90; //if on blue rotate 180 degrees
        }
        double distanceToSide = color.beacon.getAnalysis().getCenter().x - 200;
        if (color.beacon.getAnalysis().getConfidence()<0.2) {
            distanceToSide = -angle;
        }
        double angleFromPicture = chassis.r(angle - chassis.angle());
        //State Machine
        switch (step) {
            case (0): //turn to approx angle
                angle = angleFromPicture; //angle difference
                if (angle<0) { //which angle to turn
                    chassis.turnMotors(-0.3);
                } else {
                    chassis.turnMotors(0.3);
                }
                state.state(Math.abs(angle)<5,1);
                break;
            case (1): //turn to precise angle
                double spd = 0.175;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100 * 0.875;
                if (angleFromPicture<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromPicture)<2,2);
                break;
            case (2): //move to center line
                if (distanceToSide < 0) {
                    chassis.omniDrive(-0.75,0);
                } else {
                    chassis.omniDrive(0.75,0);
                }
                state.state(Math.abs(distanceToSide)<8,3);
                break;
                /*if (distanceToSide<0) { //move onto the line (forward or back)
                    chassis.omniDrive(0.7,0);
                } else if (distanceToSide>0) chassis.omniDrive(-0.7,0);
                state.state(Math.abs(distanceToSide)<20 && distanceToSide != 0,3);
                break;*/
            case (3): //turn to precise angle (VUF)
                spd = 0.175;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100 * 0.875;
                if (angleFromPicture<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromPicture)<2,4);
                break;
            case (4): //scan
                boolean isBlueOnLeft = color.beacon.getAnalysis().isLeftBlue();
                if (isBlueOnLeft ^ colorEqualsBlue) {
                    state.state(true, 6); //one's true, one's false, therefore, our color is not on the left
                } else {
                    state.state(true, 5); //both are the same, therefore, our color is on the left
                }
                break;
            case (5): //move left
                chassis.omniDrive(0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>225,7); //change to 7
                encTicsToCenter = 225;
                break;
            case (6): //move left a little (to hit right button
                chassis.omniDrive(0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>40,7); //change to 7
                encTicsToCenter = 40;
                break;
            case (7): //return to precise angle
                spd = 0.175;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100 * 0.875;
                if (angleFromPicture<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromPicture)<2,8);
                break;
            case (8): //press button
                chassis.setMotors(-0.25);
                state.state(chassis.aRange(-INF, -foot.s(2)) && !wallBash,9);
                break;
            case (9): //move back
                chassis.setMotors(0.5);
                state.state(chassis.aRange(foot.s(1.75), INF),10);
                break;
            case (10): //recenter
                chassis.omniDrive(-0.75,0);
                if (Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>encTicsToCenter) return true;
                break;
        }
        return false;
    }
    @Override
    public void stop() {
        chassis.stop();
        color.stop();
    }
    @Override
    public void between() {
        chassis.stop();
        chassis.resetEncs();
    }
}