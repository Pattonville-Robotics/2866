package org.pattonvillerobotics.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.math3.linear.RealVector;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.KalmanFilterGuidance;

/**
 * Created by skaggsm on 10/25/16.
 */

@Autonomous(name = "Kalman Filter Test", group = OpModeGroups.TESTING)
public class KalmanFilterTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final EncoderDrive encoderDrive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        final ModernRoboticsI2cGyro gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        //gyro.calibrate();
        //while (gyro.isCalibrating())
        //    idle();
        double sum = gyro.rawZ();
        int count = 1;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 4000) {
            sum += gyro.rawZ();
        }
        final KalmanFilterGuidance kalmanFilterGuidance = new KalmanFilterGuidance(this, encoderDrive, gyro, CustomizedRobotParameters.VUFORIA_PARAMETERS, sum / count);

        telemetry.addData("Lifecycle", "Initialization Complete, calibrated gyro to " + (sum / count));
        telemetry.update();

        waitForStart();

        kalmanFilterGuidance.run();

        Thread printThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStopRequested()) {
                    synchronized (kalmanFilterGuidance.kalmanFilter) {
                        RealVector currentState = kalmanFilterGuidance.getCurrentState();
                        telemetry.addData("Printout", "Current Angle: " + currentState.getEntry(6) + " degrees\nCurrent X: " + currentState.getEntry(0) + " in\nCurrent Y: " + currentState.getEntry(1) + " in");
                        telemetry.update();
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        printThread.start();

        //encoderDrive.moveInches(Direction.FORWARD, 24, .2);
        //encoderDrive.rotateDegrees(Direction.RIGHT, 90, .2);
        //encoderDrive.moveInches(Direction.FORWARD, 24, .2);
        Thread.sleep(30000);

        kalmanFilterGuidance.stop();
        printThread.interrupt();
    }
}
