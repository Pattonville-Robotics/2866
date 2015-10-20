package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.robotclasses.Drive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Team 2866 on 10/6/15.
 */
public class OfficialTeleOp extends OpMode {

    public static final String TAG = "TestOp";

    private Drive drive;

    public void init() {

        drive = new Drive(hardwareMap);

        gamepad1.setJoystickDeadzone(0.05f);
        gamepad2.setJoystickDeadzone(0.05f);
    }

    public void loop() {

        float right = gamepad1.right_stick_y;
        float left = gamepad1.left_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        drive.moveFreely(left, right);

        telemetry.addData(TAG, "Teleop");
        telemetry.addData(TAG, drive);
    }
}
