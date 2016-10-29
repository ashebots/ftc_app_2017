package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by jezebelquit on 10/29/16.
 */

public class AutoBallThrow extends OpMode {
    DcMotor Left;
    DcMotor Right;
    DcMotor Accelerator;
    Servo Top;
    Servo Bottom;
        public long distanceDrive = 1000; //Distance we will drive to
        public boolean startLaunch = false;
    @Override

    public void init(){
            Left = hardwareMap.dcMotor.get("Left");
            Right = hardwareMap.dcMotor.get("Right");
            Accelerator = hardwareMap.dcMotor.get("Accelerator");
            Top = hardwareMap.servo.get("Top");
            Bottom = hardwareMap.servo.get("Bottom");
        Left.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop(){
        //Tests if the robot has moved far enough with if statment, then launches
    if(Left.getCurrentPosition() < distanceDrive && Right.getCurrentPosition() < distanceDrive && !startLaunch){
        Left.setPower(1);
        Right.setPower(1);
        startLaunch = false;
    } else if (Left.getCurrentPosition() >= distanceDrive && Right.getCurrentPosition() >= distanceDrive && !startLaunch){
        Left.setPower(0);
        Right.setPower(0);
        startLaunch = true;
    } else if (startLaunch){
        //Initiates the launch of particles
        Accelerator.setPower(1);
        Top.setPosition(1);
        Bottom.setPosition(1);

        if (Accelerator.getCurrentPosition() > 10000) {
            Accelerator.setPower(0);
            Top.setPosition(0.5);
            Bottom.setPosition(0.5);
            Left.setPower(1);
            Right.setPower(1);
            if (Left.getCurrentPosition() >= 2000 & Right.getCurrentPosition() >= 2000 && startLaunch){
                stop();
            }
        }
        }

    }

    @Override           //stops code
    public void stop(){
    Left.setPower(0);
    Right.setPower(0);
    Accelerator.setPower(0);
    Top.setPosition(0.5);
    Bottom.setPosition(0.5);
    }
}
