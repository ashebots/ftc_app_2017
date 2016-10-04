package org.firstinspires.ftc.teamcode.auto;
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

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_KEY = "";

    public PressButton(IMUChassis c, int t) {
        setupVuforia(t);
        lastKnownLocation = createMatrix(0,0,0,0,0,0);
        visionTargets.activate();
        chassis = c;
    }
    @Override
    public boolean states(int step) {
        OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();
        if (latestLocation != null)
            lastKnownLocation = latestLocation;
        //Telemetry

        //State Machine
        switch (step) {
            case 0:
                //turn towards buttons, roughly > 1
                break;
            case 1:
                //turn towards buttons, exactly > 2
                break;
            case 2:
                //move to correct distance > 3
                break;
            case 3:
                //scan colors and do trig > 4
                break;
            case 4:
                //do this move > 5
                break;
            case 5:
                //turn paralell to wall, with BNO > 6
                break;
            case 6:
                //move forward until second button > 7
                //if on first button and should hit first button > 7
                break;
            case 7:
                //press button > 8
                break;
            case 8:
                //move forward dist from first button to exit > -1
                //if moved from second button and should hit second button > -1
                break;
        }
        return false;
    }
    @Override
    public void stop() {}
    @Override
    public void between() {}

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
