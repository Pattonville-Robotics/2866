package org.pattonvillerobotics.team2866.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.opmodes.CommonAutonomous;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Blocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ZipRelease;

/**
 * Created by Kevin Stewart & James McMahon on 10/15/15.
 * <p/>
 * TODO: Walk through values
 * TODO: Initialize servo values
 */
@OpMode("Blue Park 2")
public class AutoBucketParkBlueTwo extends LinearOpMode {

    public static final String TAG = "Blue Park Autonomous";

    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);
        ZipRelease zipRelease = new ZipRelease(hardwareMap);
        Blocker blocker = new Blocker(hardwareMap);

        zipRelease.moveLeft(Direction.DOWN);
        zipRelease.moveRight(Direction.DOWN);
        climberDumper.move(Direction.DOWN);

        waitForStart();

        CommonAutonomous.leavePositionTwo(drive);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.75);
        CommonAutonomous.secondPositionTravel(drive, blocker);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.75);
        CommonAutonomous.dumpClimber(drive, climberDumper);
        drive.stopDriveMotors();
    }
}
