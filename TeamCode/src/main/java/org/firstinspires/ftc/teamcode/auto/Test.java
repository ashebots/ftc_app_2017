package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.*;

@Autonomous(name="Test", group ="Test")
public class Test extends AdvOpMode {
    ModularAuto a;
    @Override
    public void init(){
        Scaler s = new Scaler();
        s.setTicksPer(2750);
        double[][] p = {a.CENTER_START,a.CLOSE_THROW,a.FAR_THROW,a.RAMP_PARK};
        a = new ModularAuto(p,false,imuchassis("Left","Right","IMU"),s);
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
