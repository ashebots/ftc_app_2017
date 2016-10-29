package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

import org.ashebots.ftcandroidlib.complexOps.*;

@TeleOp(name="Drive", group ="TeleOp")

public class SimpleTeleOp extends AdvOpMode {
    //Define hardware
    AdvMotor lift;
    Chassis chassis;
    JoyEvent n = new JoyEvent(0.4,0.45,0.9);
    JoyEvent f = new JoyEvent(0.9,1.0,1.0);

    Servo top;
    Servo bottom;
    AdvMotor accelerator;
    BoolEvent yButton = new BoolEvent();
    BoolEvent bButton = new BoolEvent();

    boolean accTog;
    boolean frtTog;
    @Override
    public void init() {
        lift = mtr("Lift");
        chassis = chassis("Left", "Right");
        top = srv("Top");
        bottom = srv("Bottom");
        top.setPosition(0.5);
        bottom.setPosition(0.5);
        accelerator = mtr("Accelerator");


    }

    @Override
    public void loop() {
        //Motor calculations
        double[] mVals = n.calc(gamepad1.left_stick_x,gamepad1.left_stick_y);
        if (gamepad1.x) {
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
            accelerator.setMotor(0.5);
        } else accelerator.setMotor(0);
        //Servo controls
        double topSpd = 0.5;
        double botSpd = 0.5;
        int  conflicts = 0; //count how many are held down at once, 2 or more is bad
        if (gamepad1.left_trigger>0) {
            topSpd = botSpd = 1;
            conflicts++;
        }
        if (gamepad1.left_bumper) {
            topSpd = botSpd = 0;
            conflicts++;
        }
        if (gamepad1.dpad_up) {
            topSpd = 1;
            conflicts++;
        }
        if (gamepad1.dpad_down) {
            topSpd = 0;
            conflicts++;
        }
        if (conflicts < 2) { //If more than one are held down, just don't change it.
            top.setPosition(topSpd);
            bottom.setPosition(botSpd);
        }
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