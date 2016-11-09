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
    public AdvMotor sweeper;
    public AdvMotor accelerator;
    boolean blue = false;

    //COORDINATES in feet
    static public double[] RAMP = {1.5,1.5}; //gets onto the ramp
    static public double[] RAMP_PARK = {1.5, 1.5}; //gets onto the ramp fully
    static public double[] CLOSE_PARK = {6,5.25};
    static public double[] FAR_PARK = {5.25,6};
    static public double[] CLOSE_BEACON = {1.5,5};
    static public double[] FAR_BEACON = {1.5,9};
    static public double[] CLOSE_BEACON_PUSH = {1.5,5};
    static public double[] FAR_BEACON_PUSH = {1.5,9};
    static public double[] CLOSE_HUB = {6,3.5};
    static public double[] FAR_HUB = {4,3.5};
    static public double[] CLOSE_THROW = {6,2.88};
    static public double[] FAR_THROW = {4,2.88};
    static public double[] LEFT_START = {4,0.75};
    static public double[] CENTER_START = {6,0.75};
    static public double[] RIGHT_START = {8,0.75};

    int wait = 0;
    Timer timer = new Timer();

    public ModularAuto(double[][] position, boolean blue, IMUChassis c, Scaler s, AdvMotor accelerator, AdvMotor sweeper) {
        this.blue = blue;
        //puts these values in the program
        pos = position;
        chassis = c;
        foot = s;
        this.accelerator = accelerator;
        this.sweeper = sweeper;
        //between calculates the next move. Called between because it runs between each step
        between();
    }

    public ModularAuto(double[][] position, boolean blue, IMUChassis c, Scaler s, AdvMotor accelerator, AdvMotor sweeper, int time) {
        this.blue = blue;
        //puts these values in the program
        pos = position;
        chassis = c;
        foot = s;
        //between calculates the next move. Called between because it runs between each step
        between();
        wait = time;
        this.accelerator = accelerator;
        this.sweeper = sweeper;
    }

    @Override
    public void stop() {
        next.stop();
        chassis.stop();
    }

    @Override
    //this code does
    public void between() {
        int s = getStep();
        if (blue) { //switch
            next = new Vector(12 - pos[s][0], pos[s][1], 12 - pos[s + 1][0], pos[s + 1][1], foot, chassis); //blue
        } else next = new Vector(pos[s][0],pos[s][1],pos[s+1][0],pos[s+1][1],foot,chassis); //red

        if (pos[s+1]==ModularAuto.RAMP_PARK) {
            special = new Ramp(chassis);
        }
        else if (pos[s+1]==ModularAuto.CLOSE_BEACON_PUSH) {
            special = new PressButton(chassis, foot, servo, 0);
        }
        else if (pos[s+1]==ModularAuto.FAR_BEACON_PUSH) {
            special = new PressButton(chassis, foot, servo, 0);
        }
        else if (pos[s+1]==ModularAuto.CLOSE_THROW) {
            special = new ShootBall(chassis, sweeper, accelerator, -20.3);
        }
        else if (pos[s+1]==ModularAuto.FAR_THROW) {
            special = new ShootBall(chassis, sweeper, accelerator, 20.3);
        }
        else {
            special = null;
        }
        if (special != null) {
            special.reset();
        }
    }

    boolean reset = true;

    @Override
    public boolean states(int step) {
        if (reset) {
            reset = false;
            timer.resetTimer();
        }
        if (!timer.tRange(wait)) {
            return false;
        }
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