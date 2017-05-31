package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by jezebelquit on 5/30/17.
 */
@TeleOp(name = "Encoder test")
public class TestEncoders extends OpMode {
    DcMotor testMotor;
    @Override
    public void init(){
        testMotor = hardwareMap.dcMotor.get("RightBack");
    }

    @Override
    public void loop(){
        testMotor.setPower(gamepad1.left_stick_y);
        telemetry.addData("Encoder value", testMotor.getCurrentPosition());
    }
}
