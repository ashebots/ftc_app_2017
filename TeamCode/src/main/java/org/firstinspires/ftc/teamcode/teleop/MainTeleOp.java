package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.Servo;

import org.ashebots.ftcandroidlib.complexOps.*;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group ="TeleOp")

public class MainTeleOp extends AdvOpMode {
    //Define hardware
    AdvMotor lift;
    ChassisMechanum chassis;
    JoyEvent drive = new JoyEvent(1.0,1.0,1.0);
    JoyEvent mechanum = new JoyEvent(1.0,1.0,1.0);

    AdvMotor bottomSweeper;
    AdvMotor accelerator;
    Servo topSweeper;

    BoolEvent aButton = new BoolEvent();
    BoolEvent bButton = new BoolEvent();
    BoolEvent xButton = new BoolEvent();
    BoolEvent yButton = new BoolEvent();

    boolean accTog;
    boolean frtTog = true;
    int speedMode = 1;

    @Override
    public void init() {
        topSweeper = srv("topSweep");
        lift = mtr("Lift");
        chassis = chassismechanum("Left", "Right", "LeftBack", "RightBack");
        bottomSweeper = mtr("bottomSweep");
        accelerator = mtr("Accelerator");
        topSweeper.setDirection(Servo.Direction.REVERSE);
        bottomSweeper.reverse();
    }

    @Override
    public void start() {
        speedMode = 1;
    }

    @Override
    public void loop() {
        telemetry.addData("Frt",chassis.motorRight.getCurrentPosition());
        telemetry.addData("Bck",chassis.motorRightB.getCurrentPosition());
        //Speed Mode
        if (xButton.parse(gamepad1.x).equals("PRESSED")) { //fast
            if (speedMode == 1) {
                speedMode = 0;
            } else speedMode = 1;
        }
        if (aButton.parse(gamepad1.a).equals("PRESSED")) { //slow
            if (speedMode == 2) {
                speedMode = 0;
            } else speedMode = 2;
        }
        //Reverse Mode
        frtTog = (frtTog ^ bButton.parse(gamepad1.b).equals("PRESSED"));

        boolean driveMode = drive.parse(gamepad1.left_stick_x,gamepad1.left_stick_y)=="HELD";
        boolean mechnMode = mechanum.parse(gamepad1.right_stick_x,gamepad1.right_stick_y)=="HELD";
        if (driveMode && mechnMode) { //Strafe Mode
            double modifier = 0.5;
            if (speedMode == 2) modifier = 0.25; //slow mode
            if (speedMode == 1) modifier = 1; //fast mode
            double[] motors = drive.calc(-modifier*gamepad1.left_stick_x,modifier*gamepad1.left_stick_y);
            if (frtTog) modifier *= -1;
            double[] mechnm = mechanum.calc(modifier*gamepad1.right_stick_x,modifier*gamepad1.right_stick_y);
            if (frtTog) { //reverse by switching motor values
                double placeholder = motors[0];
                motors[0] = -motors[1];
                motors[1] = -placeholder;
            }
            chassis.motorLeft.setPower((mechnm[0]+motors[0])/2);
            chassis.motorRight.setPower((mechnm[1]+motors[1])/2);
            chassis.motorLeftB.setPower((mechnm[0]+motors[1])/2);
            chassis.motorRightB.setPower((mechnm[1]+motors[0])/2);
        } else if (driveMode) { //Drive Mode
            double modifier = 0.5;
            if (speedMode == 2) modifier = 0.25; //slow mode
            if (speedMode == 1) modifier = 1; //fast mode
            double[] motors = drive.calc(modifier*gamepad1.left_stick_x,modifier*gamepad1.left_stick_y);
            if (frtTog) { //reverse by switching motor values
                double placeholder = motors[0];
                motors[0] = -motors[1];
                motors[1] = -placeholder;
            }
            chassis.moveMotors(motors[0],motors[1]);
        } else { //Mechanum Mode
            double modifier = 0.5;
            if (speedMode == 2) modifier = 0.25; //slow mode
            if (speedMode == 1) modifier = 1; //fast mode
            if (frtTog) modifier *= -1;
            chassis.omniDrive(modifier*gamepad1.right_stick_x,modifier*gamepad1.right_stick_y);
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
        //Sweeper controls for both sweepers
        if (gamepad1.left_trigger>0) {
            bottomSweeper.setMotor(1);
            topSweeper.setPosition(1);
        } else if (gamepad1.left_bumper) {
            bottomSweeper.setMotor(-1);
            topSweeper.setPosition(0);
        } else if (gamepad1.dpad_up){
            //When the dpad is pressed up, the bottom sweeper will rotate inward
            bottomSweeper.setMotor(1);
            topSweeper.setPosition(0.5);
        } else if (gamepad1.dpad_down){
            //When the dpad is pressed down, the bottom sweeper will rotate outward
            topSweeper.setPosition(0.5);
            bottomSweeper.setMotor(-1);
        } else{
            bottomSweeper.setMotor(0);
            topSweeper.setPosition(0.5);
        }
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