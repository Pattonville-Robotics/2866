package org.pattonvillerobotics.team2866.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.OpMode;
import org.pattonvillerobotics.team2866.opmodes.CommonAutonomous;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;
import org.pattonvillerobotics.team2866.robotclasses.controllables.SuperBlocker;
import org.pattonvillerobotics.team2866.robotclasses.controllables.ZipRelease;

/**
 * Created by skeltonn on 11/20/15.
 * <p/>
 * TODO: Measure and write OpMode
 */
@OpMode("Red Park 2")
public class AutoBucketParkRedTwo extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);
        ZipRelease zipRelease = new ZipRelease(hardwareMap);
        SuperBlocker blocker = new SuperBlocker(hardwareMap, this);

        waitForStart();

        blocker.moveVertical(Direction.MID);
        blocker.setPosition(Direction.UP);

        CommonAutonomous.leavePositionTwo(drive);
        drive.sleep(1000);
        drive.rotateDegrees(Direction.LEFT, 45, 0.5); //Make sure this goes at a 45˚ angle!
        drive.sleep(1000);
        CommonAutonomous.secondPositionTravel(drive, blocker);
        drive.sleep(1000);
        drive.rotateDegrees(Direction.LEFT, 45, 0.5);
        drive.sleep(1000);
        CommonAutonomous.dumpClimber(drive, climberDumper);

        drive.moveInches(Direction.FORWARDS, 12, .75);
        drive.rotateDegrees(Direction.RIGHT, 90, .5);
        drive.moveInches(Direction.BACKWARDS, 24, .75);

        drive.stopDriveMotors();
    }
}
