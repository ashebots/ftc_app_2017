package org.firstinspires.ftc.teamcode.auto.finished.drive_only;
import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/17/16.
 */
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name="[BlueL] Center")
public class BlueF_Center extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.RIGHT_START,ModularAuto.CLOSE_THROW,ModularAuto.CLOSE_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(700);
        a = new ModularAuto(sequence, true, imuchassis("Left","Right","IMU"),s,null,null);
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
