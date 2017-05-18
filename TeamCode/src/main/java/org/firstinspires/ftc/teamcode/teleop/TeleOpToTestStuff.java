package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by jezebelquit on 5/16/17.
 */
@TeleOp(name = "Joystick test", group = "Niko's Code")
public class TeleOpToTestStuff extends OpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
    Chassis chassis;

    @Override
    public void init(){
        leftMotor = hardwareMap.dcMotor.get("Left motor");
        rightMotor = hardwareMap.dcMotor.get("Right motor");
        chassis = new Chassis(leftMotor, rightMotor);

    }

    @Override
    public void loop(){
        chassis.JoystickDrive(-gamepad1.left_stick_x, gamepad1.left_stick_y);
    }
}
