package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.hardware.Servo;

import org.ashebots.ftcandroidlib.complexOps.*;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

public class PressButton extends AutoRoutine {
    IMUChassis chassis;
    Servo servo;
    VuforiaImaging vuforia = new VuforiaImaging();

    Scaler foot;

    int button = 0;
    Vector moveToLoc;

    public static final String VUFORIA_KEY = "";

    public PressButton(IMUChassis c, Scaler s, Servo srv, int t) {
        vuforia.startup();
        chassis = c;
        foot = s;
        servo = srv;
    }
    @Override
    public boolean states(int step) {
        //Calculate distances and angles
        double distanceAway = vuforia.picDistance();
        double distanceToSide = vuforia.picSide();
        double angleFromPicture = vuforia.picAngle();
        //State Machine
        switch (step) {

        }
        return false;
    }
    @Override
    public void stop() {
        chassis.stop();
    }
    @Override
    public void between() {
        chassis.resetEncs();
    }
}
