package org.pattonvillerobotics.team2866.opmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;

/**
 * Created by skeltonn on 11/20/15.
 *
 * TODO: Measure and write OpMode
 */
@OpMode("Red Park 2")
public class AutoBucketParkRed2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);

        waitForStart();

        CommonAutonomous.leadPosition2(drive);
        drive.rotateDegrees(Direction.LEFT, 45, 1); //Make sure this goes at a 45˚ angle!
        drive.moveInches(Direction.FORWARDS, 24, 1); //92 inches or w/e up to the rescue bit
        drive.rotateDegrees(Direction.LEFT, 45, 1);
        drive.moveInches(Direction.BACKWARDS, 18, 1); //18 inches: NIN*2
        climberDumper.move(Direction.UP); //Moves the servo to dump the lil guys into the basket
        climberDumper.move(Direction.DOWN);
        drive.stop();
    }
}
