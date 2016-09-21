package com.qualcomm.ftcrobotcontroller.opmodes.auto.finished.drive_only;

import com.qualcomm.ftcrobotcontroller.opmodes.auto.ModularAuto;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.Scaler;

/**
 * Created by apple on 9/17/16.
 */
public class Blue_LeftBallStand extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {a.LEFT_START,a.FAR_THROW,a.FAR_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(1375);
        a = new ModularAuto(sequence, true, imuchassis("Left","Right","IMU"),s);
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
