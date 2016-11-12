package org.firstinspires.ftc.teamcode.auto;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/15/16.
 */
public class Ball extends AutoRoutine {
    AdvMotor sweeper;
    Timer timer = new Timer();
    boolean reset = true;

    public Ball (AdvMotor s) {
        sweeper = s;
    }

    @Override
    public boolean states(int step) {
        if (reset) {
            reset = false;
            timer.resetTimer();
        }
        sweeper.setMotor(-0.1);
        if (timer.tRange(3000)) {
            return true;
        }
        return false;
    }


    @Override
    public void stop() {
        sweeper.stop();
    }

    @Override
    public void between() {
    }
}
