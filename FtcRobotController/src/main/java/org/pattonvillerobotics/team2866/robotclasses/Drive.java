package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Nathan Skelton on 10/15/15.
 * Last edited by Mitchell Skaggs on 11/3/15
 * <p/>
 * TODO Change rotate() to use the gyro sensor instead of encoders :MITCHELL:
 */
public class Drive {

    public static final double WHEEL_RADIUS = 1.5;
    public static final double WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS;
    public static final double TICKS_PER_REVOLUTION = 1440;
    public static final double INCHES_PER_TICK = WHEEL_CIRCUMFERENCE / TICKS_PER_REVOLUTION;

    public static final double WHEEL_BASE_RADIUS = 8;
    public static final double WHEEL_BASE_CIRCUMFERENCE = 2 * Math.PI * WHEEL_BASE_RADIUS;
    public static final int DEGREES_PER_REVOLUTION = 360; // Why lol
    public static final double INCHES_PER_DEGREE = WHEEL_BASE_CIRCUMFERENCE / DEGREES_PER_REVOLUTION;
    public DcMotor motorLeft;
    public DcMotor motorRight;
    private HardwareMap hardwareMap;
    private LinearOpMode linearOpMode;

    public Drive(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;

        this.motorLeft = this.hardwareMap.dcMotor.get(Config.MOTOR_DRIVE_LEFT);
        this.motorRight = this.hardwareMap.dcMotor.get(Config.MOTOR_DRIVE_RIGHT);

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    public static double inchesToTicks(double inches) {
        return inches / INCHES_PER_TICK;
    }

    public static double degreesToTicks(double degrees) {
        return inchesToTicks(degrees * INCHES_PER_DEGREE);
    }

    public void moveFreely(double left, double right) {

        motorLeft.setPower(left);
        motorRight.setPower(right);
    }

    @Deprecated
    public void moveStraight(double power) {
        motorRight.setPower(power);
        motorLeft.setPower(power);
    }

    @Deprecated
    public void rotateLeft(double power) {
        motorRight.setPower(power);
        motorLeft.setPower(-power);
    }

    @Deprecated
    public void rotateRight(double power) {
        motorRight.setPower(-power);
        motorLeft.setPower(power);
    }

    @Deprecated
    public void stop() {
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void moveInches(DirectionEnum direction, double inches, double power) {
        //if (Math.signum(motorLeft.getCurrentPosition()) != Math.signum(motorRight.getCurrentPosition()))
        //    throw new AssertionError("robit is kill");
        if (power > 1 || power < 0)
            throw new IllegalArgumentException("Power must be positive!");
        if (inches <= 0)
            throw new IllegalArgumentException("Distance must be positive!");

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
                targetPosition = startPosition - deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be FORWARDS or BACKWARDS!");
        }

        motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorLeft.setTargetPosition(targetPosition);
        motorRight.setTargetPosition(targetPosition);

        motorLeft.setPower(power);
        motorRight.setPower(power);

        linearOpMode.telemetry.addData(this.getClass().getSimpleName(), "Started encoder move...");
        while (Math.abs(motorRight.getCurrentPosition() - targetPosition) > 10 && Math.abs(motorLeft.getCurrentPosition() - targetPosition) > Config.ENCODER_MOVEMENT_TOLERANCE) {
            try {
                this.linearOpMode.sleep(Config.ENCODER_MOVEMENT_UPDATE_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        linearOpMode.telemetry.addData(this.getClass().getSimpleName(), "Finished encoder move...");
    }

    public void rotateDegrees(DirectionEnum direction, double degrees, double power) {
        if (degrees <= 0)
            throw new IllegalArgumentException("Degrees must be positive!");
        if (power <= 0)
            throw new IllegalArgumentException("Power must be positive!");

        int targetLeft;
        int targetRight;

        switch (direction) {
            case LEFT: {
                int startPositionLeft = motorLeft.getCurrentPosition();
                int deltaPositionLeft = (int) Math.round(degreesToTicks(degrees));
                targetLeft = startPositionLeft - deltaPositionLeft;

                int startPositionRight = motorLeft.getCurrentPosition();
                int deltaPositionRight = (int) Math.round(degreesToTicks(degrees));
                targetRight = startPositionRight + deltaPositionRight;
                break;
            }
            case RIGHT: {
                int startPositionLeft = motorLeft.getCurrentPosition();
                int deltaPositionLeft = (int) Math.round(degreesToTicks(degrees));
                targetLeft = startPositionLeft + deltaPositionLeft;

                int startPositionRight = motorLeft.getCurrentPosition();
                int deltaPositionRight = (int) Math.round(degreesToTicks(degrees));
                targetRight = startPositionRight - deltaPositionRight;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");
        }

        motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorLeft.setTargetPosition(targetLeft);
        motorRight.setTargetPosition(targetRight);

        motorLeft.setPower(power);
        motorRight.setPower(power);
    }

    @Override
    public String toString() {

        return "Drive Left Power: " + motorLeft.getPower() + ", Drive Right Power: " + motorRight.getPower();
    }
}
