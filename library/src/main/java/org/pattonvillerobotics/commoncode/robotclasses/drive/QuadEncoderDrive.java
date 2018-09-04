package org.pattonvillerobotics.commoncode.robotclasses.drive;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by skaggsm on 3/9/17.
 */

public class QuadEncoderDrive extends EncoderDrive {
    private static final String LEFT_REAR_MOTOR_NAME = "left_rear_motor";
    private static final String RIGHT_REAR_MOTOR_NAME = "right_rear_motor";
    public final Optional<DcMotor> secondaryLeftDriveMotor, secondaryRightDriveMotor;

    /**
     * Sets up Drive object with custom RobotParameters useful for doing calculations with encoders
     *
     * @param hardwareMap     a hardwareMap
     * @param linearOpMode    a linearOpMode
     * @param robotParameters a RobotParameters containing robot specific calculations
     */
    public QuadEncoderDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, final RobotParameters robotParameters) {
        super(hardwareMap, linearOpMode, robotParameters);

        this.secondaryLeftDriveMotor = hardwareMap.dcMotor.contains(LEFT_REAR_MOTOR_NAME) ? Optional.of(hardwareMap.dcMotor.get(LEFT_REAR_MOTOR_NAME)) : Optional.<DcMotor>empty();
        this.secondaryRightDriveMotor = hardwareMap.dcMotor.contains(RIGHT_REAR_MOTOR_NAME) ? Optional.of(hardwareMap.dcMotor.get(RIGHT_REAR_MOTOR_NAME)) : Optional.<DcMotor>empty();

        this.secondaryLeftDriveMotor.ifPresent(ZERO_POWER_BEHAVIOR_SETTER);
        this.secondaryRightDriveMotor.ifPresent(ZERO_POWER_BEHAVIOR_SETTER);

        this.secondaryLeftDriveMotor.ifPresent(RUN_MODE_RUN_WITHOUT_ENCODER_SETTER);
        this.secondaryRightDriveMotor.ifPresent(RUN_MODE_RUN_WITHOUT_ENCODER_SETTER);

        this.secondaryRightDriveMotor.ifPresent(DIRECTION_SETTER);


        if (robotParameters.areEncodersEnabled()) {
            this.secondaryLeftDriveMotor.ifPresent(RUN_MODE_RUN_USING_ENCODER_SETTER);
            this.secondaryRightDriveMotor.ifPresent(RUN_MODE_RUN_USING_ENCODER_SETTER);
        }

        this.secondaryLeftDriveMotor.ifPresent(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setDirection(robotParameters.getLeftDriveMotorDirection());
            }
        });
        this.secondaryRightDriveMotor.ifPresent(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setDirection(robotParameters.getRightDriveMotorDirection());
            }
        });
    }

    @Override
    protected boolean isMovingToPosition() {
        return super.isMovingToPosition()
                && this.secondaryLeftDriveMotor.map(IS_BUSY_FUNCTION).orElse(true)
                && this.secondaryRightDriveMotor.map(IS_BUSY_FUNCTION).orElse(true);
    }

    @Override
    protected void setMotorsRunToPosition() {
        super.setMotorsRunToPosition();
        this.secondaryLeftDriveMotor.ifPresent(RUN_MODE_RUN_TO_POSITION_SETTER);
        this.secondaryRightDriveMotor.ifPresent(RUN_MODE_RUN_TO_POSITION_SETTER);
    }

    @Override
    protected void resetMotorEncoders() {
        super.resetMotorEncoders();
        this.secondaryLeftDriveMotor.ifPresent(RUN_MODE_STOP_AND_RESET_ENCODER_SETTER);
        this.secondaryRightDriveMotor.ifPresent(RUN_MODE_STOP_AND_RESET_ENCODER_SETTER);
    }

    @Override
    protected void setMotorTargets(final int targetPositionLeft, final int targetPositionRight) {
        super.setMotorTargets(targetPositionLeft, targetPositionRight);
        this.secondaryLeftDriveMotor.ifPresent(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setTargetPosition(targetPositionLeft);
            }
        });
        this.secondaryRightDriveMotor.ifPresent(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setTargetPosition(targetPositionRight);
            }
        });
    }

    @Override
    public void moveFreely(final double leftPower, final double rightPower) {
        super.moveFreely(leftPower, rightPower);
        this.secondaryLeftDriveMotor.ifPresent(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setPower(leftPower);
            }
        });
        this.secondaryRightDriveMotor.ifPresent(new Consumer<DcMotor>() {
            @Override
            public void accept(DcMotor dcMotor) {
                dcMotor.setPower(rightPower);
            }
        });
    }
}
