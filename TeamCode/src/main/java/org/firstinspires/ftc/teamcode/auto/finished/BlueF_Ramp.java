package org.firstinspires.ftc.teamcode.auto.finished;
import org.firstinspires.ftc.teamcode.auto.*;
import org.ashebots.ftcandroidlib.complexOps.*;

/**
 * Created by apple on 9/17/16.
 */
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name="[BlueL] Ramp")
public class BlueF_Ramp extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init() {
        double[][] sequence = {ModularAuto.RIGHT_START,ModularAuto.FAR_THROW,ModularAuto.RAMP_PARK};
        Scaler s = new Scaler();
        s.setTicksPer(700);
        a = new ModularAuto(sequence, true, imuchassis("Left","Right","IMU"),s,mtr("Accelerator"),mtr("Sweeper"),srv("topSweep"),5000);
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
