package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.Direction;
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
        drive.moveInches(Direction.FORWARDS, 30, 1);
        wait(1000);

        telemetry.addData("FLOW", "Moving backwards");
        drive.moveInches(Direction.BACKWARDS, 30, 1);
        wait(1000);

        telemetry.addData("FLOW", "Rotating left 45");
        drive.rotateDegrees(Direction.LEFT, 45, 1);
        wait(1000);

        telemetry.addData("FLOW", "Rotating right 45");
        drive.rotateDegrees(Direction.RIGHT, 45, 1);
        wait(1000);

        telemetry.addData("FLOW", "Rotating left 90");
        drive.rotateDegrees(Direction.LEFT, 90, 1);
        wait(1000);

        telemetry.addData("FLOW", "Rotating right 90");
        drive.rotateDegrees(Direction.RIGHT, 90, 1);
        wait(1000);

        telemetry.addData("FLOW", "Finished move");
    }
}