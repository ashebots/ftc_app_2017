package org.firstinspires.ftc.teamcode.auto;

import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.firstinspires.ftc.teamcode.R;

/**
 * Created by apple on 11/10/16.
 */

public class VuforiaImaging {
    VuforiaTrackables beacons;
    public void startup() {
        VuforiaLocalizer.Parameters parans = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parans.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parans.vuforiaLicenseKey = "Adz4uVb/////AAAAGTTgOpGudEXPhq0rfEmXQQlV8jmI3grQmsKFdm3b/TmyXQrrNFBUP/axQdDclnPwypGWbahlLCoFTKj6LSaWv+ZWx8Gju+Nsg/Tpe7ohKJ9vhiVbUiSYkrZWSWMCpUitwZCSH8h8bzuBePNmjmq1Cy8VLs/K7CCRJNZHLp4ruYM5QqXhYeBZ0vbb2QScEHAqOZ2qumf6BCixcTrXDZD6mPVVhc06k9A28AblyCsaE8McRP1DwXH0YiID7pCwJ8/iHr1eJyh3WqIo7eQt6gus0Q+BxUgjScBpBkfq0SXU2H1pfcwBXn27tTp9GFoEDxNw8GZUQNwF31riJQmLHdvLt9hRSLosBHNkKqqeiCuzydXm";
        parans.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parans);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);


        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        //Wheels, Tools, Lego, Gears
        beacons.activate();
    }
    public double picDistance(int pic) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacons.get(pic).getListener()).getPose();
        if (pose==null) return 0;
        return pose.getTranslation().get(2);
    }
    public double picSide(int pic) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacons.get(pic).getListener()).getPose();
        if (pose==null) return 0;
        return pose.getTranslation().get(0);
    }
    public double picAngle(int pic) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacons.get(pic).getListener()).getPose();
        if (pose==null) return 180;
        return Math.toDegrees(Math.asin(pose.get(0,0)));
    }
}
