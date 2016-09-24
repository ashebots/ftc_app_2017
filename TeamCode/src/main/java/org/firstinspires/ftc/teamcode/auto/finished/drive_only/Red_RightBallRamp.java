package org.firstinspires.ftc.teamcode.auto.finished.drive_only;
import org.firstinspires.ftc.teamcode.auto.*;

/**
 * Created by apple on 9/17/16.
 */
public class Red_RightBallRamp extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {a.RIGHT_START,a.CLOSE_PARK,a.RAMP_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(1375);
        a = new ModularAuto(sequence, false, imuchassis("Left","Right","IMU"),s);
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
