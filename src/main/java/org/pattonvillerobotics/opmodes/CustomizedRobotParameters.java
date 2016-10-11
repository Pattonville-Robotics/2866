package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by skaggsm on 9/27/16.
 */

public class CustomizedRobotParameters {
    public static final RobotParameters ROBOT_PARAMETERS;

    static {
        ROBOT_PARAMETERS = new RobotParameters.Builder()
                .encodersEnabled(true)
                .gyroEnabled(false)
                .driveGearRatio(2)
                .wheelBaseRadius(14)
                .wheelRadius(3)
                .dcMotorMaxSpeed(RobotParameters.TICKS_PER_REVOLUTION * 2)
                .rightDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
                .leftDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
                .build();
    }
}
