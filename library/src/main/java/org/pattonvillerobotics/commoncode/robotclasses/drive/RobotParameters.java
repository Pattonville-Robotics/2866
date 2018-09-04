package org.pattonvillerobotics.commoncode.robotclasses.drive;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.apache.commons.math3.util.FastMath;

/**
 * Created by skaggsm on 9/22/16.
 */



public class RobotParameters {
    public static final int TICKS_PER_REVOLUTION = 1440;

    /**
     * Provided values
     */
    private final double wheelBaseRadius, wheelRadius, driveGearRatio;
    private final boolean gyroEnabled, encodersEnabled;
    private final DcMotorSimple.Direction leftDriveMotorDirection, rightDriveMotorDirection;

    /**
     * Cached computed values, never change since the class is final
     */
    private final double wheelCircumference, wheelBaseCircumference, adjustedTicksPerRevolution;

    private RobotParameters(double wheelBaseRadius, double wheelRadius, double driveGearRatio, boolean gyroEnabled, boolean encodersEnabled, DcMotorSimple.Direction leftDriveMotorDirection, DcMotorSimple.Direction rightDriveMotorDirection) {
        this.wheelBaseRadius = wheelBaseRadius;
        this.wheelRadius = wheelRadius;
        this.driveGearRatio = driveGearRatio;
        this.gyroEnabled = gyroEnabled;
        this.encodersEnabled = encodersEnabled;

        this.wheelCircumference = wheelRadius * 2 * FastMath.PI;
        this.wheelBaseCircumference = wheelBaseRadius * 2 * FastMath.PI;
        this.adjustedTicksPerRevolution = TICKS_PER_REVOLUTION / driveGearRatio;
        this.leftDriveMotorDirection = leftDriveMotorDirection;
        this.rightDriveMotorDirection = rightDriveMotorDirection;
    }

    public double getWheelBaseRadius() {
        return wheelBaseRadius;
    }

    public double getWheelRadius() {
        return wheelRadius;
    }

    public double getAdjustedTicksPerRevolution() {
        return adjustedTicksPerRevolution;
    }

    public double getWheelCircumference() {
        return wheelCircumference;
    }

    public double getWheelBaseCircumference() {
        return wheelBaseCircumference;
    }

    public double getDriveGearRatio() {
        return driveGearRatio;
    }

    public boolean isGyroEnabled() {
        return gyroEnabled;
    }

    public DcMotorSimple.Direction getLeftDriveMotorDirection() {
        return leftDriveMotorDirection;
    }

    public DcMotorSimple.Direction getRightDriveMotorDirection() {
        return rightDriveMotorDirection;
    }

    public boolean areEncodersEnabled() {
        return encodersEnabled;
    }

    public static class Builder {
        private double wheelBaseRadius;
        private double wheelRadius;
        private double driveGearRatio = 1;
        private boolean gyroEnabled = false;
        private boolean encodersEnabled = false;
        private DcMotorSimple.Direction leftDriveMotorDirection = DcMotorSimple.Direction.FORWARD;
        private DcMotorSimple.Direction rightDriveMotorDirection = DcMotorSimple.Direction.REVERSE;

        public Builder() {
        }

        public Builder wheelBaseRadius(double wheelBaseRadius) {
            this.wheelBaseRadius = wheelBaseRadius;
            return this;
        }

        public Builder wheelRadius(double wheelRadius) {
            this.wheelRadius = wheelRadius;
            return this;
        }

        public Builder driveGearRatio(double driveGearRatio) {
            this.driveGearRatio = driveGearRatio;
            return this;
        }

        public Builder gyroEnabled(boolean gyroEnabled) {
            this.gyroEnabled = gyroEnabled;
            return this;
        }

        public Builder encodersEnabled(boolean encodersEnabled) {
            this.encodersEnabled = encodersEnabled;
            return this;
        }

        public Builder leftDriveMotorDirection(DcMotorSimple.Direction leftDriveMotorDirection) {
            this.leftDriveMotorDirection = leftDriveMotorDirection;
            return this;
        }

        public Builder rightDriveMotorDirection(DcMotorSimple.Direction rightDriveMotorDirection) {
            this.rightDriveMotorDirection = rightDriveMotorDirection;
            return this;
        }

        public RobotParameters build() {
            if (wheelBaseRadius <= 0)
                throw new IllegalArgumentException("wheelBaseRadius must be > 0");
            if (wheelRadius <= 0)
                throw new IllegalArgumentException("wheelRadius must be > 0");
            return new RobotParameters(wheelBaseRadius, wheelRadius, driveGearRatio, gyroEnabled, encodersEnabled, leftDriveMotorDirection, rightDriveMotorDirection);
        }
    }
}
