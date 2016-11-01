package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractComplexDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

/**
 * Created by tyarksc00 on 10/27/16.
 */
@Autonomous(name = "Colleen's Blue Dumb Autonomous", group = "Generic OpModes")
public class ColleenAutonomous2 extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        AbstractComplexDrive drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);

        waitForStart();

        drive.moveInches(Direction.FORWARD, 43, .2);
        drive.rotateDegrees(Direction.RIGHT, 90, .2);
        drive.moveInches(Direction.FORWARD, 10, .2);
        drive.rotateDegrees(Direction.RIGHT, 45, .2);
        drive.moveInches(Direction.FORWARD, 20, .2);


    }

}
