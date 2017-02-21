package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.hardware.*;

import org.ashebots.ftcandroidlib.complexOps.*;

public class ShootBall extends AutoRoutine {
    Chassis chassis;
    AdvMotor sweeperTop;
    AdvMotor sweeper;
    AdvMotor accelerator;
    Timer timer = new Timer();
    double angle;
    boolean ball1;
    boolean ball0;
    public ShootBall(Chassis chassis, AdvMotor sweeperTop, AdvMotor sweeper, AdvMotor accelerator, double angle, int balls) {
        this.chassis = chassis;
        this.sweeper = sweeper;
        this.sweeperTop = sweeperTop;
        this.accelerator = accelerator;
        this.angle = angle;
        this.sweeper.setTargetSpeed(6000);
        this.sweeperTop.setTargetSpeed(6000);
        ball1 = balls == 1;
        ball0 = balls == 0;
    }
    @Override
    public boolean states(int step) { //aim
        if (step==0) {
            double difference = chassis.angle() - angle; //difference between the angles
            double spd = 0.25;
            if (Math.abs(difference)<25) {
                spd = Math.abs(difference)/100;
            }
            if (difference > 0){ //move the correct direction
                chassis.turnMotors(-spd);
            } else {
                chassis.turnMotors(spd);
            }
            state.state((Math.abs(difference)<2.5), 1);
        }
        if (step==1) { //wait until motor is fully sped up
            if (ball0) return true;
            accelerator.setMotor(0.18);
            state.state(accelerator.speed()>700 || timer.tRange(2000), 2);
        }
        if (step==2) { //shoot
            sweeperTop.setMotor(-1);
            state.state(timer.tRange(1500),3); //run until it has fed the first ball
        }
        if (step==3) { //respeed as friction just slowed it
            if (ball1) return true;
            accelerator.setMotor(0.26);
            sweeper.setMotor(1);
            state.state(accelerator.speed()>1000 || timer.tRange(2000), 4);
        }
        if (step==4) {
            sweeper.setMotor(1);
            sweeperTop.setMotor(-1);
            if (timer.tRange(1500)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void stop() {
        chassis.stop();
        accelerator.stop();
        sweeper.stop();
        sweeperTop.stop();
    }
    @Override
    public void between() {
        chassis.stop();
        sweeper.stop();
        timer.resetTimer();
    }
    public void reset() {
        state.state(true, 0);
    }
}