package org.firstinspires.ftc.teamcode.auto;

import android.graphics.Camera;
import android.graphics.Color;
import android.hardware.camera2.CameraDevice;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.vuforia.Vuforia;

import org.ashebots.ftcandroidlib.complexOps.*;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.lasarobotics.vision.opmode.ColorImaging;

public class PressButton2 extends AutoRoutine {
    Chassis chassis;
    VuforiaImaging vuforia;
    ColorImaging color;
    ColorSensor lineDetector;

    Scaler foot;

    int target;

    boolean colorEqualsBlue;

    int encTicsToCenter;
    Timer timer = new Timer();

    CenterAlignment centerAlignment = new CenterAlignment();

    public PressButton2(Chassis c, Scaler s, int t, boolean isBlue, ColorImaging color, ColorSensor lineDetector) {
        chassis = c;
        foot = s;
        target = t;
        colorEqualsBlue = isBlue;
        this.lineDetector = lineDetector;
        this.color = color;
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
                double spd = 0.2;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100;
                if (angleFromPicture<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromPicture)<2,2);
                break;
            case (2): //move forward to align with wall
                chassis.setMotors(-0.4);
                state.state(chassis.aRange(-INF, -foot.s(2.25)),20);
                break;
            case (3): //turn to precise angle
                spd = 0.2;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100;
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
                    state.state(true, 8); //both are the same, therefore, our color is on the left
                }
                break;
            case (5): //left (unused, it will hit anyway)
                chassis.omniDrive(-0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>80,7);
                encTicsToCenter = 80;
                break;
            case (6): //right
                chassis.omniDrive(-0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>150,7);
                encTicsToCenter = 150;
                break;
            case (7): //return to precise angle
                spd = 0.2;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100;
                if (angleFromPicture<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromPicture)<2,8);
                break;
            case (8): //press button
                chassis.setMotors(-0.4);
                state.state(chassis.aRange(-INF, -foot.s(2)),9);
                break;
            case (9): //move back
                chassis.setMotors(0.5);
                state.state(chassis.aRange(foot.s(1.15), INF),10);
                break;
            case (10): //recenter
                chassis.omniDrive(0.75,0);
                if (Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>encTicsToCenter) return true;
                break;


            //NEW CODE: THIS CAN BE EASILY DISCONNECTED FROM THE PROGRAM
            case (20): //move back a little
                chassis.setMotors(0.35);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>foot.s(1.25),21);
                break;
            case (21): //turn to precise angle
                spd = 0.2;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100;
                if (angleFromPicture<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromPicture)<2,22);
                break;
            case (22): //move to line and scan
                if (colorEqualsBlue) {
                    chassis.omniDrive(0.75,0);
                } else {
                    chassis.omniDrive(-0.75,0);
                }
                state.state(lineDetector.red()+lineDetector.green()+lineDetector.blue()>20,3); //new
                break;
            case (23): //obsolete
                encTicsToCenter = 300 - centerAlignment.findCenter();
                state.state(timer.tRange(1500),23);
                break;
            case (24): //obsolete
                chassis.omniDrive(0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>300,3); //150 was encticstocenter
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
        timer.resetTimer();
    }

    public void sideMove(int dir, double angleFromPicture) {
        double trnspd = 0.175;
        if (Math.abs(angleFromPicture) < 20) trnspd = Math.abs(angleFromPicture) / 100 * 0.875;
        if (angleFromPicture<0) { //which angle to turn
            trnspd *= -1;
        }

        chassis.motorLeft.setPower(dir*0.2+trnspd);
        chassis.motorRight.setPower(-dir*0.2-trnspd);
        chassis.motorLeftB.setPower(-dir*0.53+trnspd);
        chassis.motorRightB.setPower(dir*0.53-trnspd);
    }
}