package org.firstinspires.ftc.teamcode.auto;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/15/16.
 */
public class Ramp extends AutoRoutine {
    Chassis chassis;
    double angle;
    public Ramp(Chassis c, boolean blue) {
        chassis = c;
        if (blue) {
            angle = 135;
        } else {
            angle = -135;
        }
    }

    @Override
    public boolean states(int step) {
        if (step==0) {
            double difference = chassis.angle() - angle; //difference between the angles
            double spd = 0.5;
            if (Math.abs(difference) < 25) {
                spd = Math.abs(difference) / 50;
            }
            if (difference > 0) { //move the correct direction
                chassis.turnMotors(-spd);
            } else {
                chassis.turnMotors(spd);
            }
            state.state((Math.abs(difference) < 2.5), 1);
        } else {
            chassis.setMotors(-0.5);
            if (chassis.PRange(15, INF)) return true;
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
