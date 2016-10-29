package org.pattonvillerobotics.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
        final KalmanFilterGuidance kalmanFilterGuidance = new KalmanFilterGuidance(this, encoderDrive, gyro);

        waitForStart();

        kalmanFilterGuidance.run();
    }
}
