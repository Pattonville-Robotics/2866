package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.DirectionEnum;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;

/**
 * Created by mcmahonj on 11/10/15.
 */
@OpMode("Test Autonomous")
public class TestAutonomous extends LinearOpMode {

    public static final String TAG = "TestAutonomous";

    @SuppressWarnings("MagicNumber")
    @Override
    public void runOpMode() throws InterruptedException {

        Drive drive = new Drive(hardwareMap, this);
        waitForStart();

        telemetry.addData("FLOW", "Started move");
        telemetry.addData("FLOW", "Moving forwards");
        drive.moveInches(DirectionEnum.FORWARDS, 30, .75);
        telemetry.addData("FLOW", "Moving backwards");
        drive.moveInches(DirectionEnum.BACKWARDS, 30, .75);

        //drive.rotateDegrees(DirectionEnum.LEFT, 90, 1);
        telemetry.addData("FLOW", "Rotating right");
        drive.rotateDegrees(DirectionEnum.RIGHT, 90, .75);

        //drive.moveInches(DirectionEnum.FORWARDS, 30, 1);
        telemetry.addData("FLOW", "Finished move");
    }
}
