package org.firstinspires.ftc.teamcode.auto.test;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="Auto Test", group ="Test")
public class AutoTest extends AdvOpMode {
    ModularAuto a;
    Chassis c;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.CENTER_START,ModularAuto.CLOSE_PARK,ModularAuto.RAMP_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(700);
        c = imuchassis("Left","Right","IMU");
        a = new ModularAuto(sequence, false, c,s,mtr("Accelerator"),mtr("bottomSweeper"), srv("topSweeper"));
    }

    @Override
    public void loop() {
        a.run();
        telemetry.addData("Angle", c.angle());
        telemetry.addData("Pitch", c.pitch());
        telemetry.addData("Roll", c.roll());
        telemetry.addData("Left", c.motorLeft.getCurrentPosition()-c.loff);
        telemetry.addData("Right", c.motorRight.getCurrentPosition()-c.roff);
        double[] coords = a.next.coords;
        telemetry.addData("First Coordinate",coords[0]+", "+coords[1]);
        telemetry.addData("Second Coordinate",coords[2]+", "+coords[3]);
        telemetry.addData("State",a.getStep());
    }

    @Override
    public void stop() {
        a.stop();
    }
}
