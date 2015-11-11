package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.DirectionEnum;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;

/**
 * Created by Kevin Stewart & James McMahon on 10/15/15.
 */
@OpMode("Blue Autonomous")
public class AutoB extends LinearOpMode {

    public static final String TAG = "TestAutonomous";

    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap, this);
        ClimberDumper climberDumper = new ClimberDumper(hardwareMap);
        ClimbAssist climbAssist = new ClimbAssist(hardwareMap);

        waitForStart();

        drive.moveInches(DirectionEnum.BACKWARDS, 30, 1); //Make sure this doesn't cross over! Go 30 inches
        drive.rotateDegrees(DirectionEnum.RIGHT, 45, 1); //Make sure this goes at a 45˚ angle!
        drive.moveInches(DirectionEnum.BACKWARDS, 92, 1); //92 inches or w/e up to the rescue bit
        drive.rotateDegrees(DirectionEnum.RIGHT, 45, 1);
        drive.moveInches(DirectionEnum.BACKWARDS, 18, 1); //18 inches: NIN*2
        climberDumper.move(DirectionEnum.UP); //Moves the servo to dump the lil guys into the basket
        climberDumper.move(DirectionEnum.DOWN);
        drive.moveInches(DirectionEnum.FORWARDS, 18, 1);
        drive.rotateDegrees(DirectionEnum.LEFT, 45, 1);
        drive.moveInches(DirectionEnum.FORWARDS, 18, 1);
        drive.rotateDegrees(DirectionEnum.LEFT, 90, 1);
        climbAssist.motorChain(1);
        drive.moveInches(DirectionEnum.BACKWARDS, 102, 1); //Measurement required
        climbAssist.motorChain(0);
        drive.stop();
    }
}
