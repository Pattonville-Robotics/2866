package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by skaggsm on 11/14/15.
 */
public class MRGyroHelper {

    public static final String TAG = "GYROHELPER";
    private ModernRoboticsI2cGyro gyro;
    private LinearOpMode linearOpMode;

    public MRGyroHelper(ModernRoboticsI2cGyro gyro, LinearOpMode linearOpMode) {
        this.gyro = gyro;
        this.linearOpMode = linearOpMode;
    }

    public void calibrateAndWait() throws InterruptedException {
        linearOpMode.telemetry.addData(TAG, "Starting calibration...");
        gyro.calibrate();
        while (gyro.isCalibrating()) {
            linearOpMode.waitForNextHardwareCycle();
        }
        linearOpMode.telemetry.addData(TAG, "Calibration complete!");
    }

    public int getIntegratedZValue() {
        return gyro.getIntegratedZValue();
    }
}
