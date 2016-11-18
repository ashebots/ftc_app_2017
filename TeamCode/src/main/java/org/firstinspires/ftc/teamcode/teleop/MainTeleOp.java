package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.ashebots.ftcandroidlib.complexOps.*;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Drive (w/ Mechanum)", group ="TeleOp")

public class MainTeleOp extends AdvOpMode {
    //Define hardware
    AdvMotor lift;
    ChassisMechanum chassis;
    JoyEvent s = new JoyEvent(0.1,0.15,1.0);
    JoyEvent n = new JoyEvent(0.4,0.45,1.0);
    JoyEvent f = new JoyEvent(1.0,1.0,1.0);

    AdvMotor sweeper;
    AdvMotor accelerator;

    BoolEvent aButton = new BoolEvent();
    BoolEvent bButton = new BoolEvent();
    BoolEvent xButton = new BoolEvent();
    BoolEvent yButton = new BoolEvent();

    boolean accTog;
    boolean frtTog;
    int speedMode;
    @Override
    public void init() {
        lift = mtr("Lift");
        chassis = chassismechanum("Left", "Right", "LeftBack", "RightBack");
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
        if (gamepad1.right_stick_x != 0 || gamepad1.right_stick_y != 0) { //Mechanum Joystick
            double[] mVals = {gamepad1.right_stick_x,gamepad1.right_stick_y};
            if (speedMode == 0) { //normal mode
                mVals[0] *= 0.4;
                mVals[1] *= 0.4;
            }
            if (speedMode == 2) { //slow mode
                mVals[0] *= 0.1;
                mVals[1] *= 0.1;
            }
            if (frtTog) {
                mVals[0] *= -1;
                mVals[1] *= -1;
            }
            chassis.mechanumDrive(mVals[0],mVals[1]);
        } else { //Normal Joystick
            double[] mVals = n.calc(gamepad1.left_stick_x, gamepad1.left_stick_y);
            if (speedMode == 1) { //fast mode
                mVals = f.calc(gamepad1.left_stick_x, gamepad1.left_stick_y);
            }
            if (speedMode == 2) { //slow mode
                mVals = s.calc(gamepad1.left_stick_x, gamepad1.left_stick_y);
            }
            frtTog = (frtTog ^ bButton.parse(gamepad1.b).equals("PRESSED"));
            if (frtTog) {
                double placeholder = mVals[0];
                mVals[0] = -mVals[1];
                mVals[1] = -placeholder;
            }
            chassis.moveMotors(mVals[0], mVals[1]);
        }

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