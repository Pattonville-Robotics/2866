package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.KalmanFilterGuidance;

import java.util.concurrent.TimeUnit;

/**
 * Created by skaggsm on 10/25/16.
 */

public class KalmanFilterTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final KalmanFilterGuidance kalmanFilterGuidance = new KalmanFilterGuidance();
        final EncoderDrive encoderDrive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);

        Thread encoderThread = new Thread(new Runnable() {
            long lastTimeNS = System.nanoTime();

            @Override
            public void run() {
                while (opModeIsActive()) {
                    long nowTimeNS = System.nanoTime();
                    double elapsedTimeS = (nowTimeNS - lastTimeNS) / (double) TimeUnit.SECONDS.toNanos(1);

                    //kalmanFilterGuidance.measure();
                }
            }
        });

        waitForStart();


    }
}
