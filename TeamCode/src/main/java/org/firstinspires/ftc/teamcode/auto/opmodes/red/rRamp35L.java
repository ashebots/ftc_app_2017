package org.firstinspires.ftc.teamcode.auto.opmodes.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.ChassisMechanum;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="RedL 2S R [35]", group="E")
public class rRamp35L extends AdvOpMode {
    public rRamp35L() {
        msStuckDetectInit = 60000;
    }
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.LEFT_START,ModularAuto.FAR_THROW,ModularAuto.FAR_HUB,ModularAuto.RAMP_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(encoderConstant);
        ChassisMechanum c = imuchassismechanum("Left","Right","LeftBack","RightBack","IMU");
        a = new ModularAuto(sequence, false, c,s,mtr("Accelerator"),mtr("Sweeper"),mtr("topSweep"), 2, 7500);
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
