package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.commoncode.vision.ftc.resq.Beacon;

/**
 * Created by skaggsm on 11/15/16.
 */

public final class CommonAutonomous {

    public static final double
            WALL_TO_BALL_DISTANCE = 56,
            TILE_SIZE = 22,
            COS_45_I = 1 / FastMath.cos(FastMath.toRadians(45)),
            BACKUP_DISTANCE = 12;

    public static void tile1ToBeacon1(VuforiaNav vuforiaNav, BeaconColorDetection beaconColorDetection, BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor) {
        tile1ToBeacon1(vuforiaNav, beaconColorDetection, beaconPresser, drive, linearOpMode, allianceColor, 0L);
    }

    private static void tile1ToBeacon1(VuforiaNav vuforiaNav, BeaconColorDetection beaconColorDetection, BeaconPresser beaconPresser, AbstractComplexDrive drive, LinearOpMode linearOpMode, AllianceColor allianceColor, long delayMS) {
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

        drive.moveInches(Direction.FORWARD, 6, .5);
        drive.rotateDegrees(direction1, 45, 0.25);
        drive.moveInches(Direction.FORWARD, 56, .5);
        drive.rotateDegrees(direction2, 45, 0.25);
        drive.moveInches(Direction.FORWARD, 10, .5);

        beaconPresser.setLeftServoUp();
        beaconPresser.setRightServoUp();
        linearOpMode.sleep(1000);

        beaconColorDetection.setAnalysisMethod(Beacon.AnalysisMethod.COMPLEX);
        Beacon.BeaconAnalysis beaconAnalysis = beaconColorDetection.analyzeFrame(vuforiaNav.getImage());

        if ((beaconAnalysis.isLeftRed() && allianceColor == AllianceColor.RED) ||
                (beaconAnalysis.isLeftBlue() && allianceColor == AllianceColor.BLUE))
            beaconPresser.setLeftServoDown();

        if ((beaconAnalysis.isRightRed() && allianceColor == AllianceColor.RED) ||
                (beaconAnalysis.isRightBlue() && allianceColor == AllianceColor.BLUE))
            beaconPresser.setRightServoDown();

        float[] location;
        final double approachSpeed = .25;

        while (linearOpMode.opModeIsActive()) {
            vuforiaNav.getNearestBeaconLocation();
            location = vuforiaNav.getLocation();

            double distanceX = location[0] / VuforiaNav.MM_PER_INCH, distanceY = location[1] / VuforiaNav.MM_PER_INCH;

            if (distanceX < 6)
                break;

            double multiplier = distanceX * (distanceY / 400);

            drive.moveFreely(approachSpeed * (1 + multiplier), approachSpeed * (1 - multiplier));
        }
        drive.stop();
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
