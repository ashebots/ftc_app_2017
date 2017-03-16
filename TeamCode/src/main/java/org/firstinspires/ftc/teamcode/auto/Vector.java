package org.firstinspires.ftc.teamcode.auto;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/15/16.
 */
public class Vector extends AutoRoutine {
    public double angle;
    double distance;
    public double target;
    Chassis chassis;
    Scaler foot;
    public double[] coords = new double[4];

    public Vector(double x1, double y1, double x2, double y2, Scaler s, Chassis c) {
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
        angle = Math.asin(x2/distance); //still Pythag

        //Finds what direction it needs to turn
        if (y2<0) {
            if (angle>0) {
                angle = Math.PI - angle;
            } else {
                angle = -Math.PI - angle;
            }
        }
        angle = c(angle);  //C converts from radians to degrees
        foot = s;
    }

    @Override
    public boolean states(int step) {
        switch (step) {
            case 0:
                target = chassis.r(angle - chassis.angle()); //target it the angle of the wanted vector
                double spd = 0.5;
                if (Math.abs(target) < 50){
                    spd = Math.abs(target / 100); //gradual decrease
                }
                if (target<0) {
                    chassis.turnMotors(-spd);
                } else {
                    chassis.turnMotors(spd);
                }
                state.state(Math.abs(target)<5,1);
                break;
            case 1:
                spd = 1;
                if (distance-chassis.encoderLeft<foot.s(4)) spd = (distance-chassis.encoderLeft) / (foot.s(4)); //gradual decrease
                chassis.setMotors(-spd);
                if (chassis.aRange(distance,INF) || chassis.aRange(-INF, -distance)) {
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
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chassis.resetEncs();
    }
    public double c(double r) {
        r *= 180 / Math.PI;
        return chassis.r(r);
    }
}