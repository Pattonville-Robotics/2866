package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.DirectionEnum;
import org.pattonvillerobotics.team2866.robotclasses.Drive;

/**
 * Created by Kevin Stewart & James McMahon on 10/15/15.
 */
public class AutoB extends LinearOpMode {

    public static final String TAG = "TestAutonomous";

    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap);

        waitForStart();

        drive.moveInches(DirectionEnum.FORWARDS, 30, 1); //Make sure this doesn't cross over! Go 30 inches
        drive.rotateDegrees(DirectionEnum.RIGHT, 45, 1); //Make sure this goes at a 45˚ angle!
        drive.moveInches(DirectionEnum.FORWARDS, 92, 1); //92 inches or w/e up to the rescue bit
        drive.rotateDegrees(DirectionEnum.RIGHT, 45, 1);
        drive.moveInches(DirectionEnum.FORWARDS, 18, 1); //18 inches: NIN*2
        drive.moveInches(DirectionEnum.BACKWARDS, 18, 1);
        drive.rotateDegrees(DirectionEnum.LEFT, 45, 1);
        drive.moveInches(DirectionEnum.BACKWARDS, -18, 1);
        drive.rotateDegrees(DirectionEnum.LEFT, 90, 1);
        drive.moveInches(DirectionEnum.FORWARDS, 102, 1); //Measurement required
        drive.stop();
    }
}
