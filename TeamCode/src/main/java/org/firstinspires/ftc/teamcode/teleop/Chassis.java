package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by jezebelquit on 5/16/17.
 */

public class Chassis {
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorLeftRear;
    DcMotor motorRightRear;

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

    public void JoystickDrive(double xPos, double yPos){
        double[] motorSpeeds = Joystick.calculate(xPos, yPos);
        motorLeft.setPower(motorSpeeds[0]);
        motorRight.setPower(motorSpeeds[1]);
        if (motorLeftRear != null){
            motorLeft.setPower(motorSpeeds[1]);
            motorRight.setPower(motorSpeeds[0]);
            motorLeftRear.setPower(motorSpeeds[0]);
            motorRightRear.setPower(motorSpeeds[1]);
        }
    }
}
