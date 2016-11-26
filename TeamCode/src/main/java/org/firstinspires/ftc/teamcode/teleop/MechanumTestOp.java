package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.ashebots.ftcandroidlib.complexOps.*;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Mechanum")

public class MechanumTestOp extends AdvOpMode { //This class is testing the mechanum wheel drive.
    ChassisMechanum chassis;
    JoyEvent drive = new JoyEvent(1.0,1.0,1.0);
    JoyEvent mechanum = new JoyEvent(1.0,1.0,1.0);
    @Override
    public void init() {
        chassis = chassismechanum("Left", "Right", "LeftBack", "RightBack");
    }

    @Override
    public void loop() {
        boolean driveMode = drive.parse(gamepad1.left_stick_x,gamepad1.left_stick_y)=="HELD";
        boolean mechnMode = mechanum.parse(gamepad1.right_stick_x,gamepad1.right_stick_y)=="HELD";
        if (driveMode && mechnMode) {
            double[] mechnm = mechanum.calc(gamepad1.right_stick_x,gamepad1.right_stick_y);
            double[] motors = drive.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
            chassis.motorLeft.setPower((mechnm[0]+motors[0])/2);
            chassis.motorRight.setPower((mechnm[1]+motors[1])/2);
            chassis.motorLeftB.setPower((mechnm[0]+motors[1])/2);
            chassis.motorRightB.setPower((mechnm[1]+motors[0])/2);
        } else if (driveMode) {
            double[] motors = drive.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
            chassis.moveMotors(motors[0],motors[1]);
        } else {
            chassis.omniDrive(gamepad1.right_stick_x,gamepad1.right_stick_y);
        }
    }
    @Override
    public void stop() {
        chassis.stop();
    }
}