package org.firstinspires.ftc.teamcode.auto.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.teamcode.R;

/**
 * Created by jezebelquit on 11/12/16.
 */
@Autonomous(name="VuefriaTest")
public class VueforiaTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters parans = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parans.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parans.vuforiaLicenseKey = "Adz4uVb/////AAAAGTTgOpGudEXPhq0rfEmXQQlV8jmI3grQmsKFdm3b/TmyXQrrNFBUP/axQdDclnPwypGWbahlLCoFTKj6LSaWv+ZWx8Gju+Nsg/Tpe7ohKJ9vhiVbUiSYkrZWSWMCpUitwZCSH8h8bzuBePNmjmq1Cy8VLs/K7CCRJNZHLp4ruYM5QqXhYeBZ0vbb2QScEHAqOZ2qumf6BCixcTrXDZD6mPVVhc06k9A28AblyCsaE8McRP1DwXH0YiID7pCwJ8/iHr1eJyh3WqIo7eQt6gus0Q+BxUgjScBpBkfq0SXU2H1pfcwBXn27tTp9GFoEDxNw8GZUQNwF31riJQmLHdvLt9hRSLosBHNkKqqeiCuzydXm";
        parans.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parans);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);


        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        waitForStart();

        beacons.activate();



        while (opModeIsActive())
            for (VuforiaTrackable beac : beacons){
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

                if(pose !=null){
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(beac.getName() + "-Translation", translation);

                    double xBasisX = pose.get(0,0);
                    double angle = Math.toDegrees(Math.asin(xBasisX));

                    telemetry.addData(beac.getName() + "-Rotation", angle);
                }
            }
        telemetry.update();
    }

}
