package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;

/**
 * Created by skaggsm on 11/15/16.
 */

public final class CommonAutonomous {

    public static final double
            WALL_TO_BALL_DISTANCE = 56,
            TILE_SIZE = 22,
            COS_45_I = 1 / FastMath.cos(FastMath.toRadians(45));

    public static void tile1ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile1ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {

        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = 18 * COS_45_I, straightDistance = 5;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.RIGHT;
                direction2 = Direction.LEFT;
                break;
            case BLUE:
                direction1 = Direction.LEFT;
                direction2 = Direction.RIGHT;
                break;
        }
        drive.moveInches(Direction.FORWARD, straightDistance, .5);
        drive.rotateDegrees(direction1, 45, .25);
        drive.moveInches(Direction.FORWARD, diagonalDistance, .5);
        drive.rotateDegrees(direction2, 45, .25);
        drive.moveInches(Direction.FORWARD, WALL_TO_BALL_DISTANCE - straightDistance - diagonalDistance, .75);
    }

    public static void tile2ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile2ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile2ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {

        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = 4 * COS_45_I, straightDistance = 5;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.LEFT;
                direction2 = Direction.RIGHT;
                break;
            case BLUE:
                direction1 = Direction.RIGHT;
                direction2 = Direction.LEFT;
                break;
        }
        drive.moveInches(Direction.FORWARD, straightDistance, .5);
        drive.rotateDegrees(direction1, 45, .25);
        drive.moveInches(Direction.FORWARD, diagonalDistance, .5);
        drive.rotateDegrees(direction2, 45, .25);
        drive.moveInches(Direction.FORWARD, WALL_TO_BALL_DISTANCE - straightDistance - diagonalDistance, .75);
    }

    public static void tile3ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile3ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile3ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = (4 + TILE_SIZE) * 2 * COS_45_I, straightDistance = 5;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.LEFT;
                direction2 = Direction.RIGHT;
                break;
            case BLUE:
                direction1 = Direction.RIGHT;
                direction2 = Direction.LEFT;
                break;
        }
        drive.moveInches(Direction.FORWARD, straightDistance, .5);
        drive.rotateDegrees(direction1, 45, .25);
        drive.moveInches(Direction.FORWARD, diagonalDistance, .5);
        drive.rotateDegrees(direction2, 45, .25);
        drive.moveInches(Direction.FORWARD, WALL_TO_BALL_DISTANCE - straightDistance - diagonalDistance, .75);
    }
}
