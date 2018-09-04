package org.pattonvillerobotics.commoncode.robotclasses.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.enums.Direction;

/**
 * Created by skaggsm on 9/27/16.
 */

public abstract class AbstractComplexDrive extends AbstractDrive {

    protected final RobotParameters robotParameters;

    public AbstractComplexDrive(LinearOpMode linearOpMode, HardwareMap hardwareMap, RobotParameters robotParameters) {
        super(linearOpMode, hardwareMap);
        this.robotParameters = robotParameters;

        if (robotParameters.areEncodersEnabled()) {
            this.leftDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.rightDriveMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        this.leftDriveMotor.setDirection(robotParameters.getLeftDriveMotorDirection());
        this.rightDriveMotor.setDirection(robotParameters.getRightDriveMotorDirection());
    }

    /**
     * NOT REVERSIBLE WITH <code>degreesToInches</code>!
     *
     * @param inches the number of inches to be covered by a single wheel
     * @return the number of encoder ticks to achieve that
     * @see AbstractComplexDrive#inchesToTicksInverse(int)
     */
    public double inchesToTicks(double inches) {
        return robotParameters.getAdjustedTicksPerRevolution() * inches / robotParameters.getWheelCircumference();
    }

    public double inchesToTicksInverse(int ticks) {
        return ticks * robotParameters.getWheelCircumference() / robotParameters.getAdjustedTicksPerRevolution();
    }

    /**
     * NOT REVERSIBLE WITH <code>inchesToTicks</code>!
     *
     * @param degrees the number of degrees to turn the robot
     * @return the number of inches each wheel has to travel
     * @see AbstractComplexDrive#degreesToInchesInverse(double)
     */
    public double degreesToInches(double degrees) {
        return robotParameters.getWheelBaseCircumference() * degrees / 360;
    }

    public double degreesToInchesInverse(double inches) {
        return 360 * inches / robotParameters.getWheelBaseCircumference();
    }

    public abstract void moveInches(Direction direction, double inches, double power);

    public abstract void rotateDegrees(Direction direction, double degrees, double power);
}
