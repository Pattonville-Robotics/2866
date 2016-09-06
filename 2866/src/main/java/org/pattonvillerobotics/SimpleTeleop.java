package org.pattonvillerobotics;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.pattonvillerobotics.opmodes.AutoRegisteredOpMode;

/**
 * Created by skaggsm on 9/6/16.
 */
@AutoRegisteredOpMode("Simple Teleop")
public class SimpleTeleop extends LinearOpMode {
    private static final String TAG = "SimpleTeleop";

    @Override
    public void runOpMode() throws InterruptedException {
        Log.e(TAG, "Starting");
        DcMotor motorLeft = hardwareMap.dcMotor.get("motor_left");
        DcMotor motorRight = hardwareMap.dcMotor.get("motor_right");
        motorLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        this.waitForStart();

        Log.e(TAG, "Starting loop");
        while (this.opModeIsActive()) {
            //Log.e(TAG,"Looping");
            motorLeft.setPower(gamepad1.left_stick_y);
            motorRight.setPower(gamepad1.right_stick_y);
        }
        Log.e(TAG, "Done");
    }
}
