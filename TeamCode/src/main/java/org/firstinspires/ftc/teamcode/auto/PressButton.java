package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.hardware.Servo;

import org.ashebots.ftcandroidlib.complexOps.*;

public class PressButton extends AutoRoutine {
    Chassis chassis;
    VuforiaImaging vuforia = new VuforiaImaging();

    Scaler foot;

    int target;

    boolean colorEqualsBlue;

    public PressButton(Chassis c, Scaler s, int t, boolean isBlue) {
        vuforia.init();
        vuforia.start();
        chassis = c;
        foot = s;
        target = t;
        colorEqualsBlue = isBlue;
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
                AddTelemertyData("State", "IMU turn");
                double angle = 0;
                if (target > 1){
                    angle = 180; //if on blue rotate 180 degrees
                }
                angle = chassis.r(angle - chassis.angle()); //angle difference
                double spd = 0.375;
                if (angle<0) { //which angle to turn
                    chassis.turnMotors(0.375);
                } else {
                    chassis.turnMotors(-0.375);
                }
                state.state(Math.abs(angle)<5,1);

                break;
            case (1): //turn to precise angle (VUF)
                AddTelemertyData("State", "VUF turn");
                angle = 0;
                if (target > 1){
                    angle = 180; //if on blue rotate 180 degrees
                }
                angle = chassis.r(angle - angleFromPicture); //angle difference
                if (angle<0) { //which angle to turn
                    chassis.turnMotors(0.05);
                } else {
                    chassis.turnMotors(-0.05);
                }
                state.state(Math.abs(angle)<1,2);

                break;
            case (2): //move to center line
                AddTelemertyData("State", "Align with targets");
                if (distanceToSide<0) { //move onto the line (forward or back)
                    chassis.setMotors(0.1);
                } else chassis.setMotors(-0.1);
                state.state(Math.abs(distanceToSide)<5,3);
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
                chassis.setMotors(0.1);
                state.state(distanceToSide<-76,6);
                break;
            case (5): //move right
                chassis.setMotors(-0.1);
                state.state(distanceToSide>76,6);
                break;
            case (6): //press button
                chassis.omniDrive(0.1,0);
                state.state(distanceAway<80,7);
                break;
            case (7): //move back
                chassis.omniDrive(-0.1,0);
                state.state(distanceAway>300,8);
                break;
            case (8): //recenter
                if (distanceToSide<0) { //pick a direction (based on which button you pressed)
                    chassis.setMotors(-0.1);
                } else {
                    chassis.setMotors(0.1);
                }
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
