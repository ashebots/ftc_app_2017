package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.hardware.adafruit.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;
import org.ashebots.ftcandroidlib.complexOps.Chassis;
import org.firstinspires.ftc.teamcode.auto.Ramp;


@Autonomous(name = "RampTest", group = "Z")
public class RampTest extends AdvOpMode
{
    Ramp ramp;
    Chassis chassis;

    @Override
    public void init () {
        chassis = imuchassis("Left","Right","IMU");
        ramp = new Ramp(chassis, false);

    }

    @Override
    public void loop () {
        ramp.run();

        telemetry.addData("Pitch", chassis.pitch());
    }

    @Override
    public void stop () {
        ramp.stop();
    }
}
