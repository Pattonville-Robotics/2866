package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.ZipRelease;

/**
 * Created by mcmahonj on 12/1/15.
 */
@OpMode("Blue Mountain 3")
public class AutoBucketMountainBlue3 extends LinearOpMode {
    public static final String TAG = "TestAutonomous";

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

        CommonAutonomous.firstPosition3(drive);
        drive.rotateDegrees(Direction.RIGHT, 45, 1);
        CommonAutonomous.dumpClimber(drive, climberDumper);
        drive.rotateDegrees(Direction.LEFT, 45, 1);
        CommonAutonomous.secondPositionTravel(drive);
        drive.rotateDegrees(Direction.LEFT, 90, 1);
        drive.moveInches(Direction.FORWARDS, 50, 1); //Measurement required
        //climbAssist.moveChain(1);
        //climbAssist.moveChain(0);
        drive.stop();
    }
}
