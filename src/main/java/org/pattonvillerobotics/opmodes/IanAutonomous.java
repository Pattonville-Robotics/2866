package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

/**
 * Created by mckeowni on 10/29/16.
 */
@Autonomous (name = "Ian's Red Dumb Autonomous", group = "Generic OpModes")
public class IanAutonomous extends LinearOpMode{
    @Override
    public  void runOpMode() throws InterruptedException {
        AbstractComplexDrive drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);

        waitForStart();

        drive.moveInches(Direction.FORWARD, 28, .2);
        drive.wait(500);
        drive.rotateDegrees(Direction.LEFT, 120, .2);
    }

}

