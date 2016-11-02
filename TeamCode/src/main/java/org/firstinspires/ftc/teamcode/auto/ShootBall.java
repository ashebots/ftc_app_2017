package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.hardware.*;

import org.ashebots.ftcandroidlib.complexOps.*;

public class ShootBall extends AutoRoutine {
    IMUChassis chassis;
    Servo top;
    Servo bottom;
    DcMotor accelerator;
    Timer timer = new Timer();
    double angle;
    public ShootBall(IMUChassis chassis, Servo top, Servo bottom, DcMotor accelerator, double angle) {
        this.chassis = chassis;
        this.top = top;
        this.bottom = bottom;
        this.accelerator = accelerator;
        this.angle = angle;
    }
    @Override
    public boolean states(int step) {
        if (step==0) {
            double difference = chassis.angle() - angle; //difference between the angles
            double spd = 0.5;
            if (Math.abs(difference)<25) {
                spd = Math.abs(difference)/50;
            }
            if (difference > 0){
                chassis.turnMotors(spd);
            } else {
                chassis.turnMotors(-spd);
            }
            state.state((Math.abs(difference)<2.5), 1);
        }
        if (step==1) {
            accelerator.setPower(1);
            top.setPosition(0);
            bottom.setPosition(0);
            if (timer.tRange(5000)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void stop() {
        chassis.stop();
        top.setPosition(0.5);
        bottom.setPosition(0.5);
        accelerator.setPower(0);
    }
    @Override
    public void between() {
        chassis.stop();
        timer.resetTimer();
    }
    public void reset() {
        state.state(true, 0);
    }
}
