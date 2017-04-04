package org.firstinspires.ftc.teamcode.auto.opmodes.blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.ChassisMechanum;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="[Blue-Right] Beacon Shoot Center",group="C")
public class BlueRightBeaconShootCenter extends AdvOpMode {
    public BlueRightBeaconShootCenter() {
        msStuckDetectInit = 60000;
    }

    ModularAuto a;
    ChassisMechanum c;

    @Override
    public void init() {
        double[][] sequence = {ModularAuto.LEFT_START, ModularAuto.FAR_HUB, ModularAuto.CLOSE_BEACON, ModularAuto.FAR_BEACON, ModularAuto.BEACON_THROW, ModularAuto.CLOSE_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(encoderConstant);
        c = imuchassismechanum("Left", "Right", "LeftBack", "RightBack", "IMU");
        a = new ModularAuto(sequence, true, c, s, mtr("Accelerator"), mtr("Sweeper"), mtr("topSweep"), 2, hardwareMap);
    }

    @Override
    public void loop() {
        a.run();
        if (a.special!=null) {
            telemetry.addData("State",a.special.getStep());
        }
        telemetry.addData("Line",a.lineDetector.red()+a.lineDetector.green()+a.lineDetector.blue());
    }

    @Override
    public void stop() {
        a.stop();
    }
}