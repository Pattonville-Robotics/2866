package org.pattonvillerobotics.robotclasses.misc;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.enums.ArmType;

/**
 * Parameters for the AbstractArm class.
 * To be implemented later in the season
 * to improve functionality of the arm.
 * @author Samuel Vaclavik
 */
public class ArmParameters {


    private final int numberOfServos, numberOfMotors, numberOfActuaries;
    //assumes only one motor for right now
    private final double motorGearRatio;
    private final boolean encodersEnabled;
    private final ArmType armType;

    public ArmParameters(int numberOfServos, int numberOfMotors, int numberOfActuaries, boolean encodersEnabled, ArmType armType, double motorGearRatio) {
        this.numberOfServos = numberOfServos;
        this.numberOfMotors = numberOfMotors;
        this.numberOfActuaries = numberOfActuaries;
        this.encodersEnabled = encodersEnabled;
        this.motorGearRatio = motorGearRatio;
        this.armType = armType;
    }

    public int getNumberOfServos() {
        return numberOfServos;
    }

    public int getNumberOfMotors() {
        return numberOfMotors;
    }

    public int getNumberOfActuaries() {
        return numberOfActuaries;
    }

    public boolean areEncodersEnabled() {
        return encodersEnabled;
    }

    public ArmType getArmType() {
        return armType;
    }

    public double getAdjustedTicksPerRevolution() {
        return RobotParameters.TICKS_PER_REVOLUTION / motorGearRatio;
    }

    public static class Builder {
        private int numberOfServos;
        private int numberOfMotors;
        private int numberOfActuaries;
        private double motorGearRatio = 1;
        private boolean encodersEnabled = false;
        private ArmType armType = ArmType.LINEAR;

        public Builder() {
        }

        public Builder numberOfServos(int numberOfServos) {
            this.numberOfServos = numberOfServos;
            return this;
        }

        public Builder numberOfMotors(int numberOfMotors) {
            this.numberOfMotors = numberOfMotors;
            return this;
        }

        public Builder numberOfActuaries(int numberOfActuaries) {
            this.numberOfActuaries = numberOfActuaries;
            return this;
        }

        public Builder motorGearRatio(double motorGearRatio) {
            this.motorGearRatio = motorGearRatio;
            return this;
        }

        public Builder encodersEnabled(boolean encodersEnabled) {
            this.encodersEnabled = encodersEnabled;
            return this;
        }

        public Builder armType(ArmType armType) {
            this.armType = armType;
            return this;
        }

        public ArmParameters build() {
            if(numberOfServos < 0)
                throw new IllegalArgumentException("Cannot have negative amount of servos");
            if(numberOfActuaries < 0)
                throw new IllegalArgumentException("Cannot have negative amount of actuaries");
            if(numberOfMotors < 0)
                throw new IllegalArgumentException("Cannot have negative amount of motors");

            return new ArmParameters(numberOfServos, numberOfMotors, numberOfActuaries, encodersEnabled, armType, motorGearRatio);
        }
    }
}
