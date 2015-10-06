package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by skaggsm on 10/6/15.
 */
public class TestOp extends LinearOpMode {

	public static final String LEFT_MOTOR = "left_motor";
	public static final String RIGHT_MOTOR = "right_motor";

	@Override
	public void runOpMode() throws InterruptedException {
		DcMotor motorLeft = hardwareMap.dcMotor.get(LEFT_MOTOR);
		DcMotor motorRight = hardwareMap.dcMotor.get(RIGHT_MOTOR);

		motorLeft.setDirection(DcMotor.Direction.REVERSE);

		gamepad1.setJoystickDeadzone(0.05f);
		gamepad2.setJoystickDeadzone(0.05f);

		waitForStart();

		while (opModeIsActive()) {
			float throttle = -gamepad1.left_stick_y;
			float direction = gamepad1.left_stick_x;
			float right = throttle - direction;
			float left = throttle + direction;

			// clip the right/left values so that the values never exceed +/- 1
			right = Range.clip(right, -1, 1);
			left = Range.clip(left, -1, 1);

			// write the values to the motors
			motorRight.setPower(right);
			motorLeft.setPower(left);

			telemetry.addData("2866", "K9TeleOp");
			telemetry.addData("2866", motorLeft.getPower());
			telemetry.addData("2866", motorRight.getPower());

			waitOneFullHardwareCycle();
		}
	}
}
