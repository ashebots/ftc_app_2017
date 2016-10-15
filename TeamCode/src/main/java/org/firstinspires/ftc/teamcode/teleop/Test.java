package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ashebots.ftcandroidlib.complexOps.*;

@TeleOp(name="IMU Test", group ="TeleOp")

public class Test extends AdvOpMode {
    IMUChassis chassis;

    JoyEvent s = new JoyEvent(0.07,0.1,1.0);
    JoyEvent n = new JoyEvent(0.3,0.4,1.0);
    JoyEvent f = new JoyEvent(0.9,1.0,1.0);
    @Override
    public void init() {
        chassis = imuchassis("Left", "Right", "IMU");
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
        telemetry.addData("Angle",chassis.angle());
        telemetry.addData("Pitch",chassis.pitch());
    }
    @Override
    public void stop() {
        chassis.stop();
    }
}