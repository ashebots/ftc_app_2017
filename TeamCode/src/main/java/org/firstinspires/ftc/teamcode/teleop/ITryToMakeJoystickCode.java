package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class ITryToMakeJoystickCode extends OpMode {

    //The coordinates of the joystick
    double joystickXPosition;
    double joystickYPosition;

    //The angle formed by the y axis and the line formed by (0,0) and where the joystick is located.
    double joystickAngle;

    //The distance that the joystick is from the center
    double joystickDistance;

    //The two motors of the drive base
    DcMotor motorLeft;
    DcMotor motorRight;

    //The speed of the motors
    double leftMotorSpeed;
    double rightMotorSpeed;

    @Override
    public void init(){
        motorLeft = hardwareMap.dcMotor.get("Left motor");
        motorRight = hardwareMap.dcMotor.get("Right motor");
        motorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop(){
        //Defining joystickXPosition
        joystickXPosition = gamepad1.left_stick_x;

        //Defining joystickYPosition. Since the Y axis of the joystick is inverted, joystickYPosition will be the opposite of the original Y position.
        joystickYPosition = -gamepad1.left_stick_y;

        //The distance that the joystick is from the center can be calculated by using the pythagorean theorem.
        joystickDistance = Math.sqrt((joystickXPosition * joystickXPosition) + (joystickYPosition * joystickYPosition));

        //Sometimes, the joystick distance exceeds 1 by a little bit, and since that would make the motor speed exceed 1 or -1, it would make the robot crash. In the if statement, if joystickDistance exceeds 1, joystickDistance will just read as 1.
        if (joystickDistance > 1){
            joystickDistance = 1;
        }

        //Trigonometry can be be used to calculate joystickAngle.
        joystickAngle = Math.toDegrees(Math.atan2(joystickXPosition, joystickYPosition));

        if (joystickAngle >= 0 && joystickAngle < 90) {
            rightMotorSpeed = ((45 - joystickAngle) / 45) * joystickDistance;
            leftMotorSpeed = joystickDistance;

        }else if (joystickAngle >= 90){

            leftMotorSpeed = ((135 - joystickAngle) / 45) * joystickDistance;
            rightMotorSpeed = -joystickDistance;

        }else if (joystickAngle < 0 && joystickAngle > -90){

            leftMotorSpeed = ((45 + joystickAngle) / 45) * joystickDistance;
            rightMotorSpeed = joystickDistance;

        }else if (joystickAngle <= -90){
            rightMotorSpeed = ((135 + joystickAngle) / 45) * joystickDistance;
            leftMotorSpeed = -joystickDistance;
        }



        motorLeft.setPower(-leftMotorSpeed);
        motorRight.setPower(-rightMotorSpeed);

        //Stats such as the joystick coordinates, joystick distance, joystick angle, and more will be displayed in the driver station.
        telemetry.addData("Joystick X position", joystickXPosition);
        telemetry.addData("Joystick Y position", joystickYPosition);
        telemetry.addData("Joystick distance", joystickDistance);
        telemetry.addData("Joystick angle", joystickAngle);
        telemetry.addData("Left motor speed", leftMotorSpeed);
        telemetry.addData("Right motor speed", rightMotorSpeed);
    }
}
