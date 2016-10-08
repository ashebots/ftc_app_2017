package org.firstinspires.ftc.teamcode.auto.finished.drive_only;
import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/17/16.
 */
public class Blue_BallRamp extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.CENTER_START,ModularAuto.CLOSE_PARK,ModularAuto.RAMP_PARK};
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
