package org.firstinspires.ftc.teamcode.teleop;
import org.ashebots.ftcandroidlib.complexOps.*;

public class SimpleTeleOp extends AdvOpMode {
    Chassis motors;
    JoyEvent f = new JoyEvent(1.0,1.0,0.0,0.1);
    JoyEvent n = new JoyEvent(0.4,0.55,0.0,0.1);
    JoyEvent s = new JoyEvent(0.1,0.15,0.0,0.1);
    @Override
    public void init() {
        l.logDebug("Init Starting...");
        motors = chassis("Left","Right");
        l.logDebug("Init Finished!");
    }
    @Override
    public void loop() {
        l.logDebug("Loop Iteration");
        double[] values = s.calc(gamepad1.left_stick_x, gamepad1.left_stick_y);
        if (gamepad1.x) values = f.calc(gamepad1.left_stick_x, gamepad1.left_stick_y); //Fast Movement
        if (gamepad1.y) values = s.calc(gamepad1.left_stick_x, gamepad1.left_stick_y); //Slow Movement
        if (gamepad1.b) { //Reverse Movement
            motors.moveMotors(-values[0],-values[1]);
        } else motors.moveMotors(values[0],values[1]);
    }
    @Override
    public void stop() {
        l.logDebug("Robot Stopped");
        motors.stop();
    }
}