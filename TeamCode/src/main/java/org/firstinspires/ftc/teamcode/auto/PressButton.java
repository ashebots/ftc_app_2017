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
    VuforiaLocalizer localizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;
    Servo servo;
    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    Scaler foot;

    int button = 0;
    Vector moveToLoc;

    public static final String VUFORIA_KEY = "";

    public PressButton(IMUChassis c, Scaler s, Servo srv, int t) {
        setupVuforia(t);
        lastKnownLocation = createMatrix(0,0,0,0,0,0);
        visionTargets.activate();
        chassis = c;
        foot = s;
        servo = srv;
    }
    @Override
    public boolean states(int step) {
        OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();
        if (latestLocation != null)
            lastKnownLocation = latestLocation;
        //Calculate distances and angles
        double distanceAway = 0;
        double distanceToSide = 0;
        double angleFromPicture = 0;
        //State Machine
        switch (step) {
            case 0:
                double targ = chassis.r(-90 - chassis.angle());
                if (targ<0) {
                    chassis.turnMotors(-0.2);
                } else {
                    chassis.turnMotors(0.2);
                }
                state.state(Math.abs(targ)<5,1);
                break;
            case 1:
                if (angleFromPicture<0) {
                    chassis.turnMotors(0.2);
                } else {
                    chassis.turnMotors(-0.2);
                }
                state.state(Math.abs(angleFromPicture)<0.5,2);
                break;
            case 2:
                if (distanceAway<0) {
                    chassis.setMotors(-0.05);
                } else {
                    chassis.setMotors(0.05);
                }
                state.state(Math.abs(distanceAway)<0.5,3);
                break;
            case 3:
                //scan colors and do trig
                button = 1;
                moveToLoc = new Vector(distanceToSide,distanceAway,0.5,-0.25,foot,chassis);
                break;
            case 4:
                moveToLoc.run();
                state.state(moveToLoc.getStep()==-1,5);
                break;
            case 5:
                chassis.turnMotors(0.2);
                state.state(chassis.angle()>-1,6);
                break;
            case 6:
                chassis.setMotors(0.2);
                state.state(button == 1 && chassis.mRange(foot.s(1),INF),7);
                state.state(chassis.mRange(foot.s(1.5),INF),7);
                break;
            case 7:
                servo.setPosition(50);
                break;
            case 8:
                //move forward dist from first button to exit > -1
                //if moved from second button and should hit second button > -1
                break;
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

    public void setupVuforia(int t) {
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        localizer = ClassFactory.createVuforiaLocalizer(parameters);

        visionTargets = localizer.loadTrackablesFromAsset("FTC_2016-17");

        target = visionTargets.get(t);
        target.setLocation(createMatrix(0,0,0,0,0,0));

        phoneLocation = createMatrix(0,0,0,0,0,0);

        listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocation, parameters.cameraDirection);
    }

    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w) {
        return OpenGLMatrix.translation(x, y, z).
            multiplied(Orientation.getRotationMatrix(
                    AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));
    }
    public String formatMatrix(OpenGLMatrix matrix) {
        return matrix.formatAsTransform();
    }
}
