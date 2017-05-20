package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jezebelquit on 5/16/17.
 */
@TeleOp(name = "Joystick test", group = "Niko's Code")
public class TeleOpToTestStuff extends OpMode {
    DcMotor drivingMotor;
    Servo turningServo;
    Chassis chassis;

    @Override
    public void init(){
        drivingMotor = hardwareMap.dcMotor.get("Driving motor");
        turningServo = hardwareMap.servo.get("Turning servo");
        chassis = new Chassis(drivingMotor, turningServo);

    }

    @Override
    public void loop(){
        chassis.CarvingDrive(gamepad1.right_stick_x, -gamepad1.left_stick_y);
    }
}
