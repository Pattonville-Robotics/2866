package com.qualcomm.ftcrobotcontroller.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Nathan Skelton on 10/15/15.
 */
public class Drive {

	private HardwareMap hardwareMap;
	private DcMotor motorLeft;
	private DcMotor motorRight;

	public Drive(HardwareMap hardwareMap) {

		this.hardwareMap = hardwareMap;
		this.motorLeft = hardwareMap.dcMotor.get("motor_drive_left");
		this.motorRight = hardwareMap.dcMotor.get("motor_drive_right");
	};

	public void moveFreely(double left, double right) {

		motorLeft.setPower(left);
		motorRight.setPower(right);
	}

	public void moveStraight(double power){

		motorRight.setPower(-power);
		motorLeft.setPower(power);

	}

	public void moveLeft(double power) {

		motorRight.setPower(power);
		motorLeft.setPower(power);
	}

	public void moveRight(double power) {

		motorRight.setPower(-power);
		motorLeft.setPower(-power);
	}

	public void stop() {

		motorLeft.setPower(0);
		motorRight.setPower(0);
	}

    @Override
    public String toString() {

        return "Left Motor: " + motorLeft.getPower() + "\n" + "Right Power: " + motorRight.getPower();
    }
}
