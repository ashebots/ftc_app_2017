package org.firstinspires.ftc.teamcode.auto;

import android.graphics.Bitmap;
import android.hardware.camera2.*;
import android.hardware.camera2.CameraDevice;

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
        if (pose==null) return 400;
        return -pose.getTranslation().get(2);
    }
    //finds left/right distance relative to camera of target, right is positive. In millimeters
    public double rawPicSide(int pic) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacons.get(pic).getListener()).getPose();
        if (pose==null) return 0;
        return -pose.getTranslation().get(0);
    }
    //Find angle to target. In degrees
    public double picAngle(int pic) {
        OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacons.get(pic).getListener()).getPose();
        //If no target found return 180
        if (pose==null) return 180;
        double angle = Math.toDegrees(Math.asin(pose.get(0,2)));
        if (angle < -180) {
            angle += 360;
        }
        return angle;
    }

    public double picSide(int pic) {
        double x = rawPicSide(pic);
        if (x == 0) return 0;
        double z = picDistance(pic);
        double angle = Math.toRadians(picAngle(pic));
        return Math.sin(angle)*(z - x / Math.tan(angle));
    }

    Image image = null;
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

    public int leftBlue;
    public int rightBlue;
    public int leftRed;
    int pixelSkip = 10;
    public boolean getColorSide(Image i) {
        totalblue = 0;
        totalred = 0;
        Bitmap bm = Bitmap.createBitmap(1280,720,Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(i.getPixels());
        leftBlue = 0;
        leftRed = 0;
        for (int x = 360; x < 720; x+=pixelSkip) {
            for (int y = 0; y < 1280; y+=pixelSkip) {
                int pixel = bm.getPixel(y, x);
                toRGB(pixel);
                if (blue > 20 && red < 20) {
                    leftBlue++;
                }
                if (red > 20 && blue < 20) {
                    leftRed++;
                }
            }
        }
        rightBlue = 0;
        for (int x = 0; x < 360; x+=pixelSkip) {
            for (int y = 0; y < 1280; y+=pixelSkip) {
                int pixel = bm.getPixel(y, x);
                toRGB(pixel);
                if (blue > 20 && red < 20) {
                    rightBlue++;
                }
            }
        }
        return leftBlue > rightBlue;
    }

    public int blue;
    public int red;
    public int totalblue;
    public int totalred;
    public void toRGB(int pixel) {
        blue = pixel & 31;
        totalblue += blue;
        red = (pixel >> 11) & 31;
        totalred += red;
    }
}