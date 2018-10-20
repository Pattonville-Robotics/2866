package org.pattonvillerobotics;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

public class RobotParams {
    public static RobotParameters setParams() {
        return new RobotParameters.Builder()
                .wheelRadius(1.931)
                .wheelBaseRadius(7)
                .driveGearRatio(1)
                .encodersEnabled(true)
                .rightDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
                .leftDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
                .gyroEnabled(true)
                .build();
    }

}
