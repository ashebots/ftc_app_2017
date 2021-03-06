package org.firstinspires.ftc.teamcode.auto.opmodes.blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.ChassisMechanum;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="[Blue-Left] NoBeacon Shoot Center",group="E")
public class BlueLeftNoBeaconShootCenter extends AdvOpMode {
    public BlueLeftNoBeaconShootCenter() {
        msStuckDetectInit = 60000;
    }

    ModularAuto a;
    ChassisMechanum c;

    @Override
    public void init() {
        double[][] sequence = {ModularAuto.RIGHT_START, ModularAuto.RIGHT_HUB, ModularAuto.CLOSE_THROW, ModularAuto.CLOSE_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(encoderConstant);
        c = imuchassismechanum("Left", "Right", "LeftBack", "RightBack", "IMU");
        a = new ModularAuto(sequence, true, c, s, mtr("Accelerator"), mtr("Sweeper"), mtr("topSweep"), 2, 10000);
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