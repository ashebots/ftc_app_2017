package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Joystick {
    public static double[] calculateNormal(double xPos, double yPos){

        //The angle formed by the y axis and the line formed by (0,0) and where the joystick is located.
        double joystickAngle;

        //The distance that the joystick is from the center
        double joystickDistance;

        //The speed of the motors
        double[] motorSpeeds = new double[2];

            //The distance that the joystick is from the center can be calculated by using the pythagorean theorem.
            joystickDistance = Math.sqrt((xPos * xPos) + (yPos * yPos));

            //Sometimes, the joystick distance exceeds 1 by a little bit, and since that would make the motor speed exceed 1 or -1, it would make the robot crash. In the if statement, if joystickDistance exceeds 1, joystickDistance will just read as 1.
            if (joystickDistance > 1){
                joystickDistance = 1;
            }

            //Trigonometry can be be used to calculate joystickAngle.
            joystickAngle = Math.toDegrees(Math.atan2(xPos, yPos));

            if (joystickAngle >= 0 && joystickAngle < 90) {
                motorSpeeds[1] = ((45 - joystickAngle) / 45) * joystickDistance;
                motorSpeeds[0] = joystickDistance;

            }else if (joystickAngle >= 90){

                motorSpeeds[0] = ((135 - joystickAngle) / 45) * joystickDistance;
                motorSpeeds[1] = -joystickDistance;

            }else if (joystickAngle < 0 && joystickAngle > -90){

                motorSpeeds[0] = ((45 + joystickAngle) / 45) * joystickDistance;
                motorSpeeds[1] = joystickDistance;

            }else if (joystickAngle <= -90){
                motorSpeeds[1] = ((135 + joystickAngle) / 45) * joystickDistance;
                motorSpeeds[0] = -joystickDistance;
            }

        return  motorSpeeds;
        }

    public static double[] calculateCarving(double xPos, double yPos){

        double[] motorSpeeds = new double[2];

        if (xPos > 1) xPos = 1;
        if (yPos > 1) yPos = 1;

        motorSpeeds[0] = yPos;
        motorSpeeds[1] = (xPos + 1) * (3/8);

        return motorSpeeds;
        }
    }


