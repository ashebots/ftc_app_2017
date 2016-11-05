package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.ashebots.ftcandroidlib.complexOps.*;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Drive", group ="TeleOp")

public class MainTeleOp extends AdvOpMode {
    //Define hardware
    AdvMotor lift;
    Chassis chassis;
    JoyEvent n = new JoyEvent(0.4,0.45,0.9);
    JoyEvent f = new JoyEvent(0.9,1.0,1.0);

    AdvMotor sweeper;
    AdvMotor accelerator;
    BoolEvent yButton = new BoolEvent();
    BoolEvent bButton = new BoolEvent();
    BoolEvent xButton = new BoolEvent();

    boolean accTog;
    boolean frtTog;
    boolean spdTog;
    @Override
    public void init() {
        lift = mtr("Lift");
        chassis = chassis("Left", "Right");
        sweeper = mtr("Sweeper");
        accelerator = mtr("Accelerator");
    }

    @Override
    public void loop() {
        //Motor calculations
        double[] mVals = n.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        spdTog = (spdTog ^ xButton.parse(gamepad1.x).equals("PRESSED"));
        if (spdTog) {
            mVals = f.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        }
        frtTog = (frtTog ^ bButton.parse(gamepad1.b).equals("PRESSED"));
        if (frtTog) {
            double placeholder = mVals[0];
            mVals[0] = -mVals[1];
            mVals[1] = -placeholder;
        }
        chassis.moveMotors(mVals[0], mVals[1]);

        telemetry.addData("Left", mVals[0]);
        telemetry.addData("Right", mVals[1]);
        //Toggle accelerator
        accTog = (accTog ^ yButton.parse(gamepad1.y).equals("PRESSED"));
        if (accTog) {
            accelerator.setMotor(1);
        } else accelerator.setMotor(0);
        //Sweeper controls
        if (gamepad1.left_trigger>0) {
            sweeper.setMotor(-1);
        } else if (gamepad1.left_bumper) {
            sweeper.setMotor(1);
        } else sweeper.stop();
        //Lift
        if (gamepad1.right_bumper) {
            lift.setMotor(1);
        } else if (gamepad1.right_trigger>0) {
            lift.setMotor(-1);
        } else {
            lift.setMotor(0);
        }
    }
    @Override
    public void stop() {
        chassis.stop();
    }
}