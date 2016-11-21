package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.hardware.*;

import org.ashebots.ftcandroidlib.complexOps.*;

public class ShootBall extends AutoRoutine {
    Chassis chassis;
    AdvMotor sweeper;
    AdvMotor accelerator;
    Timer timer = new Timer();
    double angle;
    double oldEnc = 0;
    double oldChange = 0;
    int shootcount = 0;
    public ShootBall(Chassis chassis, AdvMotor sweeper, AdvMotor accelerator, double angle) {
        this.chassis = chassis;
        this.sweeper = sweeper;
        this.accelerator = accelerator;
        this.angle = angle;
    }
    @Override
    public boolean states(int step) { //aim
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
        if (step==1) { //wait until motor is fully sped up
            int reqSpeed = 100;
            double encoder = accelerator.getEnc(); //position
            double change = (encoder - oldEnc) * 100; //this is to convert it from Ticks/centisecond to Ticks/second
            double speedDiff = change - reqSpeed;
            double speedDiffO = oldChange - reqSpeed;
            state.state((speedDiff>=0 && speedDiffO<=0) || (speedDiffO>=0 && speedDiff<=0),2); //if sign on speed difference changed, run
            oldChange = change; //used to see if the speed crossed the target
            oldEnc = encoder;
            if (change<reqSpeed) {
                accelerator.setMotor(1);
            } else {
                accelerator.setMotor(0); //stops if it's too fast, to re speed up
            }
            if (shootcount>=2) return true; //how many balls have been shot
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (step==2) { //shoot
            accelerator.setMotor(1);
            sweeper.setMotor(1);
            if (timer.tRange(1000)) {
                shootcount++;
                state.state(true,1);
            }
        }
        return false;
    }
    @Override
    public void stop() {
        chassis.stop();
        accelerator.stop();
        sweeper.stop();
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
