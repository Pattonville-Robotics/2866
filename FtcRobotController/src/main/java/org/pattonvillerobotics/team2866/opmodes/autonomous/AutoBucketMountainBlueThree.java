package org.pattonvillerobotics.team2866.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.opmodes.CommonAutonomous;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ZipRelease;

/**
 * Created by stewartk02 on 11/5/15.
 * <p/>
 * TODO: Measure and write OpMode
 */
@OpMode("Blue Mountain 3")
public class AutoBucketMountainBlueThree extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);
        ZipRelease zipRelease = new ZipRelease(hardwareMap);

        zipRelease.moveLeft(Direction.DOWN);
        zipRelease.moveRight(Direction.DOWN);
        climberDumper.move(Direction.DOWN);

        waitForStart();

        CommonAutonomous.leavePositionThree(drive);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.5); //Make sure this goes at a 45˚ angle!
        CommonAutonomous.secondPositionTravel(drive);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.5);
        CommonAutonomous.dumpClimber(drive, climberDumper);
        CommonAutonomous.dumperReturn(drive);
        drive.rotateDegrees(Direction.LEFT, 45, 0.4);
        CommonAutonomous.mountainTravel(drive);
        drive.rotateDegrees(Direction.LEFT, 90, 0.4);
        CommonAutonomous.mountainAscend(drive);
        //climbAssist.moveChain(1);
        //climbAssist.moveChain(0);
        drive.stopDriveMotors();
    }
}
