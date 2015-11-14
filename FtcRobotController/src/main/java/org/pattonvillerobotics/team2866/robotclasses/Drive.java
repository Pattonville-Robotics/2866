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
    public static final double WHEEL_BASE_RADIUS = 8.5;
    public static final double WHEEL_BASE_CIRCUMFERENCE = 2 * Math.PI * WHEEL_BASE_RADIUS;
    public static final int DEGREES_PER_REVOLUTION = 360; // Why lol
    public static final double INCHES_PER_DEGREE = WHEEL_BASE_CIRCUMFERENCE / DEGREES_PER_REVOLUTION;
    private static final String TAG = Drive.class.getSimpleName();
    private static int numInstantiations = 0;
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
        motorRight.setDirection(DcMotor.Direction.FORWARD);

        this.linearOpMode.telemetry.addData(TAG, "Drive class instantiated " + numInstantiations + " times so far!");
        numInstantiations++;
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

        int targetPositionLeft;
        int targetPositionRight;

        switch (direction) {
            case FORWARDS: {
                int startPositionLeft = motorLeft.getCurrentPosition();
                int startPositionRight = motorRight.getCurrentPosition();
                int deltaPosition = (int) Math.round(inchesToTicks(inches));
                targetPositionLeft = startPositionLeft + deltaPosition;
                targetPositionRight = startPositionRight + deltaPosition;
                break;
            }
            case BACKWARDS: {
                int startPositionLeft = motorLeft.getCurrentPosition();
                int startPositionRight = motorRight.getCurrentPosition();
                int deltaPosition = (int) Math.round(inchesToTicks(inches));
                targetPositionLeft = startPositionLeft - deltaPosition;
                targetPositionRight = startPositionRight - deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be FORWARDS or BACKWARDS!");
        }

        try {
            this.linearOpMode.waitOneFullHardwareCycle();
            motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
            this.linearOpMode.waitOneFullHardwareCycle();
            motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

            this.linearOpMode.waitOneFullHardwareCycle();
            motorLeft.setTargetPosition(targetPositionLeft);
            this.linearOpMode.waitOneFullHardwareCycle();
            motorRight.setTargetPosition(targetPositionRight);

            this.linearOpMode.waitOneFullHardwareCycle();
            motorLeft.setPower(power);
            this.linearOpMode.waitOneFullHardwareCycle();
            motorRight.setPower(power);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        linearOpMode.telemetry.addData(TAG, "Started encoder move...");
        while (Math.abs(motorRight.getCurrentPosition() - targetPositionRight) > Config.ENCODER_MOVEMENT_TOLERANCE && Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft) > Config.ENCODER_MOVEMENT_TOLERANCE) {
            try {
                //noinspection ConstantConditions
                if (Config.ENCODER_MOVEMENT_UPDATE_DELAY < 1)
                    this.linearOpMode.waitForNextHardwareCycle();
                else
                    this.linearOpMode.sleep(Config.ENCODER_MOVEMENT_UPDATE_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        linearOpMode.telemetry.addData(TAG, "Finished encoder move...");
    }

    public void rotateDegrees(DirectionEnum direction, double degrees, double power) {
        if (degrees <= 0)
            throw new IllegalArgumentException("Degrees must be positive!");
        if (power <= 0)
            throw new IllegalArgumentException("Power must be positive!");

        int targetPositionLeft;
        int targetPositionRight;

        switch (direction) {
            case LEFT: {
                int startPositionLeft = motorLeft.getCurrentPosition();
                int startPositionRight = motorLeft.getCurrentPosition();

                int deltaPositionLeft = (int) Math.round(degreesToTicks(degrees));
                int deltaPositionRight = (int) Math.round(degreesToTicks(degrees));

                targetPositionLeft = startPositionLeft - deltaPositionLeft;
                targetPositionRight = startPositionRight + deltaPositionRight;
                break;
            }
            case RIGHT: {
                int startPositionLeft = motorLeft.getCurrentPosition();
                int startPositionRight = motorLeft.getCurrentPosition();

                int deltaPositionLeft = (int) Math.round(degreesToTicks(degrees));
                int deltaPositionRight = (int) Math.round(degreesToTicks(degrees));

                targetPositionLeft = startPositionLeft + deltaPositionLeft;
                targetPositionRight = startPositionRight - deltaPositionRight;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");
        }

        try {
            this.linearOpMode.waitOneFullHardwareCycle();
            motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
            this.linearOpMode.waitOneFullHardwareCycle();
            motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

            this.linearOpMode.waitOneFullHardwareCycle();
            motorLeft.setTargetPosition(targetPositionLeft);
            this.linearOpMode.waitOneFullHardwareCycle();
            motorRight.setTargetPosition(targetPositionRight);

            this.linearOpMode.waitOneFullHardwareCycle();
            motorLeft.setPower(power);
            this.linearOpMode.waitOneFullHardwareCycle();
            motorRight.setPower(power);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        linearOpMode.telemetry.addData(TAG, "Started encoder rotate...");
        while (Math.abs(motorRight.getCurrentPosition() - targetPositionRight) > Config.ENCODER_MOVEMENT_TOLERANCE && Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft) > Config.ENCODER_MOVEMENT_TOLERANCE) {
            try {
                //noinspection ConstantConditions
                if (Config.ENCODER_MOVEMENT_UPDATE_DELAY < 1)
                    this.linearOpMode.waitForNextHardwareCycle();
                else
                    this.linearOpMode.sleep(Config.ENCODER_MOVEMENT_UPDATE_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        linearOpMode.telemetry.addData(TAG, "Finished encoder rotate...");
    }
}
