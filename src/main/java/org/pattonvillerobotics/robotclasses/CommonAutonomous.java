package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;

/**
 * Created by skaggsm on 11/15/16.
 */

public final class CommonAutonomous {
    public static void tile1ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile1ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        switch (allianceColor) {
            case RED:
            case BLUE:
                drive.moveInches(Direction.FORWARD, 60, .5);
                break;
        }
    }
}
