package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Nathan Skelton on 10/15/15.
 * <p/>
 * TODO: Determine the calculations for inchesToTicks(double inches)
 * TODO: Create method to move a measured distance in inches
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
        return 9001.6969;
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

    public void moveInches(DirectionEnum direction, double inches, double power) {
        if (Math.signum(motorLeft.getCurrentPosition()) != Math.signum(motorRight.getCurrentPosition()))
            throw new AssertionError("robt is kill");
        if (power > 1 || power < 0)
            throw new IllegalArgumentException("Invalid power value");

        int targetPosition;

        switch (direction) {
            case FORWARDS: {
                int startPosition = motorLeft.getCurrentPosition();
                int deltaPosition = (int) Math.round(inchesToTicks(inches));
                targetPosition = startPosition + deltaPosition;
                break;
            }
            case BACKWARDS: {
                int startPosition = motorLeft.getCurrentPosition();
                int deltaPosition = (int) Math.round(inchesToTicks(inches));
                targetPosition = startPosition + deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be FORWARDS or BACKWARDS");
        }

        motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorLeft.setTargetPosition(targetPosition);
        motorRight.setTargetPosition(targetPosition);

        motorLeft.setPower(power);
        motorRight.setPower(power);
    }

    public void rotateDegrees(DirectionEnum direction, double degrees) {
        switch (direction) {
            case LEFT:
                break;
            case RIGHT:
                break;
            default:
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT");
        }
    }

    @Override
    public String toString() {

        return "Drive Left Motor: " + motorLeft.getPower() + "\n" + "Drive Right Power: " + motorRight.getPower();
    }
}
