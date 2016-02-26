package org.pattonvillerobotics.team2866.opmodes.testing;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;

import java.util.Locale;

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

        final boolean distanceMode = false;
        //noinspection ConstantConditions
        if (distanceMode) {
            Log.i(TAG, "Moving 100 inches...");
            drive.moveInches(Direction.FORWARDS, 100, .75);
            Log.i(TAG, "Finished moving.");
        } else {
            Log.i(TAG, "Rotating 4 times (1440 deg)...");
            drive.rotateDegrees(Direction.RIGHT, 1440, .75);
            Log.i(TAG, "Finished rotation.");
        }
    }
}
