package org.pattonvillerobotics.team2866.robotclasses.controllables;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;

/**
 * Created by Nathan Skelton on 10/15/15.
 * Last edited by Mitchell Skaggs on 11/14/15
 * Lol this is never updated
 * <p/>
 */
public class Drive {

    @SuppressWarnings("MagicNumber")
    public static final double WHEEL_DIAMETER = 220 / 100d; // New tread adjustment
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER;
    public static final double TICKS_PER_REVOLUTION = 1440;
    public static final double INCHES_PER_TICK = WHEEL_CIRCUMFERENCE / TICKS_PER_REVOLUTION;
    public static final double WHEEL_BASE_DIAMETER = 18.3;
    public static final double WHEEL_BASE_CIRCUMFERENCE = Math.PI * WHEEL_BASE_DIAMETER;
    public static final int DEGREES_PER_REVOLUTION = 360;
    public static final double INCHES_PER_DEGREE = WHEEL_BASE_CIRCUMFERENCE / DEGREES_PER_REVOLUTION;
    public static final double POWER_SCALE = .005;
    public static final int ERROR_SMOOTHING_FACTOR = 300; // What encoder value reaches 50% power. Normal behavior at 0
    private static final double GYRO_POWER_SCALE = 0.05;    // 2 percent power per degree of error
    private static final double GYRO_I = 0.00001;    // Integral factor
    private static final double GYRO_D = 0.0;    // Derivative factor
    private static final String TAG = "Drive";
    private static int numInstantiations = 0;
    public final DcMotor motorLeft, motorRight;
    public final MRGyroHelper gyro;
    public final DoubleGyroHelper doubleGyroHelper;
    public final LinearOpMode linearOpMode;

    public Drive(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;

        this.motorLeft = hardwareMap.dcMotor.get(Config.MOTOR_DRIVE_LEFT);
        this.motorRight = hardwareMap.dcMotor.get(Config.MOTOR_DRIVE_RIGHT);

        this.gyro = null;//new MRGyroHelper((ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get(Config.SENSOR_GYRO), this.linearOpMode);
        this.doubleGyroHelper = new DoubleGyroHelper(hardwareMap);

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
        this.moveInches(direction, inches, power, SensorMode.NONE);
    }

    public void moveInches(Direction direction, double inches, double power, SensorMode sensorMode) {

        if (power > 1 || power <= 0)
            throw new IllegalArgumentException("Power must be positive!");
        if (inches <= 0)
            throw new IllegalArgumentException("Distance must be positive!");

        int targetPositionLeft;
        int targetPositionRight;

        int startPositionLeft = motorLeft.getCurrentPosition();
        int startPositionRight = motorRight.getCurrentPosition();

        Log.d(TAG, "Straight Move... Left motor start: " + startPositionLeft + " Right motor start: " + startPositionRight);

        this.waitOneFullHardwareCycle();

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

        this.waitOneFullHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        this.waitOneFullHardwareCycle();

        motorLeft.setTargetPosition(targetPositionLeft);
        motorRight.setTargetPosition(targetPositionRight);

        this.waitOneFullHardwareCycle();

        motorLeft.setPower(leftPowerAdjust(power));
        motorRight.setPower(rightPowerAdjust(power));

        this.waitOneFullHardwareCycle();

		/*
        DescriptiveStatistics statisticalSummary = new DescriptiveStatistics();
		final boolean useGyro = false;
		int target_angle = gyro.getIntegratedZValue();
		int angleDelta;
		double powerAdjustment;
		int previous_error = 0;
		int totalError = 0;
		*/

		/*
		try {
			gyro.calibrateAndWait();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		*/

        int currentTargetDistance = Integer.MAX_VALUE;
        double currentError = 0;

        Log.d(TAG, "Started encoder move...");
        while (this.linearOpMode.opModeIsActive() && currentTargetDistance > Config.ENCODER_MOVEMENT_TOLERANCE) {
            this.waitOneFullHardwareCycle();

            double leftPower = targetScale(leftPowerAdjust(power), currentTargetDistance) + currentError;
            double rightPower = targetScale(rightPowerAdjust(power), currentTargetDistance) - currentError;

            motorLeft.setPower(Range.clip(leftPower, 0, 1));
            motorRight.setPower(Range.clip(rightPower, 0, 1));

            Log.d(TAG, "Current distance remaining: \t" + (motorLeft.getCurrentPosition() - targetPositionLeft));
            Log.d(TAG, "Current encoder error: \t" + (Math.abs(motorLeft.getCurrentPosition() - startPositionLeft) - Math.abs(motorRight.getCurrentPosition() - startPositionRight)));
            Log.d(TAG, "Current gyro heading: \t" + doubleGyroHelper.getHeading());
            Log.d(TAG, "Current gyro Z value: \t" + doubleGyroHelper.getIntegratedZValue());

            //currentTargetDistance = Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft);

            switch (sensorMode) {
                case ENCODERS:
                    break;
                case GYROS:
                    break;
                case BOTH:
                    break;
                case NONE:
                    currentError = 0;
                    currentTargetDistance = Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft);
                    break;
            }

			/*
			//noinspection ConstantConditions
			if (useGyro) {
				angleDelta = gyro.getIntegratedZValue() - target_angle;

				totalError += angleDelta;

				double pError = angleDelta * GYRO_POWER_SCALE;
				double iError = totalError * GYRO_I;
				double dError = (angleDelta - previous_error) * GYRO_D;

				powerAdjustment = pError + iError + dError;

				if (angleDelta > 10) {
					angleDelta = 10;
				}
				if (angleDelta < -10) {
					angleDelta = -10;
				}

				motorLeft.setPower(Range.clip(power - powerAdjustment, 0, 1));
				motorRight.setPower(Range.clip(power + powerAdjustment, 0, 1));

				previous_error = angleDelta;

				Log.d(TAG, "Int. Z Value: " + gyro.getIntegratedZValue() + " Total Error: " + totalError + " Derivative Error: " + (angleDelta - previous_error));
			} else {
				//int distanceLeft = targetPositionLeft - motorLeft.getCurrentPosition();
				//int distanceRight = targetPositionRight - motorRight.getCurrentPosition();
				//double error = (distanceLeft - distanceRight) * POWER_SCALE;

				//motorLeft.setPower(Range.clip(leftPowerAdjust(power), 0, 1));
				//motorRight.setPower(Range.clip(rightPowerAdjust(power), 0, 1));

				//Log.e(TAG, "Left: " + distanceLeft + " Right: " + distanceRight + " Error: " + error);
				//statisticalSummary.addValue(error);
			}
			*/
        }
        Log.d(TAG, "Finished encoder move...");

        this.waitOneFullHardwareCycle();

        this.stopDriveMotors();
    }

    public void waitOneFullHardwareCycle() {
        try {
            this.linearOpMode.waitOneFullHardwareCycle();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
	}

    public static double inchesToTicks(double inches) {
        return inches / INCHES_PER_TICK;
    }

    public double leftPowerAdjust(double power) {
        return Range.clip(power * 1, 0, 1);
    }

    public double rightPowerAdjust(double power) {
        return Range.clip(power * 1, 0, 1);
    }

    public static double targetScale(double power, int currentError) {
        return power * (currentError / (double) (currentError + ERROR_SMOOTHING_FACTOR));
    }

    public void stopDriveMotors() {
        //this.waitOneFullHardwareCycle();
        //motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        //motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        this.waitOneFullHardwareCycle();
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void waitForNextHardwareCycle() {
        try {
            this.linearOpMode.waitForNextHardwareCycle();
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

        Log.d(TAG, "Rotation... Left motor start: " + startPositionLeft + " Right motor start: " + startPositionRight);

        int deltaPositionLeft;
        int deltaPositionRight;

        switch (direction) {
            case LEFT: {
                deltaPositionLeft = (int) Math.round(degreesToTicks(-degrees));
                deltaPositionRight = (int) Math.round(degreesToTicks(degrees));

                targetPositionLeft = startPositionLeft + deltaPositionLeft;
                targetPositionRight = startPositionRight + deltaPositionRight;

                powerLeft = power;
                powerRight = power;
                break;
            }
            case RIGHT: {
                deltaPositionLeft = (int) Math.round(degreesToTicks(degrees));
                deltaPositionRight = (int) Math.round(degreesToTicks(-degrees));

                targetPositionLeft = startPositionLeft + deltaPositionLeft;
                targetPositionRight = startPositionRight + deltaPositionRight;

                powerLeft = power;
                powerRight = power;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");
        }

        Log.d(TAG, "Rotation target left: " + targetPositionLeft + " Rotation target right: " + targetPositionRight);

        this.waitOneFullHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        this.waitOneFullHardwareCycle();

        motorLeft.setTargetPosition(targetPositionLeft);
        motorRight.setTargetPosition(targetPositionRight);

        this.waitOneFullHardwareCycle();

        motorLeft.setPower(leftPowerAdjust(powerLeft));
        motorRight.setPower(rightPowerAdjust(powerRight));

        this.waitOneFullHardwareCycle();

        Log.d(TAG, "Started encoder rotate...");
        int currentError = Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft);//(direction == Direction.LEFT) ? Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft) : Math.abs(motorRight.getCurrentPosition() - targetPositionRight);

        while (currentError > Config.ENCODER_MOVEMENT_TOLERANCE) {
            this.waitOneFullHardwareCycle();
            Log.d(TAG, "Current Error: " + currentError);

            motorLeft.setPower(targetScale(leftPowerAdjust(powerLeft), currentError));
            motorRight.setPower(targetScale(rightPowerAdjust(powerRight), currentError));

            currentError = Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft);//direction == Direction.LEFT ? Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft) : Math.abs(motorRight.getCurrentPosition() - targetPositionRight);

            Log.d(TAG, "Current encoder error: \t" + (Math.abs(motorLeft.getCurrentPosition() - startPositionLeft) - Math.abs(motorRight.getCurrentPosition() - startPositionRight)));
            Log.d(TAG, "Current gyro heading: \t" + doubleGyroHelper.getHeading());
            Log.d(TAG, "Current gyro Z value: \t" + doubleGyroHelper.getIntegratedZValue());
        }
        Log.d(TAG, "Finished encoder rotate...");

        waitOneFullHardwareCycle();

        this.stopDriveMotors();
    }

    public static double degreesToTicks(double degrees) {
        return inchesToTicks(degrees * INCHES_PER_DEGREE);
    }

    public void rotateDegreesGyro(Direction direction, double degrees, double power) {

        this.waitOneFullHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        this.waitOneFullHardwareCycle();

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

        this.waitOneFullHardwareCycle();

        motorLeft.setPower(leftPowerAdjust(motorLeftPower));
        motorRight.setPower(rightPowerAdjust(motorRightPower));

        double currentError = Math.abs(gyro.getIntegratedZValue() - target);

        while (this.linearOpMode.opModeIsActive() && currentError > Config.GYRO_TURN_TOLERANCE) {
            currentError = Math.abs(gyro.getIntegratedZValue() - target);

            motorLeft.setPower(leftPowerAdjust(motorLeftPower * errorFunction(currentError)));
            motorRight.setPower(rightPowerAdjust(motorRightPower * errorFunction(currentError)));

            Log.e(TAG, "Current degree drift: " + gyro.getCurrentDrift());
            this.waitOneFullHardwareCycle();
        }
        Log.e(TAG, "Last degree error readout: " + currentError);

        this.stopDriveMotors();

        this.waitOneFullHardwareCycle();
    }

    private double errorFunction(double error) {
        return 2 * Math.atan(error / 5) / Math.PI;
    }

    private void rotateDegreesPID(Direction direction, int degrees, final double power) {
        if (direction != Direction.LEFT && direction != Direction.RIGHT)
            throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");

        this.waitOneFullHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        this.waitOneFullHardwareCycle();

        int target = gyro.getIntegratedZValue() + (direction == Direction.LEFT ? degrees : -degrees);
        double proportionalPower = power;

        while (this.linearOpMode.opModeIsActive() && Math.abs(target - gyro.getIntegratedZValue()) > Config.GYRO_TURN_TOLERANCE) {
            //this.linearOpMode.telemetry.addData(TAG, "Current degree readout: " + gyro.getIntegratedZValue());
            this.waitOneFullHardwareCycle();

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

        this.waitOneFullHardwareCycle();
    }

    public void moveFreely(double left, double right) {
        motorLeft.setPower(left);
        motorRight.setPower(right);
    }

    enum SensorMode {
        ENCODERS, GYROS, BOTH, NONE
    }
}
