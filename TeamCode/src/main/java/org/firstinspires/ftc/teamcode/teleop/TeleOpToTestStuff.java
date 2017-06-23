package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jezebelquit on 5/16/17.
 */
@TeleOp(name = "Joystick test", group = "Niko's Code")
public class TeleOpToTestStuff extends OpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;
    Chassis chassis;
    IrSeekerSensor sensor;
    @Override
    public void init(){
        sensor = hardwareMap.irSeekerSensor.get("IrSensor");
        leftMotor = hardwareMap.dcMotor.get("Left");
        rightMotor = hardwareMap.dcMotor.get("Right");
        chassis = new Chassis(leftMotor, rightMotor);

    }

    @Override
    public void loop(){
        chassis.NormalDrive(-gamepad1.left_stick_x, gamepad1.left_stick_y);
        telemetry.addData("Ir sensor angle", sensor.getAngle());
        telemetry.addData("Ir sensor strength", sensor.getStrength());

    }
}
