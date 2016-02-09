package org.pattonvillerobotics.team2866.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.opmodes.CommonAutonomous;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.SuperBlocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ZipRelease;

/**
 * Created by Kevin Stewart & James McMahon on 10/15/15.
 */
@OpMode("Blue Mountain 2")
public class AutoBucketMountainBlueTwo extends LinearOpMode {

    public static final String TAG = "TestAutonomous";

    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);
        ZipRelease zipRelease = new ZipRelease(hardwareMap);
        SuperBlocker blocker = new SuperBlocker(hardwareMap);

        waitForStart();

        blocker.moveVertical(Direction.MID);
        blocker.setPosition(Direction.UP);

        CommonAutonomous.leavePositionTwo(drive);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.5);
        CommonAutonomous.secondPositionTravel(drive, blocker);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.5);
        CommonAutonomous.dumpClimber(drive, climberDumper);
        CommonAutonomous.dumperReturn(drive);
        drive.rotateDegrees(Direction.LEFT, 45 - 5, 0.4);
        CommonAutonomous.mountainTravel(drive);
        drive.rotateDegrees(Direction.LEFT, 90, 0.4);
        CommonAutonomous.mountainAscend(drive);
        //climbAssist.moveChain(1);
        //climbAssist.moveChain(0);
        drive.stopDriveMotors();
    }
}