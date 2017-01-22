
package org.firstinspires.ftc.teamcode.auto.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.Chassis;
import org.ashebots.ftcandroidlib.complexOps.Scaler;
import org.firstinspires.ftc.teamcode.auto.ModularAuto;

@Autonomous(name="[Red C] Center (NS)", group ="A")
public class RedCCenterNS extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init() {
        Chassis c = imuchassismechanum("Left","Right","LeftBack","RightBack","IMU");
        Scaler s = new Scaler();
        s.setTicksPer(encoderConstant);
        double[][] route = {ModularAuto.CENTER_START,ModularAuto.CLOSE_PARK};
        a = new ModularAuto(route, false, c, s, mtr("Accelerator"), mtr("Sweeper"), mtr("topSweep"),10000);
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
