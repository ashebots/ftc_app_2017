package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ashebots.ftcandroidlib.complexOps.AdvOpMode;

@TeleOp(name="Version Control", group ="TeleOp")

public class VersionControl extends AdvOpMode {
    @Override
    public void init(){
    }
    @Override
    public void loop() {
        telemetry.addData("Version","1.0.0");
    }
    @Override
    public void stop() {

    }
}