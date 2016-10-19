package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.IMUChassis;
import org.firstinspires.ftc.teamcode.auto.Ramp;


@Autonomous(name = "RampTest", group = "Test")
public class RampTest extends AdvOpMode
{
    Ramp ramp;
    IMUChassis chassis;

    @Override
    public void init () {
        chassis = imuchassis("Left","Right","IMU");
        ramp = new Ramp(chassis);

    }

    @Override
    public void loop () {
        ramp.run();

    }

    @Override
    public void stop () {
        ramp.stop();
    }
}
