package org.firstinspires.ftc.teamcode.auto;

import com.vuforia.*;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.*;
import org.firstinspires.ftc.teamcode.R;

import java.util.concurrent.BlockingQueue;

/**
 * Created by apple on 11/10/16.
 */

public class VuforiaImaging {
    VuforiaTrackables beacons;
    VuforiaLocalizer locale;
    public void init() {
        VuforiaLocalizer.Parameters parans = new VuforiaLocalizer.Parameters();
        parans.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parans.vuforiaLicenseKey = "Adz4uVb/////AAAAGTTgOpGudEXPhq0rfEmXQQlV8jmI3grQmsKFdm3b/TmyXQrrNFBUP/axQdDclnPwypGWbahlLCoFTKj6LSaWv+ZWx8Gju+Nsg/Tpe7ohKJ9vhiVbUiSYkrZWSWMCpUitwZCSH8h8bzuBePNmjmq1Cy8VLs/K7CCRJNZHLp4ruYM5QqXhYeBZ0vbb2QScEHAqOZ2qumf6BCixcTrXDZD6mPVVhc06k9A28AblyCsaE8McRP1DwXH0YiID7pCwJ8/iHr1eJyh3WqIo7eQt6gus0Q+BxUgjScBpBkfq0SXU2H1pfcwBXn27tTp9GFoEDxNw8GZUQNwF31riJQmLHdvLt9hRSLosBHNkKqqeiCuzydXm";
        locale = ClassFactory.createVuforiaLocalizer(parans);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);
        beacons = locale.loadTrackablesFromAsset("FTC_2016-17");

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); //enables RGB565 format for the image
        locale.setFrameQueueCapacity(1); //tells VuforiaLocalizer to only store one frame at a time
    }
    public void start() {
        //Wheels, Tools, Lego, Gears
        beacons.activate();
    }
    //Target distance from camera in millimeters
    public double picDistance(int pic) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacons.get(pic).getListener()).getPose();
        if (pose==null) return 0;
        return -pose.getTranslation().get(2);
    }
    //finds left/right distance relative to camera of target, right is positive. In millimeters
    public double picSide(int pic) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacons.get(pic).getListener()).getPose();
        if (pose==null) return 0;
        return -pose.getTranslation().get(0);
    }
    //Find angle to target. In degrees
    public double picAngle(int pic) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacons.get(pic).getListener()).getPose();
        //If no target found return 180
        if (pose==null) return 180;
        double angle = Math.toDegrees(Math.asin(pose.get(0,0)))-90;
        if (angle < -180) {
            angle += 360;
        }
        return angle;
    }
    Image image;
    public Image getImage() {
        Frame frame = null;
        try {
            BlockingQueue<VuforiaLocalizer.CloseableFrame> queue = locale.getFrameQueue();
            if (queue.isEmpty()) return image;
            frame = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < frame.getNumImages(); i++) {
            if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                image = frame.getImage(i);
                return image;
            }
        }
        return image;
    }
}
