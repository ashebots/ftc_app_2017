package org.firstinspires.ftc.teamcode.auto.finished;
import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name="Simple Autonomous")
public class CenterNoShoot extends AdvOpMode {
    ModularAuto a;
    Chassis c;
    @Override
    public void init() {
        //double[][] sequence = {ModularAuto.CENTER_START,ModularAuto.CLOSE_PARK};
        c = imuchassismechanum("Left","Right","LeftBack","RightBack","IMU");
        Scaler s = new Scaler();
        s.setTicksPer(700);
        //a = new ModularAuto(sequence, true, c,s,mtr("Accelerator"),mtr("Sweeper"));
    }

    @Override
    public void loop() {
        /*a.run();
        telemetry.addData("Angle", c.angle());
        telemetry.addData("Pitch", c.pitch());
        telemetry.addData("Roll", c.roll());
        telemetry.addData("Left", c.motorLeft.getCurrentPosition()-c.loff);
        telemetry.addData("Right", c.motorRight.getCurrentPosition()-c.roff);
        double[] coords = a.next.coords;
        telemetry.addData("First Coordinate",coords[0]+", "+coords[1]);
        telemetry.addData("Second Coordinate",coords[2]+", "+coords[3]);
        telemetry.addData("State",a.getStep());*/
    }

    @Override
    public void stop() {
        a.stop();
    }
}
