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
        switch (step) {
            case 0:
                chassis.setMotors(0.5);
                state.state(chassis.PRange(20,INF),1);
                break;
            case 1:
                chassis.setMotors(0.5);
                if (chassis.aRange(1500,INF)) return true;
        }
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
