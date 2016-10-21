package org.firstinspires.ftc.teamcode.auto;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/15/16.
 */
public class Vector extends AutoRoutine {
    public double angle;
    double distance;
    public double target;
    IMUChassis chassis;
    public double[] coords = new double[4];

    public Vector(double x1, double y1, double x2, double y2, Scaler s, IMUChassis c) {
        coords[0] = x1;
        coords[1] = y1;
        coords[2] = x2;
        coords[3] = y2;
        chassis = c;
        x2 -= x1;
        y2 -= y1;
        x2 = s.s(x2);
        y2 = s.s(y2);
        distance = Math.sqrt(x2*x2 + y2*y2);
        angle = Math.asin(x2/distance);
        if (y2<0) {
            if (angle>0) {
                angle = Math.PI - angle;
            } else {
                angle = -Math.PI - angle;
            }
        }
        angle = c(angle);
    }

    @Override
    public boolean states(int step) {
        switch (step) {
            case 0:
                target = chassis.r(angle - chassis.angle());
                double spd = 0.75;
                if (Math.abs(target)<75) spd = Math.abs(target)/100;
                if (target<0) {
                    chassis.turnMotors(spd);
                } else {
                    chassis.turnMotors(-spd);
                }
                state.state(Math.abs(target)<5,1);
                break;
            case 1:
                chassis.setMotors(1);
                if (chassis.aRange(distance,INF)) {
                    return true;
                }
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
        chassis.stop();
        chassis.resetEncs();
    }
    public double c(double r) {
        r *= 180 / Math.PI;
        return chassis.r(r);
    }
}
