package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jezebelquit on 5/16/17.
 */

public class Chassis {
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorLeftRear;
    DcMotor motorRightRear;
    Servo turningServo;

    public Chassis(DcMotor motorLeft, DcMotor motorRight){
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
        motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Chassis(DcMotor motorLeft, DcMotor motorRight, DcMotor motorLeftRear, DcMotor motorRightRear){
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
        this.motorLeftRear = motorLeftRear;
        this.motorRightRear = motorRightRear;
        motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeftRear.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Chassis(DcMotor motorLeft,Servo turningServo){
        this.motorLeft = motorLeft;
        this.turningServo = turningServo;
    }

    public void NormalDrive(double xPos, double yPos){
        double[] motorSpeeds = Joystick.calculateNormal(xPos, yPos);
        motorLeft.setPower(motorSpeeds[0]);
        motorRight.setPower(motorSpeeds[1]);
        if (motorLeftRear != null){
            motorLeft.setPower(motorSpeeds[0]);
            motorRight.setPower(motorSpeeds[1]);
            motorLeftRear.setPower(motorSpeeds[0]);
            motorRightRear.setPower(motorSpeeds[1]);
        }
    }
    public void HoloMecaDrive(double xPos, double yPos){
        double[] motorSpeeds = Joystick.calculateNormal(xPos, yPos);
        motorLeft.setPower(motorSpeeds[0]);
        motorRight.setPower(motorSpeeds[1]);
        motorLeftRear.setPower(motorSpeeds[1]);
        motorRightRear.setPower(motorSpeeds[0]);
    }
    public void CarvingDrive(double xPos, double yPos){
        double[] motorSpeeds = Joystick.calculateCarving(xPos, yPos);

        motorLeft.setPower(motorSpeeds[0]);
        turningServo.setPosition(motorSpeeds[1]);
    }

}
