package org.firstinspires.ftc.teamcode.auto.finished.drive_only;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="Ball Ramp (Red)", group ="Red")
public class Red_BallRamp extends AdvOpMode {
    ModularAuto a;
    IMUChassis c;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.CENTER_START,ModularAuto.CLOSE_PARK,ModularAuto.RAMP_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(2750); //change back to 1375
        c = imuchassis("Left","Right","IMU");
        a = new ModularAuto(sequence, false, c,s);
    }

    @Override
    public void loop() {
        a.run();
        telemetry.addData("Angle", c.angle());
        telemetry.addData("Pitch", c.pitch());
        telemetry.addData("Roll", c.roll());
        telemetry.addData("Target (Angle Difference)", a.next.target);
        double[] coords = a.next.coords;
        telemetry.addData("First Coordinate",coords[0]+", "+coords[1]);
        telemetry.addData("Second Coordinate",coords[2]+", "+coords[3]);
        telemetry.addData("State", a.getStep());
    }

    @Override
    public void stop() {
        a.stop();
    }
}
