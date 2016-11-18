package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;

/**
 * Created by skaggsm on 11/15/16.
 */

public final class CommonAutonomous {

    public static final double
            WALL_TO_BALL_DISTANCE = 56,
            TILE_SIZE = 22,
            COS_45_I = 1 / FastMath.cos(FastMath.toRadians(45)),
            BACKUP_DISTANCE = 12;

    public static void tile1ToBeacon1(VuforiaNav vuforiaNav, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToBeacon1(vuforiaNav, drive, linearOpMode, allianceColor, 0L);
    }

    private static void tile1ToBeacon1(VuforiaNav vuforiaNav, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);


        Direction direction1 = null, direction2 = null;

        switch (allianceColor) {
            case RED:
                direction1 = Direction.LEFT;
                direction2 = Direction.LEFT;
                break;
            case BLUE:
                direction1 = Direction.RIGHT;
                direction2 = Direction.RIGHT;
                break;
        }

        vuforiaNav.getNearestBeaconLocation();
        float[] location = vuforiaNav.getLocation();

        while (linearOpMode.opModeIsActive() && location[0] / VuforiaNav.MM_PER_INCH > 2) {
            vuforiaNav.getNearestBeaconLocation();
            location = vuforiaNav.getLocation();
        }
    }

    public static void tile1ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile1ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = 16 * COS_45_I, straightDistance = 5;

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
        drive.moveInches(Direction.BACKWARD, BACKUP_DISTANCE, .75);
    }

    public static void tile2ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile2ToBall(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile2ToBall(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {

        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = 10 * COS_45_I, straightDistance = 5;

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
        drive.moveInches(Direction.FORWARD, WALL_TO_BALL_DISTANCE - straightDistance - diagonalDistance - 8, .75);
        drive.moveInches(Direction.BACKWARD, BACKUP_DISTANCE, .75);
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
        drive.moveInches(Direction.BACKWARD, BACKUP_DISTANCE, .75);
    }

    public static void tile1ToMidpoint(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToMidpoint(drive, linearOpMode, allianceColor, 0L);
    }

    public static void tile1ToMidpoint(AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
        if (delayMS > 0)
            linearOpMode.sleep(delayMS);

        Direction direction1 = null, direction2 = null;
        final double diagonalDistance = TILE_SIZE * 2 * COS_45_I, straightDistance = 5;

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

        drive.moveInches(Direction.FORWARD, 10, .5);
        drive.rotateDegrees(direction1, 45, .5);
        drive.moveInches(Direction.FORWARD, 50, .5);
        drive.rotateDegrees(direction2, 45, .5);
        drive.moveInches(Direction.FORWARD, 10, .5);
    }
}
