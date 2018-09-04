package org.pattonvillerobotics.commoncode.robotclasses.drive;

import com.annimon.stream.function.Consumer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.enums.Direction;

/**
 * Created by Mitchell on 9/9/2016.
 */
public abstract class AbstractDrive implements Drive {

    protected static final Consumer<DcMotor> ZERO_POWER_BEHAVIOR_SETTER = new Consumer<DcMotor>() {
        @Override
        public void accept(DcMotor dcMotor) {
            dcMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    };
    protected static final Consumer<DcMotor> RUN_MODE_RUN_WITHOUT_ENCODER_SETTER = new Consumer<DcMotor>() {
        @Override
        public void accept(DcMotor dcMotor) {
            dcMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    };
    protected static final Consumer<DcMotor> DIRECTION_SETTER = new Consumer<DcMotor>() {
        @Override
        public void accept(DcMotor dcMotor) {
            dcMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    };

    public final DcMotor leftDriveMotor, rightDriveMotor;
    public final LinearOpMode linearOpMode;
    public final HardwareMap hardwareMap;

    public AbstractDrive(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        this.leftDriveMotor = hardwareMap.dcMotor.get("left_drive_motor");
        this.rightDriveMotor = hardwareMap.dcMotor.get("right_drive_motor");

        this.linearOpMode = linearOpMode;
        this.hardwareMap = hardwareMap;


        ZERO_POWER_BEHAVIOR_SETTER.accept(this.leftDriveMotor);
        ZERO_POWER_BEHAVIOR_SETTER.accept(this.rightDriveMotor);

        RUN_MODE_RUN_WITHOUT_ENCODER_SETTER.accept(this.leftDriveMotor);
        RUN_MODE_RUN_WITHOUT_ENCODER_SETTER.accept(this.rightDriveMotor);

        DIRECTION_SETTER.accept(this.rightDriveMotor);
    }

    public void moveFreely(double leftPower, double rightPower) {
        leftDriveMotor.setPower(leftPower);
        rightDriveMotor.setPower(rightPower);
    }

    public void move(Direction direction, double power) {
        double motorPower;

        switch (direction) {
            case FORWARD:
                motorPower = power;
                break;
            case BACKWARD:
                motorPower = -power;
                break;
            default:
                throw new IllegalArgumentException("Direction must be Backward or Forwards");
        }

        moveFreely(motorPower, motorPower);
    }

    public void turn(Direction direction, double power) {

        double left, right;

        switch (direction) {
            case LEFT:
                left = -power;
                right = power;
                break;
            case RIGHT:
                left = power;
                right = -power;
                break;
            default:
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT");
        }

        moveFreely(left, right);
    }

    @Override
    public void stop() {
        moveFreely(0, 0);
    }

    public void sleep(long milli) {
        this.linearOpMode.sleep(milli);
    }

    public Telemetry.Item telemetry(String tag, String message) {
        return this.linearOpMode.telemetry.addData(tag, message);
    }
}
