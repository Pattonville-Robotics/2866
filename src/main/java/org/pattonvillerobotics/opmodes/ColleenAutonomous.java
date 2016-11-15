package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

/**
 * Created by tyarksc00 on 9/29/16.
 */
@Autonomous(name = "test squares", group = OpModeGroups.TESTING)
public class ColleenAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AbstractComplexDrive drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        waitForStart();
        drive.moveInches(Direction.FORWARD, 5, .60);
        drive.rotateDegrees(Direction.RIGHT, 60, .80);
        drive.moveInches(Direction.FORWARD, 5, .60);
        drive.rotateDegrees(Direction.RIGHT, 60, .80);
        drive.moveInches(Direction.FORWARD, 5, .60);
        drive.rotateDegrees(Direction.RIGHT, 60, .80);
        drive.moveInches(Direction.FORWARD, 5, .60);
        drive.rotateDegrees(Direction.RIGHT, 60, .80);
        drive.moveInches(Direction.FORWARD, 10, .60);
        drive.rotateDegrees(Direction.RIGHT, 90, .80);
        drive.moveInches(Direction.FORWARD, 10, .60);
        drive.rotateDegrees(Direction.RIGHT, 90, .80);
        drive.moveInches(Direction.FORWARD, 10, .60);
        drive.rotateDegrees(Direction.RIGHT, 90, .80);
        drive.moveInches(Direction.FORWARD, 10, .60);
        drive.rotateDegrees(Direction.RIGHT, 90, .80);
        drive.moveInches(Direction.FORWARD, 15, .60);
        drive.rotateDegrees(Direction.RIGHT, 120, .80);
        drive.moveInches(Direction.FORWARD, 15, .60);
        drive.rotateDegrees(Direction.RIGHT, 120, .80);
        drive.moveInches(Direction.FORWARD, 15, .60);
        drive.rotateDegrees(Direction.RIGHT, 120, .80);
        drive.moveInches(Direction.FORWARD, 15, .60);
        drive.rotateDegrees(Direction.RIGHT, 120, .80);
    }
}
