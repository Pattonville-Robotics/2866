package org.pattonvillerobotics.opmodes;

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
                .build();
    }
}
