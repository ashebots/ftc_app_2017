package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ashebots.ftcandroidlib.complexOps.AutoRoutine;
import org.ashebots.ftcandroidlib.complexOps.Chassis;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.ashebots.ftcandroidlib.complexOps.Timer;
import org.lasarobotics.vision.opmode.ColorImaging;

public class PressButton3 extends AutoRoutine {
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

    public PressButton3(Chassis c, Scaler s, int t, boolean isBlue, ColorImaging color, ColorSensor lineDetector) {
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
        double angleFromBeacon = chassis.r(angle - chassis.angle());
        //State Machine
        switch (step) {
            case (0): //turn to approx angle
                if (angleFromBeacon<0) { //which angle to turn
                    chassis.turnMotors(-0.3);
                } else {
                    chassis.turnMotors(0.3);
                }
                state.state(Math.abs(angleFromBeacon)<5,1);
                break;
            case (1): //turn to precise angle
                double spd = 0.2;
                if (Math.abs(angleFromBeacon) < 20) spd = Math.abs(angleFromBeacon) / 100;
                if (angleFromBeacon<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                if (target==1) {
                    state.state(Math.abs(angleFromBeacon)<2,2);
                } else {
                    state.state(Math.abs(angleFromBeacon)<2,22);
                }
                break;
            case (2): //move forward to align with wall
                chassis.setMotors(-0.4);
                state.state(chassis.aRange(-INF, -foot.s(2.25)),20);
                break;
            case (3): //turn to precise angle
                spd = 0.2;
                if (Math.abs(angleFromBeacon) < 20) spd = Math.abs(angleFromBeacon) / 100;
                if (angleFromBeacon<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromBeacon)<2,4);
                break;
            case (4): //scan
                boolean isBlueOnLeft = color.beacon.getAnalysis().isLeftBlue();
                boolean isBlueOnRight = color.beacon.getAnalysis().isRightBlue();

                /*if (isBlueOnLeft == isBlueOnRight && isBlueOnLeft == colorEqualsBlue) {
                    return true;
                }*/
                if (isBlueOnLeft ^ colorEqualsBlue) {
                    state.state(true, 6); //one's true, one's false, therefore, our color is not on the left
                } else {
                    state.state(true, 5); //both are the same, therefore, our color is on the left
                }
                break;
            case (5): //left
                chassis.omniDrive(0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>60,7);
                encTicsToCenter = -60;
                break;
            case (6): //right
                chassis.omniDrive(-0.75,0);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>90,7);
                encTicsToCenter = 90;
                break;
            case (7): //return to precise angle
                spd = 0.2;
                if (Math.abs(angleFromBeacon) < 20) spd = Math.abs(angleFromBeacon) / 100;
                if (angleFromBeacon<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromBeacon)<2,8);
                break;
            case (8): //press button
                spd = -0.4;
                if (chassis.aRange(-INF, -foot.s(1))) spd = -0.15;
                chassis.setMotors(spd);
                state.state(chassis.aRange(-INF, -foot.s(2)),9);
                break;
            case (9): //move back
                chassis.setMotors(0.5);
                state.state(chassis.aRange(foot.s(1.25), INF),10);
                break;
            case (10): //recenter
                if (encTicsToCenter < 0) {
                    chassis.omniDrive(-0.75,0);
                } else {
                    chassis.omniDrive(0.75,0);
                }
                //state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>encTicsToCenter,11);
                if (Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>encTicsToCenter) {
                    if (target == 1) {
                        return true;
                    }
                    target++;
                    state.state(true,22);
                }
                break;
            /*case (11): //correct
                isBlueOnLeft = color.beacon.getAnalysis().isLeftBl ue();
                isBlueOnRight = color.beacon.getAnalysis().isRightBlue();
                if (isBlueOnLeft == isBlueOnRight && isBlueOnLeft != colorEqualsBlue) {
                    state.state(true,12);
                } else {
                    return true; //right (or we missed and give up)
                }
                break;
            case (12): //wait for cooldown
                encTicsToCenter = 0;
                state.state(timer.tRange(4000),8);
                break;*/

            //NEW CODE: THIS CAN BE EASILY DISCONNECTED FROM THE PROGRAM
            case (20): //move back a little
                chassis.setMotors(0.35);
                state.state(Math.abs(chassis.motorRight.getCurrentPosition()-chassis.roff)>foot.s(1.15),21);
                break;
            case (21): //turn to precise angle
                spd = 0.2;
                if (Math.abs(angleFromBeacon) < 20) spd = Math.abs(angleFromBeacon) / 100;
                if (angleFromBeacon<0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromBeacon)<2,22);
                break;
            case (22): //move to line and scan
                if (colorEqualsBlue) {
                    sideMove(1,angleFromBeacon);
                } else {
                    sideMove(-1,angleFromBeacon);
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
        double trnspd = 0.1;
        if (Math.abs(angleFromPicture) < 20) trnspd = Math.abs(angleFromPicture) / 100 * 0.875;
        if (angleFromPicture>0) { //which angle to turn
            trnspd *= -1;
        }

        chassis.motorLeft.setPower(dir*0.2+trnspd);
        chassis.motorRight.setPower(-dir*0.2-trnspd);
        chassis.motorLeftB.setPower(-dir*0.53+trnspd);
        chassis.motorRightB.setPower(dir*0.53-trnspd);
    }
}