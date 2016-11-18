package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.robotclasses.CommonAutonomous;

/**
 * Created by mckeowni on 11/17/16.
 */

public class WallToMidpointAutonomous {

    @Autonomous(name = "RED Tile 1 to Midpoint", group = OpModeGroups.MAIN)
    public static final class RedTile1 extends LinearOpMode {
        @Override
        public void runOpMode() throws InterruptedException {
            final EncoderDrive encoderDrive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
            waitForStart();
            CommonAutonomous.tile1ToMidpoint(encoderDrive, this, AllianceColor.RED);
}
