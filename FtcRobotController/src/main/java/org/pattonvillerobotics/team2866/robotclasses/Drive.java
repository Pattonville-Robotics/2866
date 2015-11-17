package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.hardware.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Nathan Skelton on 10/15/15.
 * Last edited by Mitchell Skaggs on 11/14/15
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
    public MRGyroHelper gyro;
    private HardwareMap hardwareMap;
    private LinearOpMode linearOpMode;

    public Drive(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;

        this.motorLeft = this.hardwareMap.dcMotor.get(Config.MOTOR_DRIVE_LEFT);
        this.motorRight = this.hardwareMap.dcMotor.get(Config.MOTOR_DRIVE_RIGHT);

        this.gyro = new MRGyroHelper((ModernRoboticsI2cGyro) this.hardwareMap.gyroSensor.get(Config.SENSOR_GYRO), this.linearOpMode);

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.FORWARD);

        this.linearOpMode.telemetry.addData(TAG, "Drive class instantiated " + numInstantiations + " times so far!");
        //noinspection AssignmentToStaticFieldFromInstanceMethod
        numInstantiations++;
    }

    public static double inchesToTicks(double inches) {
        return inches / INCHES_PER_TICK;
    }

    public static double degreesToTicks(double degrees) {
        return inchesToTicks(degrees * INCHES_PER_DEGREE);
    }

    public void sleep(long milliseconds) {
        try {
            this.linearOpMode.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitOneFullHardwareCycle() {
        try {
            this.linearOpMode.waitOneFullHardwareCycle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void stopDriveMotors() {
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    @Deprecated
    public void stop() {
        this.stopDriveMotors();
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
            e.printStackTrace();
        }
    }

    public void rotateDegreesGyro(DirectionEnum direction, int degrees, double power) {
        this.waitForNextHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        this.waitForNextHardwareCycle();

        int target = gyro.getIntegratedZValue();

        switch (direction) {
            case LEFT: {
                motorLeft.setPower(-power);
                motorRight.setPower(power);

                target += degrees;
                break;
            }
            case RIGHT: {
                motorLeft.setPower(power);
                motorRight.setPower(-power);

                target -= degrees;
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

    @Deprecated
    public void rotateDegrees(DirectionEnum direction, int degrees, double power) {
        this.rotateDegreesGyro(direction, degrees, power);
        /*
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
         */
    }
}
