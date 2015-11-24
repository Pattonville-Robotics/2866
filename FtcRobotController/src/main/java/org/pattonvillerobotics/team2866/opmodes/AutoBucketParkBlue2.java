package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.ZipRelease;

/**
 * Created by skeltonn on 11/20/15.
 *
 * TODO: Measure and write OpMode
 */
@OpMode("Blue Park 2")
public class AutoBucketParkBlue2 extends LinearOpMode {
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

        drive.moveInches(Direction.BACKWARDS, 69, 1); //66
        drive.rotateDegrees(Direction.RIGHT, 45, .5); //Make sure this goes at a 45˚ angle!
        drive.moveInches(Direction.BACKWARDS, 24, 1); //92 inches or w/e up to the rescue bit
        drive.rotateDegrees(Direction.RIGHT, 45, .5);
        climberDumper.move(Direction.UP); //Moves the servo to dump the lil guys into the basket
        climberDumper.move(Direction.DOWN);
        drive.stop();
    }
}
