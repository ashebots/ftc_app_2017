package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtColorSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.ashebots.ftcandroidlib.complexOps.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group ="TeleOp")

public class MainTeleOp extends AdvOpMode {
    //Define hardware
    AdvMotor lift;
    ChassisMechanum chassis;
    JoyEvent drive = new JoyEvent(1.0,1.0,1.0);
    JoyEvent mechanum = new JoyEvent(1.0,1.0,1.0);

    AdvMotor bottomSweeper;
    AdvMotor accelerator;
    AdvMotor topSweeper;
    BoolEvent aButton = new BoolEvent();
    BoolEvent bButton = new BoolEvent();
    BoolEvent xButton = new BoolEvent();
    BoolEvent yButton = new BoolEvent();

    boolean accTog;
    boolean frtTog = true;

    long speedTime;
    int speedMode = 1;

    @Override
    public void init() {
        topSweeper = mtr("topSweep");
        lift = mtr("Lift");
        chassis = imuchassismechanum("Left", "Right", "LeftBack", "RightBack", "IMU");
        bottomSweeper = mtr("Sweeper");
        accelerator = mtr("Accelerator");
        bottomSweeper.reverse();
        accelerator.setTargetSpeed(900);
    }

    @Override
    public void start() {
        speedMode = 1;
    }

    @Override
    public void loop() {
        telemetry.addData("Angle",chassis.angle());
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
        } else if (mechnMode) { //Mechanum Mode
            double modifier = 0.5;
            if (speedMode == 2) modifier = 0.25; //slow mode
            if (speedMode == 1) modifier = 1; //fast mode
            if (frtTog) modifier *= -1;
            chassis.omniDrive(modifier*gamepad1.right_stick_x,modifier*gamepad1.right_stick_y);
        } else {
            chassis.stop();
        }

        //Toggle accelerator
        if (yButton.parse(gamepad1.y).equals("PRESSED")) {
            accTog = !accTog;
            speedTime = System.currentTimeMillis();
        }
        if (accTog) {
            accelerator.setMotor(1);
            double timeActive = (System.currentTimeMillis() - speedTime) / 1000;
            if (timeActive < 3) {
                telemetry.addData("Shooter Speed",3 - timeActive);
            } else {
                telemetry.addData("Shooter Speed","Ready for Launch");
            }
        } else if (gamepad1.start) {
            accelerator.setMotor(-0.1);
            telemetry.addData("Shooter Speed","Backing Up");
        } else {
            accelerator.stop();
            telemetry.addData("Shooter Speed","Inactive");
        }
        //Sweeper controls for both sweepers
        if (gamepad1.left_trigger>0) {
            topSweeper.setMotor(1);
            bottomSweeper.setMotor(1);
        } else if (gamepad1.left_bumper) {
            topSweeper.setMotor(-1);
            bottomSweeper.setMotor(-1);
        } else if (gamepad1.dpad_down){
            bottomSweeper.setMotor(-1);
            topSweeper.stop();
        } else if (gamepad1.dpad_up){
            topSweeper.setMotor(-1);
            bottomSweeper.stop();
        } else {
            topSweeper.stop();
            bottomSweeper.stop();
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