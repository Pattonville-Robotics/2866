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
 * Created by skaggsm on 10/17/15.
 * <p/>
 * TODO: Walk through values
 * TODO: Initialize servo values
 */
@OpMode("Red Mountain 2")
public class AutoBucketMountainRedTwo extends LinearOpMode {

    public static final String TAG = "TestAutonomous";

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

        telemetry.addData("TEST", this.getClass().getName());

        waitForStart();

        CommonAutonomous.leavePositionTwo(drive);
        drive.rotateDegrees(Direction.LEFT, 45, 0.5);
        CommonAutonomous.secondPositionTravel(drive, blocker);
        drive.rotateDegrees(Direction.LEFT, 45, 0.5);
        CommonAutonomous.dumpClimber(drive, climberDumper);
        CommonAutonomous.dumperReturn(drive);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.4);
        CommonAutonomous.mountainTravel(drive);
        drive.rotateDegrees(Direction.RIGHT, 90, 0.4);
        CommonAutonomous.mountainAscend(drive);
        //climbAssist.moveChain(1);
        //climbAssist.moveChain(0);
        drive.stopDriveMotors();
    }
}