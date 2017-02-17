package org.firstinspires.ftc.teamcode.auto;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/15/16.
 */
public class Ball extends AutoRoutine {
    AdvMotor sweeper;
    Chassis chassis;
    Timer timer = new Timer();
    boolean reset = true;

    public Ball (AdvMotor s, Chassis c) {
        sweeper = s;
        chassis = c;
    }

    @Override
    public boolean states(int step) {
        if (reset) {
            reset = false;
            timer.resetTimer();
        }
        chassis.setMotors(0.5);
        if (timer.tRange(250)) {
            return true;
        }
        return false;
    }


    @Override
    public void stop() {
        sweeper.stop();
        chassis.stop();
    }

    @Override
    public void between() {
    }
}
