package org.firstinspires.ftc.teamcode.auto.opmodes.red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.ChassisMechanum;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="RedL 2S 1B C [70*]", group="A")
public class rBeacon70 extends AdvOpMode {
    public rBeacon70() {
        msStuckDetectInit = 60000;
    }
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.LEFT_START,ModularAuto.FAR_THROW,ModularAuto.CLOSE_BEACON,ModularAuto.BEACON_HUB,ModularAuto.CLOSE_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(encoderConstant);
        ChassisMechanum c = imuchassismechanum("Left","Right","LeftBack","RightBack","IMU");
        a = new ModularAuto(sequence, false, c,s,mtr("Accelerator"),mtr("Sweeper"),mtr("topSweep"), 2);
        a.initVuforia();
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