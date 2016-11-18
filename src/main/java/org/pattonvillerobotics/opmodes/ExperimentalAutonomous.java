package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.robotclasses.BeaconPresser;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;

/**
 * Created by skaggsm on 11/18/16.
 */

public final class ExperimentalAutonomous {
    @Autonomous(name = "BLUE Tile 1 to Beacon 1", group = OpModeGroups.TESTING)
    public static final class BlueTile1 extends LinearOpMode {
        @Override
        public void runOpMode() throws InterruptedException {
            final EncoderDrive encoderDrive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
            VuforiaNav vuforiaNav = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
            BeaconColorDetection beaconColorDetection = new BeaconColorDetection(hardwareMap);
            BeaconPresser beaconPresser = new BeaconPresser(hardwareMap);
            waitForStart();
            CommonAutonomous.tile1ToBeacon1(vuforiaNav, beaconColorDetection, beaconPresser, encoderDrive, this, AllianceColor.BLUE);
        }
    }
}
