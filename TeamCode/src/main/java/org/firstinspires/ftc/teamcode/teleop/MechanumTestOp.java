package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.ashebots.ftcandroidlib.complexOps.*;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Mechanum")

public class MechanumTestOp extends AdvOpMode { //This class is testing the mechanum wheel drive.
    ChassisMechanum chassis;
    @Override
    public void init() {
        chassis = chassismechanum("Left", "Right", "LeftBack", "RightBack");
    }

    @Override
    public void loop() {
        chassis.motorLeft.setPower(gamepad1.left_stick_y);
        chassis.motorRightB.setPower(gamepad1.left_stick_y);
        chassis.motorRight.setPower(gamepad1.right_stick_y);
        chassis.motorLeftB.setPower(gamepad1.right_stick_y);
    }
    @Override
    public void stop() {
        chassis.stop();
    }
}