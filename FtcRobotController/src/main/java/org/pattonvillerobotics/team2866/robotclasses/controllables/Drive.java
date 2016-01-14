package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.GamepadData;
import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

/**
 * Created by Nathan Skelton on 10/15/15.
 * Last edited by Mitchell Skaggs on 11/14/15
 * <p/>
 */
public class Drive implements Controllable {

    @SuppressWarnings("MagicNumber")
    public static final double WHEEL_RADIUS = 1.2906932 * (100 / 83d); // New tread adjustment
    public static final double WHEEL_CIRCUMFERENCE = 2 * Math.PI * WHEEL_RADIUS;
    public static final double TICKS_PER_REVOLUTION = 1440;
    public static final double INCHES_PER_TICK = WHEEL_CIRCUMFERENCE / TICKS_PER_REVOLUTION;
    public static final double WHEEL_BASE_RADIUS = 8.5;
    public static final double WHEEL_BASE_CIRCUMFERENCE = 2 * Math.PI * WHEEL_BASE_RADIUS;
    public static final int DEGREES_PER_REVOLUTION = 360;
    public static final double INCHES_PER_DEGREE = WHEEL_BASE_CIRCUMFERENCE / DEGREES_PER_REVOLUTION;
    public static final int DELTA_ANGLE = 0;
    private static final String TAG = Drive.class.getSimpleName();
    private static int numInstantiations = 0;
    public DcMotor motorLeft;
    public DcMotor motorRight;
    public MRGyroHelper gyro;
    private LinearOpMode linearOpMode;

    public Drive(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;

        this.motorLeft = hardwareMap.dcMotor.get(Config.MOTOR_DRIVE_LEFT);
        this.motorRight = hardwareMap.dcMotor.get(Config.MOTOR_DRIVE_RIGHT);

        this.gyro = new MRGyroHelper((ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get(Config.SENSOR_GYRO), this.linearOpMode);

        try {
            this.gyro.calibrateAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.FORWARD);

        this.linearOpMode.telemetry.addData(TAG, "Drive class instantiated " + numInstantiations + " times so far!");
        //noinspection AssignmentToStaticFieldFromInstanceMethod
        numInstantiations++;
    }

    public void sleep(long milliseconds) {
        try {
            this.linearOpMode.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void moveStraight(double power) {
        motorRight.setPower(power + DELTA_ANGLE);
        motorLeft.setPower(power - DELTA_ANGLE);
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

    public void moveInches(Direction direction, double inches, double power) {
        //if (Math.signum(motorLeft.getCurrentPosition()) != Math.signum(motorRight.getCurrentPosition()))
        //    throw new AssertionError("robit is kill");
        if (power > 1 || power < 0)
            throw new IllegalArgumentException("Power must be positive!");
        if (inches <= 0)
            throw new IllegalArgumentException("Distance must be positive!");

        int targetPositionLeft;
        int targetPositionRight;

        int startPositionLeft = motorLeft.getCurrentPosition();
        int startPositionRight = motorRight.getCurrentPosition();

        this.waitForNextHardwareCycle();

        switch (direction) {
            case FORWARDS: {

                int deltaPosition = (int) Math.round(inchesToTicks(inches));
                targetPositionLeft = startPositionLeft + deltaPosition;
                targetPositionRight = startPositionRight + deltaPosition;
                break;
            }
            case BACKWARDS: {
                int deltaPosition = (int) Math.round(inchesToTicks(inches));
                targetPositionLeft = startPositionLeft - deltaPosition;
                targetPositionRight = startPositionRight - deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be FORWARDS or BACKWARDS!");
        }

        this.waitForNextHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        this.waitForNextHardwareCycle();

        motorLeft.setTargetPosition(targetPositionLeft);
        motorRight.setTargetPosition(targetPositionRight);

        this.waitForNextHardwareCycle();

        motorLeft.setPower(power);
        motorRight.setPower(power);

        this.waitForNextHardwareCycle();

        this.linearOpMode.telemetry.addData(TAG, "Started encoder move...");
        while (this.linearOpMode.opModeIsActive() && Math.abs(motorRight.getCurrentPosition() - targetPositionRight) + Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft) > Config.ENCODER_MOVEMENT_TOLERANCE) {
            this.waitForNextHardwareCycle();
        }
        linearOpMode.telemetry.addData(TAG, "Finished encoder move...");

        this.stopDriveMotors();
    }

    public void waitForNextHardwareCycle() {
        try {
            this.linearOpMode.waitForNextHardwareCycle();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static double inchesToTicks(double inches) {
        return inches / INCHES_PER_TICK;
    }

    public void stopDriveMotors() {
        this.waitForNextHardwareCycle(); // So they can be applied simultaneously all the time
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    //TODO Design a better method. I think that this has more potential for accurate movement than the gyro, based on the accuracy of the straight-line movement
    @Deprecated
    private void rotateDegreesEncoder(Direction direction, int degrees, double power) {
        if (degrees <= 0)
            throw new IllegalArgumentException("Degrees must be positive!");
        if (power <= 0)
            throw new IllegalArgumentException("Power must be positive!");

        int targetPositionLeft;
        int targetPositionRight;

        int startPositionLeft = motorLeft.getCurrentPosition();
        int startPositionRight = motorLeft.getCurrentPosition();

        switch (direction) {
            case LEFT: {

                int deltaPositionLeft = (int) Math.round(degreesToTicks(degrees));
                int deltaPositionRight = (int) Math.round(degreesToTicks(degrees));

                targetPositionLeft = startPositionLeft - deltaPositionLeft;
                targetPositionRight = startPositionRight + deltaPositionRight;
                break;
            }
            case RIGHT: {

                int deltaPositionLeft = (int) Math.round(degreesToTicks(degrees));
                int deltaPositionRight = (int) Math.round(degreesToTicks(degrees));

                targetPositionLeft = startPositionLeft + deltaPositionLeft;
                targetPositionRight = startPositionRight - deltaPositionRight;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");
        }

        this.waitForNextHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        this.waitForNextHardwareCycle();

        motorLeft.setTargetPosition(targetPositionLeft);
        motorRight.setTargetPosition(targetPositionRight);

        this.waitForNextHardwareCycle();

        motorLeft.setPower(power);
        motorRight.setPower(power);

        this.waitForNextHardwareCycle();

        linearOpMode.telemetry.addData(TAG, "Started encoder rotate...");
        while (Math.abs(motorRight.getCurrentPosition() - targetPositionRight) + Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft) > Config.ENCODER_MOVEMENT_TOLERANCE) {
            this.waitForNextHardwareCycle();
        }
        linearOpMode.telemetry.addData(TAG, "Finished encoder rotate...");
    }

    public static double degreesToTicks(double degrees) {
        return inchesToTicks(degrees * INCHES_PER_DEGREE);
    }

    public void rotateDegrees(Direction direction, int degrees, double power) {
        this.rotateDegreesGyro(direction, degrees, power);
        //this.rotateDegreesPID(direction, degrees, power);
    }

    private void rotateDegreesGyro(Direction direction, int degrees, double power) {
        try {
            this.gyro.calibrateAndWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.waitForNextHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        this.waitForNextHardwareCycle();

        int target = gyro.getIntegratedZValue();

        switch (direction) {
            case LEFT: {
                motorLeft.setPower(-power);
                motorRight.setPower(power);

                target += degrees + Config.GYRO_TRIM;
                break;
            }
            case RIGHT: {
                motorLeft.setPower(power);
                motorRight.setPower(-power);

                target -= degrees - Config.GYRO_TRIM;
                break;
            }
            default: {
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");
            }
        }

        while (this.linearOpMode.opModeIsActive() && Math.abs(gyro.getIntegratedZValue() - target) > Config.GYRO_TURN_TOLERANCE) {
            //this.linearOpMode.telemetry.addData(TAG, "Current degree readout: " + gyro.getIntegratedZValue());
            this.waitForNextHardwareCycle();
        }

        this.stopDriveMotors();

        this.waitForNextHardwareCycle();
    }

    private void rotateDegreesPID(Direction direction, int degrees, final double power) {
        if (direction != Direction.LEFT && direction != Direction.RIGHT)
            throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");

        this.waitForNextHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        this.waitForNextHardwareCycle();

        int target = gyro.getIntegratedZValue() + (direction == Direction.LEFT ? degrees : -degrees);
        double proportionalPower = power;

        while (this.linearOpMode.opModeIsActive() && Math.abs(target - gyro.getIntegratedZValue()) > Config.GYRO_TURN_TOLERANCE) {
            //this.linearOpMode.telemetry.addData(TAG, "Current degree readout: " + gyro.getIntegratedZValue());
            this.waitForNextHardwareCycle();

            int error = target - gyro.getIntegratedZValue();

            double multiplier = Math.min(Math.sqrt(Math.abs(error)) / 10, 1);
            proportionalPower = power * multiplier;

            switch (direction) {
                case LEFT: {
                    motorLeft.setPower(-proportionalPower);
                    motorRight.setPower(proportionalPower);

                    //target += degrees;// + Config.GYRO_TRIM;
                    break;
                }
                case RIGHT: {
                    motorLeft.setPower(proportionalPower);
                    motorRight.setPower(-proportionalPower);

                    //target -= degrees;// - Config.GYRO_TRIM;
                    break;
                }
            }
        }

        this.stopDriveMotors();

        this.waitForNextHardwareCycle();
    }

    @Override
    public boolean sendGamepadData(GamepadData gamepad1DataCurrent, GamepadData gamepad1DataHistory, GamepadData gamepad2DataCurrent, GamepadData gamepad2DataHistory) {
        float right = -gamepad1DataCurrent.right_stick_y;
        float left = -gamepad1DataCurrent.left_stick_y;
        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);
        this.moveFreely(left, right);
        return true;
    }

    public void moveFreely(double left, double right) {
        motorLeft.setPower(left);
        motorRight.setPower(right);
    }

    @Override
    public GamepadFeature[] requestFeatures() {
        return new GamepadFeature[]{GamepadFeature.GAMEPAD_1_STICK_RIGHT_X, GamepadFeature.GAMEPAD_1_STICK_RIGHT_Y};
    }
}
