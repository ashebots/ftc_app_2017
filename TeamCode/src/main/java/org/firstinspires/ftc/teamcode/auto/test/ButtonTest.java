package org.firstinspires.ftc.teamcode.auto.test;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="Button Test", group ="Test")
public class ButtonTest extends AdvOpMode {
    ModularAuto a;
    IMUChassis c;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.LEFT_START,ModularAuto.FAR_THROW,ModularAuto.FAR_BEACON,ModularAuto.CLOSE_BEACON,ModularAuto.FAR_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(700);
        c = imuchassis("Left","Right","IMU");
        a = new ModularAuto(sequence, false, c,s,mtr("Accelerator"),mtr("Sweeper"));
    }

    @Override
    public void loop() {
        a.run();
        telemetry.addData("Angle", c.angle());
        telemetry.addData("Pitch", c.pitch());
        telemetry.addData("Roll", c.roll());
        telemetry.addData("Left", c.motorLeft.getCurrentPosition()-c.loff);
        telemetry.addData("Right", c.motorRight.getCurrentPosition()-c.roff);
        telemetry.addData("Angle Target", a.next.angle);
        telemetry.addData("Angle Difference", a.next.target);
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
