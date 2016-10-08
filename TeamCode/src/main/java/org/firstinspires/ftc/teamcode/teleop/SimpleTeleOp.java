package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ashebots.ftcandroidlib.complexOps.*;

@TeleOp(name="Drive", group ="TeleOp")

public class SimpleTeleOp extends AdvOpMode {
    Chassis chassis;
    JoyEvent s = new JoyEvent(0.07,0.1,1.0);
    JoyEvent n = new JoyEvent(0.3,0.4,1.0);
    JoyEvent f = new JoyEvent(0.9,1.0,1.0);
    @Override
    public void init() {
        chassis = chassis("Left","Right");
    }
    @Override
    public void loop() {
        double[] mVals = n.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        if (gamepad1.a) mVals = f.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        if (gamepad1.b) mVals = s.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        if (gamepad1.x) {
            double placeholder = mVals[0];
            mVals[0] = -mVals[1];
            mVals[1] = -placeholder;
        }
        chassis.moveMotors(mVals[0], mVals[1]);
        telemetry.addData("Left", mVals[0]);
        telemetry.addData("Right",mVals[1]);
    }
    @Override
    public void stop() {
        chassis.stop();
    }
}