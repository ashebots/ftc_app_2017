package org.firstinspires.ftc.teamcode.auto.finished.drive_only;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

/**
 * Created by apple on 9/17/16.
 */
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name="[RedR] Center")
public class RedF_Center extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.RIGHT_START,ModularAuto.CLOSE_HUB,ModularAuto.CLOSE_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(916);
        a = new ModularAuto(sequence, false, imuchassis("Left","Right","IMU"),s,null,null);
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
