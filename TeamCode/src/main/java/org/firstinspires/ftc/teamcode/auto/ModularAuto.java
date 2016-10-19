package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.hardware.Servo;

import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/15/16.
 */
public class ModularAuto extends AutoRoutine {
    double[][] pos;
    IMUChassis chassis;
    Scaler foot;
    public Vector next;
    AutoRoutine special;
    Servo servo;

    //COORDINATES
    static public double[] RAMP = {1.5,1.5}; //gets onto the ramp
    static public double[] RAMP_PARK = {1.5, 1.5}; //gets onto the ramp fully
    static public double[] CLOSE_PARK = {6,5};
    static public double[] FAR_PARK = {5,6};
    static public double[] CLOSE_BEACON = {1.5,5};
    static public double[] FAR_BEACON = {1.5,9};
    static public double[] CLOSE_BEACON_PUSH = {1.5,5};
    static public double[] FAR_BEACON_PUSH = {1.5,9};
    static public double[] CLOSE_THROW = {6,4};
    static public double[] FAR_THROW = {4,4};
    static public double[] CLOSE_THROW_SCORE = {6,4};
    static public double[] FAR_THROW_SCORE = {4,4};
    static public double[] LEFT_START = {5,1};
    static public double[] CENTER_START = {6,1};
    static public double[] RIGHT_START = {8,1};

    public ModularAuto(double[][] position, boolean blue, IMUChassis c, Scaler s) {
        if (blue) {
            //reverses X values of all coordinates
            for (int i = 0; i < position.length; i++) {
                position[i][0] = 12-position[i][0];
            }
        }
        //puts these values in the program
        pos = position;
        chassis = c;
        foot = s;
        //between calculates the next move. Called between because it runs between each step
        between();
    }

    @Override
    public void stop() {
        next.stop();
        chassis.stop();
    }

    @Override
    public void between() {
        int s = getStep();
        next = new Vector(pos[s][0],pos[s][1],pos[s+1][0],pos[s+1][1],foot,chassis);

        if (pos[s+1]==ModularAuto.RAMP_PARK) {
            special = new Ramp(chassis);
        }
        else if (pos[s+1]==ModularAuto.CLOSE_BEACON_PUSH) {
            special = new PressButton(chassis, foot, servo, 0);
        }
        else if (pos[s+1]==ModularAuto.FAR_BEACON_PUSH) {
            special = new PressButton(chassis, foot, servo, 0);
        }
        else if (pos[s+1]==ModularAuto.CLOSE_THROW_SCORE) {
            special = new ShootBall();
        }
        else if (pos[s+1]==ModularAuto.FAR_THROW_SCORE) {
            special = new ShootBall();
        }
        else {
            special = null;
        }
        if (special != null) {
            special.reset();
        }
    }

    @Override
    public boolean states(int step) {
        chassis.calc();
        if (next.getStep()==-1 && special !=null && special.getStep()>-1) {
            special.run();
        } else {
            next.run();
        }
        state.state(next.getStep()==-1 && (special==null || special.getStep()==-1),step+1);
        if (getStep()+1==pos.length) {
            return true;
        }
        return false;
    }
}