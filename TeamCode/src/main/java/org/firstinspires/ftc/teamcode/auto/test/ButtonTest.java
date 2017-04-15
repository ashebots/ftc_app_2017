package org.firstinspires.ftc.teamcode.auto.test;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/17/16.
 */
@Autonomous(name="Button Test",group="Z")
public class ButtonTest extends AdvOpMode {
    public ButtonTest() {
        msStuckDetectInit = 60000;
    }

    ModularAuto a;
    ChassisMechanum c;

    @Override
    public void init() {
        double[][] sequence = {ModularAuto.LEFT_START, ModularAuto.FAR_HUB, ModularAuto.BEACON, ModularAuto.BEACON_THROW, ModularAuto.RAMP_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(615);
        c = imuchassismechanum("Left", "Right", "LeftBack", "RightBack", "IMU");
        a = new ModularAuto(sequence, false, c, s, mtr("Accelerator"), mtr("Sweeper"), mtr("topSweep"), 1, hardwareMap);
    }

    @Override
    public void loop() {
        a.run();
        telemetry.addData("Beacon",a.color.beacon.getAnalysis().getColorString());
        telemetry.addData("Line",a.lineDetector.red()+a.lineDetector.green()+a.lineDetector.blue());
    }

    @Override
    public void stop() {
        a.stop();
    }
}