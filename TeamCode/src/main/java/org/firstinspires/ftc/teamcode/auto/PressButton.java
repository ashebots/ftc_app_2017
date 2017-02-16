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

    public PressButton(Chassis c, Scaler s, int t, boolean isBlue, VuforiaImaging vuforia, ColorImaging color) {
        chassis = c;
        foot = s;
        target = t;
        colorEqualsBlue = isBlue;
        this.vuforia = vuforia;
        this.color = color;
        //vuforia.start();
    }

    @Override
    public boolean states(int step) {
        //Calculate distances and angles
        //double distanceAway = vuforia.picDistance(target);
        double angle = -90;
        if (colorEqualsBlue){
            angle = 90; //if on blue rotate 180 degrees
        }
        double distanceToSide = color.beacon.getAnalysis().getCenter().x - 200;
        if (color.beacon.getAnalysis().getConfidence()<0.1) {
            distanceToSide *= -1;
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
                //AUTOCORRECT TEST
                spd = 1;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 20;
                double strSpeed = (1 - spd) * 0.7;
                if (distanceToSide < 0) strSpeed *= -1;
                if (angleFromPicture < 0) spd *= -1;
                if (distanceToSide == 0) strSpeed = spd = 0;
                spd /= 5;
                chassis.motorLeft.setPower(-strSpeed+spd);
                chassis.motorRight.setPower(strSpeed-spd);
                chassis.motorLeftB.setPower(strSpeed+spd);
                chassis.motorRightB.setPower(-strSpeed-spd);
                state.state(!(Math.abs(distanceToSide)<10) && distanceToSide != 0,3); // Don't know why this works, but it does.
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

                color.loop();
                boolean isBlueOnLeft = color.beacon.getAnalysis().isLeftBlue();
                if (isBlueOnLeft ^ colorEqualsBlue) {
                    state.state(true, 6); //one's true, one's false, therefore, our color is not on the left
                } else {
                    state.state(true, 5); //both are the same, therefore, our color is on the left
                }
                break;
            case (5): //move left
                chassis.omniDrive(0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>200,7); //change to 7
                break;
            case (6): //move left a little (to hit right button
                chassis.omniDrive(0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>40,7); //change to 7
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
                state.state(chassis.aRange(-INF, -foot.s(2)),9);
                break;
            case (9): //move back
                chassis.setMotors(0.5);
                state.state(chassis.aRange(foot.s(1), INF),10);
                break;
            case (10): //move back
                chassis.setMotors(0.5);
                state.state(chassis.aRange(foot.s(2),INF),11);
                break;
            case (11): //recenter
                if (distanceToSide<0) { //move onto the line (forward or back)
                    chassis.omniDrive(0.75,0);
                } else if (distanceToSide>0) chassis.omniDrive(-0.75,0);
                if (Math.abs(distanceToSide)<13 && distanceToSide != 0) return true;
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