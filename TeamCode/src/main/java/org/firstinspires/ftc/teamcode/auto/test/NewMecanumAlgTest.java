package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.Chassis;

/**
 * Created by secretbuilder00 on 3/19/2017.
 */
@Autonomous(name = "DriveTest", group = "Z")
public class NewMecanumAlgTest extends AdvOpMode  {
    Chassis chassis;
    double Angle; //Angle you want the robot to turn to
    double DriveAngle; //Angle you want the robot to drive in
    double Speed; //how fast the robot will drive in the desired direction
    double AngleChangeSpeed; //How fast the robot will turn to match the Angle
    public void init(){
        chassis = imuchassis("Left","Right","IMU");
        Angle = 0;
        DriveAngle = 0;
        Speed = .5;
        AngleChangeSpeed = 0;
    }

    public void loop(){
        chassis.motorLeft.setPower(2/(Speed*Math.sin(Angle+3.14/4)+ AngleChangeSpeed));
        chassis.motorRight.setPower(2/(Speed*Math.cos(Angle+3.14/4)- AngleChangeSpeed));
        chassis.motorLeftB.setPower(2/(Speed*Math.cos(Angle+3.14/4)+ AngleChangeSpeed));
        chassis.motorLeft.setPower(2/(Speed*Math.sin(Angle+3.14/4)- AngleChangeSpeed));
        if(gamepad1.left_bumper){
            DriveAngle = DriveAngle + 0.0872665;
        }else if (gamepad1.right_bumper){
            DriveAngle = DriveAngle - 0.0872665;
        }else if (gamepad1.dpad_up){
            Speed = Speed + 0.1;
        }else if (gamepad1.dpad_down){
            Speed = Speed - 0.1;
        }
    }
}
