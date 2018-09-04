package org.pattonvillerobotics.commoncode.robotclasses.drive;

import android.util.Log;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.enums.Direction;

public class GyroEncoderDrive extends EncoderDrive {

    private static final double ANGLE_THRESHOLD = 1;

    public ModernRoboticsI2cGyro gyroSensor = null;

    public GyroEncoderDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters robotParameters) {
        super(hardwareMap, linearOpMode, robotParameters);

        gyroSensor = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        gyroSensor.calibrate();

        while (gyroSensor.isCalibrating() && !linearOpMode.isStopRequested()) {
            linearOpMode.idle();
        }
    }

    @Override
    public Telemetry.Item telemetry(String message) {
        return super.telemetry("GyroEncoderDrive", message);
    }

    @Override
    public void rotateDegrees(Direction direction, double degrees, double speed) {
        this.gyroTurnDegrees(direction, degrees, speed);
    }

    public void gyroTurnDegrees(Direction direction, double angle, double speed) {
        //Turn Specified Degrees Using Gyro Sensor

        double currentHeading = gyroSensor.getIntegratedZValue();
        double targetHeading;
        Direction currentDirection = direction;
        int numOvershoots = 0;

        switch (direction) {
            case LEFT:
                targetHeading = currentHeading - angle;
                break;
            case RIGHT:
                targetHeading = currentHeading + angle;
                break;
            default:
                throw new IllegalArgumentException();

        }

        Telemetry.Item headingsTelemetryItem = telemetry("Headings", "Current Heading: " + currentHeading + "& Target Heading: " + targetHeading);

        while (FastMath.abs(currentHeading - targetHeading) > .01) {
            Direction newDirection = FastMath.signum(currentHeading - targetHeading) > 0 ? Direction.LEFT : Direction.RIGHT;
            if (newDirection != currentDirection)
                numOvershoots++;
            turn(currentDirection, speed / (numOvershoots + 1));
            currentHeading = gyroSensor.getIntegratedZValue();
            headingsTelemetryItem.setValue("Headings", "Current Heading: " + currentHeading + "& Target Heading: " + targetHeading);

            if (numOvershoots > 5)
                break;
        }

        telemetry("Drive", "Angle obtained, stopping motors.");
        Log.i("GyroHeading", Double.toString(gyroSensor.getIntegratedZValue()));

        stop();
    }
}
