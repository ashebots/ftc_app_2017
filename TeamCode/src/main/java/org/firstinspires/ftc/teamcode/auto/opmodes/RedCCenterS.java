package org.firstinspires.ftc.teamcode.auto.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.*;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

@Autonomous(name="[Red C] Center (S)", group ="A")
public class RedCCenterS extends AdvOpMode {
    AutoRoutine a;
    @Override
    public void init() {
        Chassis c = imuchassismechanum("Left","Right","LeftBack","RightBack","IMU");
        Scaler s = new Scaler();
        s.setTicksPer(encoderConstant);
        double[][] route = {ModularAuto.CENTER_START,ModularAuto.CLOSE_THROW,ModularAuto.CLOSE_PARK};
        a = new ModularAuto(route, false, c, s, mtr("Accelerator"), mtr("Sweeper"), mtr("topSweep"),4000);
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
