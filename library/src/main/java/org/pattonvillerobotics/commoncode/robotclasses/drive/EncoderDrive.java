package org.pattonvillerobotics.commoncode.robotclasses.drive;

import android.util.Log;

import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.enums.Direction;

public class EncoderDrive extends AbstractComplexDrive {

    public static final int TARGET_REACHED_THRESHOLD = 16;
    protected static final Consumer<DcMotor> RUN_MODE_RUN_USING_ENCODER_SETTER = new Consumer<DcMotor>() {
        @Override
        public void accept(DcMotor dcMotor) {
            dcMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    };
    protected static final Consumer<DcMotor> RUN_MODE_RUN_TO_POSITION_SETTER = new Consumer<DcMotor>() {
        @Override
        public void accept(DcMotor dcMotor) {
            dcMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    };
    protected static final Consumer<DcMotor> RUN_MODE_STOP_AND_RESET_ENCODER_SETTER = new Consumer<DcMotor>() {
        @Override
        public void accept(DcMotor dcMotor) {
            dcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    };
    protected static final Function<DcMotor, Boolean> IS_BUSY_FUNCTION = new Function<DcMotor, Boolean>() {
        @Override
        public Boolean apply(DcMotor dcMotor) {
            return dcMotor.isBusy();
        }
    };
    private static final String TAG = "EncoderDrive";
    private DcMotor.RunMode leftDriveSavedMotorMode, rightDriveSavedMotorMode;

    /**
     * sets up Drive object with custom RobotParameters useful for doing calculations with encoders
     *
     * @param hardwareMap     a hardwaremap
     * @param linearOpMode    a linearopmode
     * @param robotParameters a RobotParameters containing robot specific calculations for
     *                        wheel radius and wheel base radius
     */
    public EncoderDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters robotParameters) {
        super(linearOpMode, hardwareMap, robotParameters);
        if (!robotParameters.areEncodersEnabled())
            throw new IllegalArgumentException("Robot must have encoders enabled to use EncoderDrive! If encoders are present, call encodersEnabled(true) when building.");
    }

    public Telemetry.Item telemetry(String message) {
        return super.telemetry("EncoderDrive", message);
    }

    protected boolean isMovingToPosition() {
        return leftDriveMotor.isBusy() || rightDriveMotor.isBusy();
    }

    /**
     * drives a specific number of inches in a given direction
     *
     * @param direction the direction (forward or backward) to drive in
     * @param inches    the number of inches to drive
     * @param power     the power with which to drive
     */
    @Override
    public void moveInches(Direction direction, double inches, double power) {
        //Move Specified Inches Using Motor Encoders

        int targetPositionLeft;
        int targetPositionRight;

        Log.e(TAG, "Getting motor modes");
        storeMotorModes();

        resetMotorEncoders();

        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case FORWARD: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = deltaPosition;
                break;
            }
            case BACKWARD: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = -deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.FORWARDS or Direction.BACKWARDS!");
        }

        Log.e(TAG, "Setting motor modes");
        setMotorsRunToPosition();

        Log.e(TAG, "Setting motor power high");
        move(Direction.FORWARD, power); // To keep power in [0.0, 1.0]. Encoders control direction

        Log.e(TAG, "Setting target position");
        setMotorTargets(targetPositionLeft, targetPositionRight);

        telemetry("Moving " + inches + " inches at power " + power);
        telemetry("LMotorT: " + targetPositionLeft);
        telemetry("RMotorT: " + targetPositionRight);
        telemetry("EncoderDelta: " + deltaPosition);
        Telemetry.Item distance = telemetry("DistanceL: N/A DistanceR: N/A");

        //int oldLeftPosition = leftDriveMotor.getCurrentPosition();
        //int oldRightPosition = rightDriveMotor.getCurrentPosition();

        while ((leftDriveMotor.isBusy() || rightDriveMotor.isBusy()) || !reachedTarget(leftDriveMotor.getCurrentPosition(), targetPositionLeft, rightDriveMotor.getCurrentPosition(), targetPositionRight) && !linearOpMode.isStopRequested() && linearOpMode.opModeIsActive()) {
            Thread.yield();
            distance.setValue("DistanceL: " + leftDriveMotor.getCurrentPosition() + " DistanceR: " + rightDriveMotor.getCurrentPosition());
            linearOpMode.telemetry.update();
        }
        Log.e(TAG, "Setting motor power low");
        stop();

        Log.e(TAG, "Restoring motor mode");
        restoreMotorModes();

        sleep(100);
    }

    protected void restoreMotorModes() {
        leftDriveMotor.setMode(leftDriveSavedMotorMode);
        rightDriveMotor.setMode(rightDriveSavedMotorMode);
    }

    protected void storeMotorModes() {
        leftDriveSavedMotorMode = leftDriveMotor.getMode();
        rightDriveSavedMotorMode = rightDriveMotor.getMode();
    }

    protected void resetMotorEncoders() {
        leftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * turns the robot a certain number of degrees in a given direction
     *
     * @param direction the direction (left or right) to turn in
     * @param degrees   the number of degrees to turn
     * @param speed     the speed at which to turn
     */
    @Override
    public void rotateDegrees(Direction direction, double degrees, double speed) {
        //Move specified degrees using motor encoders

        int targetPositionLeft;
        int targetPositionRight;

        Log.e(TAG, "Getting motor modes");
        storeMotorModes();

        resetMotorEncoders();

        double inches = degreesToInches(degrees);
        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case LEFT: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = deltaPosition;
                break;
            }
            case RIGHT: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = -deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.LEFT or Direction.RIGHT!");
        }

        Log.e(TAG, "Setting motor modes");
        setMotorsRunToPosition();

        setMotorTargets(targetPositionLeft, targetPositionRight);

        Telemetry.Item[] items = new Telemetry.Item[]{
                telemetry("Rotating " + degrees + " degrees at speed " + speed).setRetained(true),
                telemetry("LMotorT: " + targetPositionLeft).setRetained(true),
                telemetry("RMotorT: " + targetPositionRight).setRetained(true),
                telemetry("EncoderDelta: " + deltaPosition).setRetained(true),
                telemetry("DistanceL: DistanceR:")
        };
        Telemetry.Item distance = items[4];

        move(Direction.FORWARD, speed); // To keep speed in [0.0, 1.0]. Encoders control direction
        while (!reachedTarget(leftDriveMotor.getCurrentPosition(), targetPositionLeft, rightDriveMotor.getCurrentPosition(), targetPositionRight) && !linearOpMode.isStopRequested() && linearOpMode.opModeIsActive()) {
            Thread.yield();
            distance.setValue("DistanceL: " + leftDriveMotor.getCurrentPosition() + " DistanceR: " + rightDriveMotor.getCurrentPosition());
            linearOpMode.telemetry.update();
        }
        stop();

        Log.e(TAG, "Restoring motor mode");
        restoreMotorModes();

        for (Telemetry.Item i : items)
            i.setRetained(false);

        sleep(100);
    }

    protected void setMotorsRunToPosition() {
        if (leftDriveMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION)
            leftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (rightDriveMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION)
            rightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    protected void setMotorTargets(int targetPositionLeft, int targetPositionRight) {
        leftDriveMotor.setTargetPosition(targetPositionLeft);
        rightDriveMotor.setTargetPosition(targetPositionRight);
    }

    /**
     * determines whether or not the robot has reached its target encoder position within a
     * certain threshold
     *
     * @param currentPositionLeft  the current position of the left encoder
     * @param targetPositionLeft   the target position of the left encoder
     * @param currentPositionRight the current position of the right encoder
     * @param targetPositionRight  the target position of the right encoder
     * @return
     */
    protected boolean reachedTarget(int currentPositionLeft, int targetPositionLeft, int currentPositionRight, int targetPositionRight) {
        return FastMath.abs(currentPositionLeft - targetPositionLeft) < TARGET_REACHED_THRESHOLD && FastMath.abs(currentPositionRight - targetPositionRight) < TARGET_REACHED_THRESHOLD;
    }
}
