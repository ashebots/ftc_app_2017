package org.firstinspires.ftc.teamcode.auto;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/15/16.
 */
public class Vector2 extends AutoRoutine {
    Chassis chassis;
    Scaler foot;
    public double[] coords = new double[4];
    double distance;
    double x;
    double y;

    public Vector2(double x1, double y1, double x2, double y2, Scaler s, Chassis c) {
        c.resetEncs();
        coords[0] = x1;
        coords[1] = y1;
        coords[2] = x2;
        coords[3] = y2;
        chassis = c;
        foot = s;

        x2 -= x1;
        y2 -= y1;
        distance = Math.sqrt(x2*x2+y2*y2);

        double angle = chassis.angle();
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));

        x = (x2*cos - y2*sin)/distance;
        y = (x2*sin + y2*cos)/distance;
    }

    @Override
    public boolean states(int step) {
        chassis.omniDrive(x, y);
        return chassis.getEncMechanum()>=foot.s(distance);
    }

    @Override
    public void stop() {
        chassis.stop();
    }

    @Override
    public void between() {
    }
}