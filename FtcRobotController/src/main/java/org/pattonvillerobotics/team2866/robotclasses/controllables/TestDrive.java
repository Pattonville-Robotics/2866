package org.pattonvillerobotics.team2866.robotclasses.controllables;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;

/**
 * Created by skeltonn on 3/13/16.
 */
public class TestDrive extends Drive {

    private static final String TAG = "TestDrive";

    public TestDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
    }

    @Override
    public void moveInches(Direction direction, double inches, double power) {

        if (power > 1 || power < 0)
            throw new IllegalArgumentException("Power must be positive!");
        if (inches <= 0)
            throw new IllegalArgumentException("Distance must be positive!");

        int targetPositionLeft;
        int targetPositionRight;

        int startPositionLeft = motorLeft.getCurrentPosition();
        int startPositionRight = motorRight.getCurrentPosition();

        Log.d(TAG, "Straight Move... Left motor start: " + startPositionLeft + " Right motor start: " + startPositionRight);

        this.waitOneFullHardwareCycle();

        switch (direction) {
            case FORWARDS: {
                int deltaPosition = (int) Math.round(inchesToTicks(inches));
                targetPositionLeft = startPositionLeft + deltaPosition;
                targetPositionRight = startPositionRight + deltaPosition;
                break;
            }
            case BACKWARDS: {
                int deltaPosition = (int) Math.round(inchesToTicks(inches));
                targetPositionLeft = startPositionLeft - deltaPosition;
                targetPositionRight = startPositionRight - deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be FORWARDS or BACKWARDS!");
        }

        this.waitOneFullHardwareCycle();

        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        this.waitOneFullHardwareCycle();

        motorLeft.setTargetPosition(targetPositionLeft);
        motorRight.setTargetPosition(targetPositionRight);

        this.waitOneFullHardwareCycle();

        motorLeft.setPower(leftPowerAdjust(power));
        motorRight.setPower(rightPowerAdjust(power));

        this.waitOneFullHardwareCycle();

        int currentError = Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft);

        double targetHeading = doubleGyroHelper.getHeading();

        int adjustmentScaler = 0;
        double adjustment = .01;

        Log.d(TAG, "Started encoder move...");
        while (this.linearOpMode.opModeIsActive() && currentError > Config.ENCODER_MOVEMENT_TOLERANCE) {

            this.waitOneFullHardwareCycle();

            Log.d(TAG, "Current distance remaining: \t" + (motorLeft.getCurrentPosition() - targetPositionLeft));

            double currentHeading = doubleGyroHelper.getHeading();

            if((targetHeading + currentHeading)/2 < 10) {

                if(currentHeading > targetHeading) {
                    currentHeading -= 360;
                }
                else if(currentHeading < targetHeading) {
                    currentHeading += 360;
                }
            }

            if(currentHeading > targetHeading) {
                adjustmentScaler--;
            }
            else if(currentHeading < targetHeading) {
                adjustmentScaler++;
            }

            motorLeft.setPower(Range.clip(leftPowerAdjust(power) + (adjustment * adjustmentScaler), -1, 1));
            motorRight.setPower(Range.clip(rightPowerAdjust(power) - (adjustment * adjustmentScaler), -1, 1));
            currentError = Math.abs(motorLeft.getCurrentPosition() - targetPositionLeft);

            linearOpMode.telemetry.addData(TAG, "Current Heading: " + doubleGyroHelper.getHeading());

        }
        Log.d(TAG, "Finished encoder move...");

        this.waitOneFullHardwareCycle();

        this.stopDriveMotors();
    }

    @Override
    public void rotateDegrees(Direction direction, double degrees, double power) {

        this.waitOneFullHardwareCycle();

        double motorLeftPower, motorRightPower;
        switch (direction) {
            case LEFT: {
                motorLeftPower = -power;
                motorRightPower = power;
                break;
            }
            case RIGHT: {
                motorLeftPower = power;
                motorRightPower = -power;
                break;
            }
            default: {
                throw new IllegalArgumentException("Direction must be LEFT or RIGHT!");
            }
        }

        motorLeft.setPower(motorLeftPower);
        motorRight.setPower(motorRightPower);

        switch(direction) {

            case LEFT:

                if(degrees < doubleGyroHelper.getHeading()) {

                    while (doubleGyroHelper.getHeading() < 359) {
                        linearOpMode.telemetry.addData(TAG, "Current Heading: " + doubleGyroHelper.getHeading());
                        sleep(100);
                    }
                }

                while(doubleGyroHelper.getHeading() > degrees) {
                    linearOpMode.telemetry.addData(TAG, "Current Heading: " + doubleGyroHelper.getHeading());
                    sleep(100);
                }
                break;
            case RIGHT:

                if(degrees > doubleGyroHelper.getHeading()) {

                    while (doubleGyroHelper.getHeading() > 1) {
                        linearOpMode.telemetry.addData(TAG, "Current Heading: " + doubleGyroHelper.getHeading());
                        sleep(100);
                    }
                }

                while(doubleGyroHelper.getHeading() < degrees) {
                    linearOpMode.telemetry.addData(TAG, "Current Heading: " + doubleGyroHelper.getHeading());
                    sleep(100);
                }
                break;
        }

        this.stopDriveMotors();

        this.waitOneFullHardwareCycle();
    }
}
