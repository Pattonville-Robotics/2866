package org.pattonvillerobotics.team2866.robotclasses.controllables;

import android.util.Log;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;

/**
 * Created by Nathan Skelton on 10/15/15.
 * Last edited by Mitchell Skaggs on 11/14/15
 * <p/>
 */
public class Drive {

    @SuppressWarnings("MagicNumber")
    public static final double WHEEL_DIAMETER = 220 / 100d; // New tread adjustment
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public static final double TICKS_PER_REVOLUTION = 1440;
    public static final double INCHES_PER_TICK = WHEEL_CIRCUMFERENCE / TICKS_PER_REVOLUTION;
    public static final double WHEEL_BASE_DIAMETER = 21;
    public static final double WHEEL_BASE_CIRCUMFERENCE = Math.PI * WHEEL_BASE_DIAMETER;
    public static final int DEGREES_PER_REVOLUTION = 360;
    public static final double INCHES_PER_DEGREE = WHEEL_BASE_CIRCUMFERENCE / DEGREES_PER_REVOLUTION;
    public static final double POWER_SCALE = .002;
    private static final String TAG = "Drive";
    private static int numInstantiations = 0;
    public final DcMotor motorLeft;
    public final DcMotor motorRight;
    public final MRGyroHelper gyro;
    private final LinearOpMode linearOpMode;

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
            throw new RuntimeException(e);
        }
    }

    public void moveInches(Direction direction, double inches, double power) {

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

        motorLeft.setPower(leftPowerAdjust(power));
        motorRight.setPower(rightPowerAdjust(power));

        this.waitForNextHardwareCycle();

        Log.e(TAG, "Started encoder move...");
        while (this.linearOpMode.opModeIsActive() &&
                Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft) > Config.ENCODER_MOVEMENT_TOLERANCE) {

            this.waitForNextHardwareCycle();
        }
        Log.e(TAG, "Finished encoder move...");

        this.waitForNextHardwareCycle();

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

    private double leftPowerAdjust(double power) {
        return power * 1.225;
    }

    private double rightPowerAdjust(double power) {
        return power * .775;
    }

    public void stopDriveMotors() {
        this.waitForNextHardwareCycle();
        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        this.waitForNextHardwareCycle();
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void waitOneFullHardwareCycle() {
        try {
            this.linearOpMode.waitOneFullHardwareCycle();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void rotateDegrees(Direction direction, double degrees, double power) {
        this.rotateDegreesEncoder(direction, degrees, power);
        //this.rotateDegreesGyro(direction, degrees, power);
    }

    public void rotateDegreesEncoder(Direction direction, double degrees, double power) {
        if (degrees <= 0)
            throw new IllegalArgumentException("Degrees must be positive!");
        if (power <= 0)
            throw new IllegalArgumentException("Power must be positive!");

        int targetPositionLeft;
        int targetPositionRight;

        double powerLeft;
        double powerRight;

        int startPositionLeft = motorLeft.getCurrentPosition();
        int startPositionRight = motorRight.getCurrentPosition();

        switch (direction) {
            case LEFT: {
                int deltaPositionLeft = (int) Math.round(degreesToTicks(-degrees));
                int deltaPositionRight = (int) Math.round(degreesToTicks(degrees));

                targetPositionLeft = startPositionLeft + deltaPositionLeft;
                targetPositionRight = startPositionRight + deltaPositionRight;

                powerLeft = power;
                powerRight = power;
                break;
            }
            case RIGHT: {
                int deltaPositionLeft = (int) Math.round(degreesToTicks(degrees));
                int deltaPositionRight = (int) Math.round(degreesToTicks(-degrees));

                targetPositionLeft = startPositionLeft + deltaPositionLeft;
                targetPositionRight = startPositionRight + deltaPositionRight;

                powerLeft = power;
                powerRight = power;
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

        motorLeft.setPower(leftPowerAdjust(powerLeft));
        motorRight.setPower(rightPowerAdjust(powerRight));

        this.waitForNextHardwareCycle();

        Log.e(TAG, "Started encoder rotate...");
        int currentError = direction == Direction.LEFT ? Math.abs(motorRight.getCurrentPosition() - targetPositionRight) : Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft);//(Math.abs(motorRight.getCurrentPosition() - targetPositionRight) + Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft)) / 2;
        while (currentError > Config.ENCODER_MOVEMENT_TOLERANCE) {
            this.waitForNextHardwareCycle();
            Log.e("Encoder", "Current Error: " + currentError);
            currentError = direction == Direction.LEFT ? Math.abs(motorRight.getCurrentPosition() - targetPositionRight) : Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft);//(Math.abs(motorRight.getCurrentPosition() - targetPositionRight) + Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft)) / 2;
        }
        Log.e(TAG, "Finished encoder rotate...");

        waitForNextHardwareCycle();

        this.stopDriveMotors();
    }

    public static double degreesToTicks(double degrees) {
        return inchesToTicks(degrees * INCHES_PER_DEGREE);
    }

    public void rotateDegreesGyro(Direction direction, double degrees, double power) {

        this.waitForNextHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        this.waitForNextHardwareCycle();

        int target = gyro.getIntegratedZValue();

        double motorLeftPower, motorRightPower;
        switch (direction) {
            case LEFT: {
                motorLeftPower = -power;
                motorRightPower = power;

                target += degrees;
                break;
            }
            case RIGHT: {
                motorLeftPower = power;
                motorRightPower = -power;

                target -= degrees;
                break;
            }
            default: {
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");
            }
        }

        this.waitForNextHardwareCycle();

        motorLeft.setPower(leftPowerAdjust(motorLeftPower));
        motorRight.setPower(rightPowerAdjust(motorRightPower));

        double currentError = Math.abs(gyro.getIntegratedZValue() - target);

        while (this.linearOpMode.opModeIsActive() && currentError > Config.GYRO_TURN_TOLERANCE) {
            currentError = Math.abs(gyro.getIntegratedZValue() - target);

            motorLeft.setPower(leftPowerAdjust(motorLeftPower * errorFunction(currentError)));
            motorRight.setPower(rightPowerAdjust(motorRightPower * errorFunction(currentError)));

            Log.e(TAG, "Current degree drift: " + gyro.getCurrentDrift());
            this.waitForNextHardwareCycle();
        }
        Log.e(TAG, "Last degree error readout: " + currentError);

        this.stopDriveMotors();

        this.waitForNextHardwareCycle();
    }

    private double errorFunction(double error) {
        return 2 * Math.atan(error / 5) / Math.PI;
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

    public void moveFreely(double left, double right) {
        motorLeft.setPower(left);
        motorRight.setPower(right);
    }
}
