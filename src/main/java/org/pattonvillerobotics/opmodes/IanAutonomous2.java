package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

/**
 * Created by mckeowni on 10/29/16.
 */

//@Autonomous(name = "Ian's Red Dumb Autonomous 2", group = OpModeGroups.TESTING)
public class IanAutonomous2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AbstractComplexDrive drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);

        waitForStart();

        drive.moveInches(Direction.FORWARD, 43, .2);
        drive.rotateDegrees(Direction.LEFT, 90, .2);
        drive.moveInches(Direction.FORWARD, 10, .2);
        drive.rotateDegrees(Direction.LEFT, 45, .2);
        drive.moveInches(Direction.FORWARD, 20, .2);
    }
}