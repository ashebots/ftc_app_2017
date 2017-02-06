package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.hardware.Servo;

import org.ashebots.ftcandroidlib.complexOps.*;

public class PressButton extends AutoRoutine {
    Chassis chassis;
    VuforiaImaging vuforia;

    Scaler foot;

    int target;

    boolean colorEqualsBlue;

    public PressButton(Chassis c, Scaler s, int t, boolean isBlue, VuforiaImaging vuforia) {
        chassis = c;
        foot = s;
        target = t;
        colorEqualsBlue = isBlue;
        this.vuforia = vuforia;
        vuforia.start();
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
                double angle = -90;
                if (colorEqualsBlue){
                    angle = 90; //if on blue rotate 180 degrees
                }
                angle = chassis.r(angle - chassis.angle()); //angle difference
                if (angle<0) { //which angle to turn
                    chassis.turnMotors(-0.3);
                } else {
                    chassis.turnMotors(0.3);
                }
                state.state(Math.abs(angle)<5,1);

                break;
            case (1): //move to center line
                if (distanceToSide>0) { //move onto the line (forward or back)
                    chassis.omniDrive(1,0);
                } else chassis.omniDrive(-1,0);
                state.state(Math.abs(distanceToSide)<2,2);
                break;
            case (2): //turn to precise angle (VUF)
                double spd = 0.175;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100 * 0.875;
                if (angleFromPicture>0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromPicture)<2,3);
                break;
            case (3): //scan
                boolean isBlueOnLeft = vuforia.getColorSide(vuforia.getImage());
                if (isBlueOnLeft ^ colorEqualsBlue) {
                    state.state(true, 5); //one's true, one's false, therefore, our color is not on the left
                } else {
                    state.state(true, 4); //both are the same, therefore, our color is on the left
                }
                break;
            case (4): //move left
                chassis.omniDrive(1,0);
                state.state(distanceToSide>65,6);
                break;
            case (5): //move to press right
                chassis.omniDrive(1,0);
                state.state(distanceToSide>10,6);
                break;
            case (6): //return to precise angle
                spd = 0.175;
                if (Math.abs(angleFromPicture) < 20) spd = Math.abs(angleFromPicture) / 100 * 0.875;
                if (angleFromPicture>0) { //which angle to turn
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(angleFromPicture)<2,7);
                break;
            case (7): //press button
                chassis.setMotors(-0.5);
                state.state(chassis.aRange(-INF, -foot.s(2)),8);
                break;
            case (8): //move back
                chassis.setMotors(0.5);
                state.state(distanceAway>400,9);
                break;
            case (9): //recenter
                chassis.omniDrive(-1,0);
                if (Math.abs(distanceToSide)<5) return true;
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