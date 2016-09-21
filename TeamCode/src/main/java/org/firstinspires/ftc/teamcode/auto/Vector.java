package com.qualcomm.ftcrobotcontroller.opmodes.auto;

import org.ashebots.ftcandroidlib.complexOps.AutoRoutine;
import org.ashebots.ftcandroidlib.complexOps.IMUChassis;
import org.ashebots.ftcandroidlib.complexOps.Scaler;

/**
 * Created by apple on 9/15/16.
 */
public class Vector extends AutoRoutine {
    double angle;
    double distance;
    IMUChassis chassis;

    public Vector(double x1, double y1, double x2, double y2, Scaler s, IMUChassis c) {
        chassis = c;
        x2 -= x1;
        y2 -= y1;
        x2 = s.s(x2);
        y2 = s.s(y2);
        distance = Math.sqrt(x2*x2 + y2*y2);
        angle = c(Math.asin(x2/distance));
    }

    @Override
    public boolean states(int step) {
        switch (step) {
            case 0:
                double target = chassis.r(angle - chassis.angle());
                if (target<0) {
                    chassis.turnMotors(-0.2);
                } else {
                    chassis.turnMotors(0.2);
                }
                state.state(Math.abs(target)<5,1);
                break;
            case 1:
                chassis.setMotors(0.75);
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
