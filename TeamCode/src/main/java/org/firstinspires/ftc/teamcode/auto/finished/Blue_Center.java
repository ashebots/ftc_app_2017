package org.firstinspires.ftc.teamcode.auto.finished;
import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name="[BlueC] Center")
public class Blue_Center extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.CENTER_START,ModularAuto.CLOSE_THROW,ModularAuto.CLOSE_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(700);
        a = new ModularAuto(sequence, true, imuchassis("Left","Right","IMU"),s,mtr("Accelerator"),mtr("Sweeper"));
    }

    @Override
    public void loop() {
        a.run();
    }

    @Override
    public void stop() {
        a.stop();
    }
}
