package org.firstinspires.ftc.teamcode.auto;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/15/16.
 */
public class Ramp extends AutoRoutine {
    IMUChassis chassis;

    public Ramp(IMUChassis c) {
        chassis = c;
    }

    @Override
    public boolean states(int step) {
        chassis.setMotors(0.5);
        if (chassis.PRange(5, INF)) return true;
        return false;
    }


    @Override
    public void stop() {
        chassis.stop();
    }

    @Override
    public void between() {
        chassis.stop();
        chassis.resetEncs();
    }
}
