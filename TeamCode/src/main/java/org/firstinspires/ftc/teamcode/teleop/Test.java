package org.firstinspires.ftc.teamcode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ashebots.ftcandroidlib.complexOps.*;

@TeleOp(name="Color Test", group ="TeleOp")

public class Test extends AdvOpMode {
    ColorSensor colorSensor;
    @Override
    public void init() {
        colorSensor = hardwareMap.colorSensor.get("Color");
    }
    @Override
    public void loop() {
        telemetry.addData("Red",colorSensor.red());
        telemetry.addData("Green",colorSensor.green());
        telemetry.addData("Blue",colorSensor.blue());
    }
    @Override
    public void stop() {

    }
}