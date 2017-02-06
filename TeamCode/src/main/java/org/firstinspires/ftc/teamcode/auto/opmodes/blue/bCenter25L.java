package org.firstinspires.ftc.teamcode.auto.opmodes.blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.ChassisMechanum;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="BlueR 1S C [25]", group="Q")
public class bCenter25L extends AdvOpMode {
    public bCenter25L() {
        msStuckDetectInit = 60000;
    }
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.LEFT_START,ModularAuto.FAR_THROW,ModularAuto.CLOSE_HUB,ModularAuto.CLOSE_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(encoderConstant);
        ChassisMechanum c = imuchassismechanum("Left","Right","LeftBack","RightBack","IMU");
        a = new ModularAuto(sequence, true, c,s,mtr("Accelerator"),mtr("Sweeper"),mtr("topSweep"), 1, 7500);
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
