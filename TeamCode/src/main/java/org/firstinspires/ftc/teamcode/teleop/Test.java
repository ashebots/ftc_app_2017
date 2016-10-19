package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ashebots.ftcandroidlib.complexOps.*;

@TeleOp(name="IMU Test", group ="TeleOp")
@Disabled
public class Test extends AdvOpMode {
    IMUChassis chassis;

    JoyEvent n = new JoyEvent(0.3,0.4,1.0);
    @Override
    public void init() {
        chassis = imuchassis("Left", "Right", "IMU");
    }
    @Override
    public void loop() {
        double[] mVals = n.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        chassis.moveMotors(mVals[0], mVals[1]);
        chassis.calc();
        telemetry.addData("Angle",chassis.angle());
        telemetry.addData("Pitch",chassis.pitch());
        telemetry.addData("Roll",chassis.roll());
    }
    @Override
    public void stop() {
        chassis.stop();
    }
}