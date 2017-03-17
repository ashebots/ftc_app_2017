package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ashebots.ftcandroidlib.complexOps.*;
import org.lasarobotics.vision.android.Cameras;
import org.lasarobotics.vision.ftc.resq.Beacon;
import org.lasarobotics.vision.opmode.ColorImaging;
import org.lasarobotics.vision.opmode.VisionOpMode;
import org.lasarobotics.vision.opmode.extensions.CameraControlExtension;
import org.lasarobotics.vision.util.ScreenOrientation;
import org.opencv.core.Size;

/**
 * Created by apple on 9/15/16.
 */
public class ModularAuto extends AutoRoutine {
    double[][] pos;
    Chassis chassis;
    Scaler foot;
    AdvMotor sweeperTop;
    public Vector next;
    public AutoRoutine special;
    public AdvMotor sweeper;
    public AdvMotor accelerator;
    boolean blue = false;
    public VuforiaImaging vuforia;
    public ColorImaging color;
    int numBalls = 2;
    public ColorSensor lineDetector;

    //COORDINATES in feet
    static public double[] RAMP_PARK = {2, 2}; //gets onto the ramp fully
    static public double[] CLOSE_PARK = {6,5};
    static public double[] FAR_PARK = {5,6};
    static public double[] CLOSE_BEACON = {2,5};
    static public double[] FAR_BEACON = {2,9};
    static public double[] BEACON_HUB = {2.5,6.5};
    static public double[] CLOSE_HUB = {6,2};
    static public double[] FAR_HUB = {4,2};
    static public double[] RIGHT_HUB = {8,1.5};
    static public double[] CLOSE_THROW = {6,3.5};
    static public double[] FAR_THROW = {4,3.5};
    static public double[] BEACON_THROW = {3.5,6};
    static public double[] LEFT_START = {4,0.75};
    static public double[] CENTER_START = {6,0.75};
    static public double[] RIGHT_START = {8,0.75}; //8, 0.75

    int wait = 0;
    Timer timer = new Timer();

    public ModularAuto(double[][] position, boolean blue, Chassis c, Scaler s, AdvMotor accelerator, AdvMotor sweeper, AdvMotor sweeperTop, int numBalls) {
        this.sweeperTop = sweeperTop;
        this.blue = blue;
        //puts these values in the program
        pos = position;
        chassis = c;
        foot = s;
        this.accelerator = accelerator;
        this.sweeper = sweeper;
        this.numBalls = numBalls;
        //between calculates the next move. Called between because it runs between each step
        between();
    }

    public ModularAuto(double[][] position, boolean blue, Chassis c, Scaler s, AdvMotor accelerator, AdvMotor sweeper, AdvMotor sweeperTop, int numBalls, int time) {
        this.sweeperTop = sweeperTop;
        this.blue = blue;
        //puts these values in the program
        pos = position;
        chassis = c;
        foot = s;
        wait = time;
        this.accelerator = accelerator;
        this.sweeper = sweeper;
        this.numBalls = numBalls;
        //between calculates the next move. Called between because it runs between each step
        between();
    }

    public ModularAuto(double[][] position, boolean blue, Chassis c, Scaler s, AdvMotor accelerator, AdvMotor sweeper, AdvMotor sweeperTop, int numBalls, HardwareMap hardwareMap) {
        this.sweeperTop = sweeperTop;
        this.blue = blue;
        //puts these values in the program
        pos = position;
        chassis = c;
        foot = s;
        this.accelerator = accelerator;
        this.sweeper = sweeper;
        this.numBalls = numBalls;
        initVuforia(hardwareMap, hardwareMap.colorSensor.get("Color"));
        //between calculates the next move. Called between because it runs between each step
        between();
    }

    public ModularAuto(double[][] position, boolean blue, Chassis c, Scaler s, AdvMotor accelerator, AdvMotor sweeper, AdvMotor sweeperTop, int numBalls, HardwareMap hardwareMap, int time) {
        this.sweeperTop = sweeperTop;
        this.blue = blue;
        //puts these values in the program
        pos = position;
        chassis = c;
        foot = s;
        wait = time;
        this.accelerator = accelerator;
        this.sweeper = sweeper;
        this.numBalls = numBalls;
        initVuforia(hardwareMap, hardwareMap.colorSensor.get("Color"));
        //between calculates the next move. Called between because it runs between each step
        between();
    }

    @Override
    public void stop() {
        next.stop();
        if (special != null) {
            special.stop();
        }
        chassis.stop();
    }

    @Override
    //this code does
    public void between() {
        int reversal = 1; //for blue throw angle switch
        int s = getStep();
        if (blue) { //switch
            next = new Vector(12 - pos[s][0], pos[s][1], 12 - pos[s + 1][0], pos[s + 1][1], foot, chassis); //blue
            reversal = -1;
        } else next = new Vector(pos[s][0],pos[s][1],pos[s+1][0],pos[s+1][1],foot,chassis); //red

        if (pos[s+1]==ModularAuto.RAMP_PARK) {
            special = new Ramp(chassis, blue);
        }
        else if (pos[s+1]==ModularAuto.CLOSE_BEACON) {
            int beacon = 3;
            if (blue) {
                beacon = 0;
            }
            special = new PressButton(chassis, foot, beacon, blue, color, lineDetector);
        }
        else if (pos[s+1]==ModularAuto.FAR_BEACON) {
            int beacon = 1;
            if (blue) {
                beacon = 2;
            }
            special = new PressButton(chassis, foot, beacon, blue, color, lineDetector);
        }
        else if (pos[s+1]==ModularAuto.CLOSE_THROW) {
            special = new ShootBall(chassis, sweeperTop, sweeper, accelerator, -30*reversal, numBalls);
        }
        else if (pos[s+1]==ModularAuto.FAR_THROW) {
            special = new ShootBall(chassis, sweeperTop, sweeper, accelerator, 30*reversal, numBalls);
        }
        else if (pos[s+1]==ModularAuto.BEACON_THROW) {
            special = new ShootBall(chassis, sweeperTop, sweeper, accelerator, 120*reversal, numBalls);
        }
        else if (pos[s+1]==ModularAuto.CLOSE_PARK) {
            special = new Ball(sweeper, chassis);
        }
        else {
            special = null;
        }
        if (special != null) {
            special.reset();
        }
    }

    boolean reset = true;

    @Override
    public boolean states(int step) {
        if (reset) {
            reset = false;
            timer.resetTimer();
        }
        if (!timer.tRange(wait)) {
            return false;
        }
        if (next.getStep()==-1 && special !=null && special.getStep()>-1) {
            special.run();
        } else {
            next.run();
        }
        state.state(next.getStep()==-1 && (special==null || special.getStep()==-1),step+1);
        if (getStep()+1==pos.length) {
            return true;
        }
        return false;
    }

    public void initVuforia(HardwareMap hardwareMap, ColorSensor lineDetector) {
        this.lineDetector = lineDetector;
        color = new ColorImaging(hardwareMap);
        color.init();
        color.setCamera(Cameras.PRIMARY);
        color.setFrameSize(new Size(720, 1280));
        color.enableExtension(VisionOpMode.Extensions.BEACON);         //Beacon detection
        color.enableExtension(VisionOpMode.Extensions.ROTATION);       //Automatic screen rotation correction
        color.enableExtension(VisionOpMode.Extensions.CAMERA_CONTROL); //Manual camera control
        color.beacon.setAnalysisMethod(Beacon.AnalysisMethod.FAST);
        color.beacon.setColorToleranceRed(0);
        color.beacon.setColorToleranceBlue(0);
        color.rotation.setIsUsingSecondaryCamera(false);
        color.rotation.disableAutoRotate();
        color.rotation.setActivityOrientationFixed(ScreenOrientation.PORTRAIT);
        color.cameraControl.setColorTemperature(CameraControlExtension.ColorTemperature.AUTO);
        color.cameraControl.setAutoExposureCompensation();
    }
    public void setNumBalls(int i) {
        numBalls = i;
    }
}