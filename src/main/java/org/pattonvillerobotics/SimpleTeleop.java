package org.pattonvillerobotics;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

/**
 * Created by skaggsm on 9/6/16.
 */
@TeleOp(name = "Simple Teleop", group = OpModeGroups.TESTING)
public class SimpleTeleop extends LinearOpMode {
    private static final String TAG = "SimpleTeleop";

    @Override
    public void runOpMode() throws InterruptedException {
        Log.e(TAG, "Starting");
        SimpleDrive drive = new SimpleDrive(this, hardwareMap);
        ListenableGamepad listenableGamepad1 = new ListenableGamepad();

        this.waitForStart();

        Log.e(TAG, "Starting loop");
        while (this.opModeIsActive()) {
            listenableGamepad1.update(new GamepadData(gamepad1));
            drive.moveFreely(gamepad1.left_stick_y, gamepad1.right_stick_y);
        }
        Log.e(TAG, "Done");
    }
}













