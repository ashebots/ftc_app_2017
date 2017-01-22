
package org.firstinspires.ftc.teamcode.auto.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.AutoRoutine;
import org.ashebots.ftcandroidlib.complexOps.Chassis;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

@Autonomous(name="[Blue R] Center (NS)", group ="B")
public class BlueRCenterNS extends AdvOpMode {
    AutoRoutine a;
    @Override
    public void init() {
        Chassis c = imuchassismechanum("Left","Right","LeftBack","RightBack","IMU");
        Scaler s = new Scaler();
        s.setTicksPer(encoderConstant);
        double[][] route = {ModularAuto.RIGHT_START,ModularAuto.CLOSE_PARK};
        a = new ModularAuto(route, true, c, s, mtr("Accelerator"), mtr("Sweeper"), mtr("topSweep"),8000);
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
