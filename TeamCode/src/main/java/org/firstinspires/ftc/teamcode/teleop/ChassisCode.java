package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by jezebelquit on 5/16/17.
 */

public class ChassisCode {
    DcMotor motorLeft;
    DcMotor motorRight;
    public ChassisCode(DcMotor motorLeft, DcMotor motorRight){
        this.motorLeft = motorLeft;
        this.motorRight = motorRight;
    }
}
