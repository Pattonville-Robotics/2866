package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.robotclasses.Drive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by skaggsm on 10/15/15.
 */
public class TestAutonomous extends LinearOpMode {

	public static final String TAG = "TestAutonomous";

	@Override
	public void runOpMode() throws InterruptedException {

		Drive drive = new Drive(hardwareMap);

		waitForStart();

		//telemetry.addData(TAG, "Position start: " + right.getCurrentPosition());
		//telemetry.addData(TAG, "Position start: " + left.getCurrentPosition());

		drive.moveStraight(1);
		this.sleep(1000);
		drive.stop();
		drive.rotateRight(1);
		this.sleep(500);
		drive.moveStraight(1);
		this.sleep(1000);
		drive.stop();
		//telemetry.addData(TAG, "Position end: " + right.getCurrentPosition());
		//telemetry.addData(TAG, "Position end: " + left.getCurrentPosition());
	}
}
