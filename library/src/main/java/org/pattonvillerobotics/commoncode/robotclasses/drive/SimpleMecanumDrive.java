package org.pattonvillerobotics.commoncode.robotclasses.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;

/**
 * Drive class for a mecanum drive without encoders.
 */
public class SimpleMecanumDrive extends AbstractDrive {

    public final DcMotor leftRearMotor, rightRearMotor;

    private final double COS135 = FastMath.cos((3*FastMath.PI)/4);
    private final double SIN135 = -COS135;
    private final double DEG45 = FastMath.PI / 4;

    public SimpleMecanumDrive(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        super(linearOpMode, hardwareMap);

        leftRearMotor = hardwareMap.dcMotor.get("left_rear_motor");
        rightRearMotor = hardwareMap.dcMotor.get("right_rear_motor");

        ZERO_POWER_BEHAVIOR_SETTER.accept(this.leftRearMotor);
        ZERO_POWER_BEHAVIOR_SETTER.accept(this.rightRearMotor);

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
        double xcomponent = COS135 * (FastMath.cos(angle + DEG45));
        double ycomponent = SIN135 * (FastMath.sin(angle + DEG45));

//        double scale = 1. / FastMath.max(FastMath.abs(xcomponent), FastMath.abs(ycomponent));
//        xcomponent *= scale;
//        ycomponent *= scale;

        super.leftDriveMotor.setPower((speed * ycomponent) - rotation);
        super.rightDriveMotor.setPower((speed * xcomponent) + rotation);
        this.leftRearMotor.setPower((speed * xcomponent) - rotation);
        this.rightRearMotor.setPower((speed * ycomponent) + rotation);
    }

    /**
     * drives a mecanum drive train based on input of two joysticks
     *
     * @param x joystick x-position
     * @param y joystick y-position
     * @param rotation x-position of the other joystick
     */
    public void driveWithJoysticks(double x, double y, double rotation) {
        Vector2D coords = toPolar(x, y);
        moveFreely(coords.getY(), coords.getX(), rotation);
    }

}
