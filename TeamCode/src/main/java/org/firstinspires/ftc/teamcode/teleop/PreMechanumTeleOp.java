package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ashebots.ftcandroidlib.complexOps.AdvMotor;
import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.BoolEvent;
import org.ashebots.ftcandroidlib.complexOps.Chassis;
import org.ashebots.ftcandroidlib.complexOps.JoyEvent;

@TeleOp(name="Drive (w/o Mechanum)", group ="TeleOp")

public class PreMechanumTeleOp extends AdvOpMode {
    //Define hardware
    AdvMotor lift;
    Chassis chassis;
    JoyEvent s = new JoyEvent(0.1,0.15,1.0);
    JoyEvent n = new JoyEvent(0.4,0.45,1.0);
    JoyEvent f = new JoyEvent(0.9,1.0,1.0);

    AdvMotor sweeper;
    AdvMotor accelerator;
    BoolEvent aButton = new BoolEvent();
    BoolEvent yButton = new BoolEvent();
    BoolEvent bButton = new BoolEvent();
    BoolEvent xButton = new BoolEvent();

    boolean accTog;
    boolean frtTog;

    int speedMode = 0; //0 is normal, 1 is fast, 2 is slow
    @Override
    public void init() {
        lift = mtr("Lift");
        chassis = chassis("Left", "Right");
        sweeper = mtr("Sweeper");
        accelerator = mtr("Accelerator");
    }

    @Override
    public void loop() {
        //Speed Mode
        if (xButton.parse(gamepad1.x).equals("PRESSED")) {
            if (speedMode == 1) {
                speedMode = 0;
            } else speedMode = 1;
        }
        if (aButton.parse(gamepad1.a).equals("PRESSED")) {
            if (speedMode == 2) {
                speedMode = 0;
            } else speedMode = 2;
        }

        //Run Motors
        double[] mVals = n.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        if (speedMode == 1) { //fast mode
            mVals = f.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        }
        if (speedMode == 2) { //slow mode
            mVals = s.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
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
        } else if (gamepad1.start) {
            accelerator.setMotor(-0.05);
        } else {
            accelerator.stop();
        }
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