package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by skaggsm on 10/6/15.
 */
public class TestOp extends LinearOpMode {

	public static final String MOTOR_LEFT = "left_motor";
	public static final String MOTOR_RIGHT = "right_motor";
	public static final String TAG = "TestOp";

	@Override
	public void runOpMode() throws InterruptedException {
		DcMotor motorLeft = hardwareMap.dcMotor.get(MOTOR_LEFT);
		DcMotor motorRight = hardwareMap.dcMotor.get(MOTOR_RIGHT);

		motorLeft.setDirection(DcMotor.Direction.REVERSE);

		gamepad1.setJoystickDeadzone(0.05f);
		gamepad2.setJoystickDeadzone(0.05f);

		waitForStart();

		while (opModeIsActive()) {
			//float throttle = -gamepad1.left_stick_y;
			//float direction = gamepad1.left_stick_x;
			float right = gamepad1.right_stick_y;
			float left = gamepad1.left_stick_y;

			// clip the right/left values so that the values never exceed +/- 1
			right = Range.clip(right, -1, 1);
			left = Range.clip(left, -1, 1);

			// write the values to the motors
			motorRight.setPower(right);
			motorLeft.setPower(left);

			telemetry.addData(TAG, "Teleop");
			telemetry.addData(TAG, motorLeft.getPower());
			telemetry.addData(TAG, motorRight.getPower());

			waitOneFullHardwareCycle();
		}
	}
}
