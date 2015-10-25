package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Nathan Skelton on 10/15/15.
 */
public class Drive {

	public static final double WHEEL_RADIUS = 10;

	private HardwareMap hardwareMap;
	private DcMotor motorLeft;
	private DcMotor motorRight;

    public Drive(HardwareMap hardwareMap) {

		this.hardwareMap = hardwareMap;
		this.motorLeft = hardwareMap.dcMotor.get("motor_drive_left");
		this.motorRight = hardwareMap.dcMotor.get("motor_drive_right");

	    motorRight.setDirection(DcMotor.Direction.REVERSE);
	}

	public static double inchesToTicks(double inches) {
		return 0;
	}

	public void moveFreely(double left, double right) {

		motorLeft.setPower(left);
		motorRight.setPower(right);
	}

	public void moveStraight(double power) {

		motorRight.setPower(power);
		motorLeft.setPower(power);
	}

	public void rotateLeft(double power) {

		motorRight.setPower(power);
		motorLeft.setPower(-power);
	}

	public void rotateRight(double power) {

		motorRight.setPower(-power);
		motorLeft.setPower(power);
	}

	public void stop() {

		motorLeft.setPower(0);
		motorRight.setPower(0);
	}

	@Override
	public String toString() {

		return "Drive Left Motor: " + motorLeft.getPower() + "\n" + "Drive Right Power: " + motorRight.getPower();
	}
}
