package org.pattonvillerobotics.team2866.opmodes.testing;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;

/**
 * Created by skaggsm on 1/28/16.
 */
@OpMode("GyroTest")
public class GyroTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(hardwareMap, this);
        waitForStart();

        int numSamples = 0;
        double currentSum = 0;

        while (opModeIsActive()) {
            for (int i = 0; i < 25; i++)
                waitForNextHardwareCycle();
            currentSum += drive.gyro.gyro.rawZ();
            numSamples++;
            Log.e("GYRO", "Average: " + (currentSum / numSamples));
        }
    }
}
