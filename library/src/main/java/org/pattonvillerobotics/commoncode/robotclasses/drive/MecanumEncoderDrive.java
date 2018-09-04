package org.pattonvillerobotics.commoncode.robotclasses.drive;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.enums.Direction;

import java.util.Locale;

import static org.apache.commons.math3.util.FastMath.PI;
import static org.apache.commons.math3.util.FastMath.cos;
import static org.apache.commons.math3.util.FastMath.sin;

/**
 * Created by greg on 10/2/2017.
 */

public class MecanumEncoderDrive extends QuadEncoderDrive {

    private static final String TAG = "MecanumEncoderDrive";
    private static final double COS_135 = cos(3 * PI / 4);
    private static final double SIN_135 = -COS_135;
    private static final double DEG_45 = PI / 4;
    public DcMotor leftRearMotor, rightRearMotor;

    public MecanumEncoderDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters robotParameters) {
        super(hardwareMap, linearOpMode, robotParameters);

        if (!super.secondaryLeftDriveMotor.isPresent() || !super.secondaryRightDriveMotor.isPresent()) {
            throw new IllegalArgumentException("Mecanum drive requires all 4 motors to be present!");
        }

        this.leftRearMotor = secondaryLeftDriveMotor.get();
        this.rightRearMotor = secondaryRightDriveMotor.get();

        this.leftRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.leftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    /**
     * can be used to convert joystick values to polar
     *
     * @return coordinate array in the form of [r, theta]
     */
    public static Vector2D toPolar(double x, double y) {
        return new Vector2D(FastMath.hypot(x, y), FastMath.atan2(y, x));
    }

    /**
     * used to drive a mecanum drive train
     *
     * @param angle    direction to go in radians
     * @param speed    speed to go
     * @param rotation rate of rotation
     */
    public void moveFreely(double angle, double speed, double rotation) {
        double xcomponent = COS_135 * (cos(angle + DEG_45));
        double ycomponent = SIN_135 * (sin(angle + DEG_45));


//        double scale = 1. / FastMath.max(FastMath.abs(xcomponent), FastMath.abs(ycomponent));
//        xcomponent *= scale;
//        ycomponent *= scale;

        super.leftDriveMotor.setPower((speed * ycomponent) - rotation);
        super.rightDriveMotor.setPower((speed * xcomponent) + rotation);
        this.leftRearMotor.setPower((speed * xcomponent) - rotation);
        this.rightRearMotor.setPower((speed * ycomponent) + rotation);
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
        int targetPositionLeftRear;
        int targetPositionRightRear;

        Log.e(TAG, "Getting motor modes");
        storeMotorModes();

        resetMotorEncoders();

        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case FORWARD: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = deltaPosition;
                targetPositionLeftRear = deltaPosition;
                targetPositionRightRear = deltaPosition;
                break;
            }
            case BACKWARD: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = -deltaPosition;
                targetPositionLeftRear = -deltaPosition;
                targetPositionRightRear = -deltaPosition;
                break;
            }
            case LEFT: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = -deltaPosition;
                targetPositionLeftRear = -deltaPosition;
                targetPositionRightRear = deltaPosition;
                break;
            }
            case RIGHT: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = deltaPosition;
                targetPositionLeftRear = deltaPosition;
                targetPositionRightRear = -deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.FORWARDS, Direction.BACKWARDS, Direction.LEFT, or Direction.RIGHT!");
        }

        Log.e(TAG, "Setting motor modes");
        setMotorsRunToPosition();

        Log.e(TAG, "Setting motor power high");
        move(Direction.FORWARD, power); // To keep power in [0.0, 1.0]. Encoders control direction

        Log.e(TAG, "Setting target position");
        setMotorTargets(targetPositionLeft, targetPositionRight, targetPositionLeftRear, targetPositionRightRear);

        telemetry("Moving " + inches + " inches at power " + power);
        telemetry("LFMotorT: " + targetPositionLeft);
        telemetry("RFMotorT: " + targetPositionRight);
        telemetry("LRMotorT: " + targetPositionLeftRear);
        telemetry("RRMotorT: " + targetPositionRightRear);
        telemetry("EncoderDelta: " + deltaPosition);
        Telemetry.Item distance = telemetry("DistanceL: N/A DistanceR: N/A");
        Telemetry.Item distanceRear = telemetry("DistanceLR: N/A DistanceRR: N/A");

        while (isMovingToPosition()
                || !motorsReachedTarget(targetPositionLeft, targetPositionRight, targetPositionLeftRear, targetPositionRightRear)
                && linearOpMode.opModeIsActive()) {
            distance.setValue(String.format(Locale.getDefault(), "DistanceL: %d DistanceR: %d", leftDriveMotor.getCurrentPosition(), rightDriveMotor.getCurrentPosition()));
            distanceRear.setValue(String.format(Locale.getDefault(), "DistanceLR: %d DistanceRR: %d", secondaryLeftDriveMotor.get().getCurrentPosition(), secondaryRightDriveMotor.get().getCurrentPosition()));
            linearOpMode.telemetry.update();
        }
        Log.e(TAG, "Setting motor power low");
        stop();

        Log.e(TAG, "Restoring motor mode");
        restoreMotorModes();

        sleep(100);
    }

    @Override
    public void rotateDegrees(Direction direction, double degrees, double speed) {
        //Move specified degrees using motor encoders
        //TODO: use the IMU on the REV module for more accurate turns
        int targetPositionLeft;
        int targetPositionRight;
        int targetPositionLeftRear;
        int targetPositionRightRear;

        Log.e(TAG, "Getting motor modes");
        storeMotorModes();

        resetMotorEncoders();

        double inches = degreesToInches(degrees);
        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case LEFT: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = deltaPosition;
                targetPositionLeftRear = -deltaPosition;
                targetPositionRightRear = deltaPosition;
                break;
            }
            case RIGHT: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = -deltaPosition;
                targetPositionLeftRear = deltaPosition;
                targetPositionRightRear = -deltaPosition;
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
                telemetry("LFMotorT: " + targetPositionLeft).setRetained(true),
                telemetry("RFMotorT: " + targetPositionRight).setRetained(true),
                telemetry("LRMotorT: " + targetPositionLeftRear).setRetained(true),
                telemetry("RRMotorT: " + targetPositionRightRear).setRetained(true),
                telemetry("EncoderDelta: " + deltaPosition).setRetained(true),
                telemetry("DistanceLF: DistanceRF:").setRetained(true),
                telemetry("DistanceLR: DistanceRR:").setRetained(true)
        };
        Telemetry.Item distance = items[6];
        Telemetry.Item distanceRear = items[7];

        move(Direction.FORWARD, speed); // To keep speed in [0.0, 1.0]. Encoders control direction
        while (isMovingToPosition()
                || !motorsReachedTarget(targetPositionLeft, targetPositionRight, targetPositionLeftRear, targetPositionRightRear)
                && linearOpMode.opModeIsActive()) {
            distance.setValue("DistanceLF: " + leftDriveMotor.getCurrentPosition() + " DistanceRF: " + rightDriveMotor.getCurrentPosition());
            distanceRear.setValue("DistanceLR: " + leftRearMotor.getCurrentPosition() + " DistanceRR: " + rightRearMotor.getCurrentPosition());
            linearOpMode.telemetry.update();
        }
        stop();

        Log.e(TAG, "Restoring motor mode");
        restoreMotorModes();

        for (Telemetry.Item i : items)
            i.setRetained(false);

        sleep(100);
    }

    @Override
    public void move(Direction direction, double power) {
        double angle;

        switch (direction) {
            case FORWARD:
                angle = FastMath.toRadians(90);
                break;
            case BACKWARD:
                angle = FastMath.toRadians(270);
                break;
            case LEFT:
                angle = FastMath.toRadians(180);
                break;
            case RIGHT:
                angle = 0;
                break;
            default:
                throw new IllegalArgumentException("Direction must be FORWARD, BACKWARD, LEFT, or RIGHT");
        }
        moveFreely(angle, power, 0);
    }

    @Override
    public void turn(Direction direction, double power) {
        double rotation;

        switch (direction) {
            case LEFT:
                rotation = -power;
                break;
            case RIGHT:
                rotation = power;
                break;
            default:
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT");
        }
        moveFreely(0, 0, rotation);
    }

    protected void setMotorTargets(int targetPositionLeft, int targetPositionRight, int targetPositionLeftRear, int targetPositionRightRear) {
        leftDriveMotor.setTargetPosition(targetPositionLeft);
        rightDriveMotor.setTargetPosition(targetPositionRight);
        leftRearMotor.setTargetPosition(targetPositionLeftRear);
        rightRearMotor.setTargetPosition(targetPositionRightRear);
    }

    protected boolean motorsReachedTarget(int targetPositionLeft, int targetPositionRight, int targetPositionLeftRear, int targetPositionRightRear) {
        return reachedTarget(leftDriveMotor.getCurrentPosition(), targetPositionLeft, rightDriveMotor.getCurrentPosition(), targetPositionRight) &&
                reachedTarget(leftRearMotor.getCurrentPosition(), targetPositionLeftRear, rightRearMotor.getCurrentPosition(), targetPositionRightRear);
    }
}
