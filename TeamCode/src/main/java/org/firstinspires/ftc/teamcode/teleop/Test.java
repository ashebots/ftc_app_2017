package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.ashebots.ftcandroidlib.complexOps.*;

@TeleOp(name="IMU Test", group ="TeleOp")

public class Test extends AdvOpMode {
    Chassis chassis;

    JoyEvent n = new JoyEvent(1.0,1.0,1.0);
    @Override
    public void init() {
        chassis = chassis("Left", "Right");
    }
    @Override
    public void loop() {
        double[] mVals = n.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        chassis.moveMotors(mVals[1], mVals[0]);

    }
    @Override
    public void stop() {
        chassis.stop();
    }
}