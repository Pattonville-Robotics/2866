package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by stewartk02 on 9/29/16.
 */
@Autonomous(name = "Kevin Test Autonomous", group = "Generic OpModes")
public class KevinAutonomous extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AbstractComplexDrive drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);

        waitForStart();

        drive.moveInches(Direction.FORWARD, 10, .2);
        drive.rotateDegrees(Direction.LEFT, 90, .2);
        drive.moveInches(Direction.FORWARD, 10, .2);
        drive.rotateDegrees(Direction.RIGHT, 45, .2);
        drive.moveInches(Direction.FORWARD, 10, .2);
        drive.rotateDegrees(Direction.RIGHT, 135, .2);
        drive.moveInches(Direction.FORWARD, 15, .2);
        drive.rotateDegrees(Direction.RIGHT, 90, .2);
        drive.moveInches(Direction.FORWARD, 25, .2);

    }
}
