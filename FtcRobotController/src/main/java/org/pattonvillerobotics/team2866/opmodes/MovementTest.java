package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;

/**
 * Created by skaggsm on 11/19/15.
 */
@OpMode("Movement Test")
public class MovementTest extends LinearOpMode {

    public static final String TAG = MovementTest.class.getSimpleName();

    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(this.hardwareMap, this);
        waitForStart();

        telemetry.addData(TAG, "Moving 100 inches...");
        drive.moveInches(Direction.FORWARDS, 100, 1);
        telemetry.addData(TAG, "Finished moving.");
    }
}
