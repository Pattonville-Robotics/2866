package org.pattonvillerobotics;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by skaggsm on 9/6/16.
 */
@TeleOp(name = "Simple Teleop", group = "Generic OpM=odes")
public class SimpleTeleop extends LinearOpMode {
    private static final String TAG = "SimpleTeleop";

    @Override
    public void runOpMode() throws InterruptedException {
        Log.e(TAG, "Starting");
        DcMotor motorLeft = hardwareMap.dcMotor.get("motor_left");
        DcMotor motorRight = hardwareMap.dcMotor.get("motor_right");
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
