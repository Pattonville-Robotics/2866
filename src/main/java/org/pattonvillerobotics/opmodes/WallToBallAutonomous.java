package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;

/**
 * Created by skaggsm on 11/15/16.
 */
public final class WallToBallAutonomous {

    @Autonomous(name = "RED Tile 1 to Ball", group = OpModeGroups.MAIN)
    public static final class RedTile1 extends LinearOpMode {
        @Override
        public void runOpMode() throws InterruptedException {
            final EncoderDrive encoderDrive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
            waitForStart();
            CommonAutonomous.tile1ToBall(encoderDrive, this, AllianceColor.RED);
        }
    }

    @Autonomous(name = "BLUE Tile 1 to Ball", group = OpModeGroups.MAIN)
    public static final class BlueTile1 extends LinearOpMode {
        @Override
        public void runOpMode() throws InterruptedException {
            final EncoderDrive encoderDrive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
            waitForStart();
            CommonAutonomous.tile1ToBall(encoderDrive, this, AllianceColor.BLUE);
        }
    }

    @Autonomous(name = "RED Tile 2 to Ball", group = OpModeGroups.MAIN)
    public static final class RedTile2 extends LinearOpMode {
        @Override
        public void runOpMode() throws InterruptedException {
            final EncoderDrive encoderDrive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
            waitForStart();
            CommonAutonomous.tile2ToBall(encoderDrive, this, AllianceColor.RED);
        }
    }

    @Autonomous(name = "BLUE Tile 2 to Ball", group = OpModeGroups.MAIN)
    public static final class BlueTile2 extends LinearOpMode {
        @Override
        public void runOpMode() throws InterruptedException {
            final EncoderDrive encoderDrive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
            waitForStart();
            CommonAutonomous.tile2ToBall(encoderDrive, this, AllianceColor.BLUE);
        }
    }
}
