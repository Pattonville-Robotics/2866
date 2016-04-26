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
 * Created by mcmahonj on 12/1/15.
 */
@OpMode("Red Mountain 1")
public class AutoBucketMountainRedOne extends LinearOpMode {

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

        drive.sleep(10000);

        CommonAutonomous.leavePositionOne(drive);
        drive.rotateDegrees(Direction.LEFT, 45, 0.5); //Make sure this goes at a 45˚ angle!
        CommonAutonomous.dumpClimber(drive, climberDumper);

        /*
        CommonAutonomous.dumperReturn(drive);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.4);
        CommonAutonomous.mountainTravel(drive);
        drive.rotateDegrees(Direction.RIGHT, 90, 0.4);
        CommonAutonomous.mountainAscend(drive);
        */
        //climbAssist.moveChain(1);
        //climbAssist.moveChain(0);
        drive.stopDriveMotors();
    }
}
