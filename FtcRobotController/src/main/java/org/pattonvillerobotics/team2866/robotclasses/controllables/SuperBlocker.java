package org.pattonvillerobotics.team2866.robotclasses.controllables;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;

/**
 * Created by skaggsm on 1/28/16.
 */
public class SuperBlocker {

    private static final double VERTICAL_UP = .6;
    private static final double VERTICAL_MID = .3;
    private static final double VERTICAL_DOWN = .25;

    private static final double LEFT_DOWN = .31;
    private static final double LEFT_MID = .38;
    private static final double LEFT_UP = .58;

    private static final double RIGHT_DOWN = .60;
    private static final double RIGHT_MID = .57;
    private static final double RIGHT_UP = .35;

    private static final String TAG = "SuperBlocker";
    private static final int encoderMidDelta = 40; // Add to the start position
    private static final int encoderHighDelta = 280;
    public final Servo servoLeft, servoRight, servoVertical;
    public final DcMotor motorVertical;
    private int encoderStartPosition;
    private Direction verticalPostiiton;
    private Direction horizontalPosition;


    public SuperBlocker(HardwareMap hardwareMap, LinearOpMode linearOpMode) throws InterruptedException {
        this.servoLeft = hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_LEFT);
        this.servoRight = hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_RIGHT);
        //noinspection AssignmentToNull
        this.servoVertical = null;//hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_VERTICAL);

        motorVertical = hardwareMap.dcMotor.get(Config.MOTOR_SUPERBLOCKER_VERTICAL);
        motorVertical.setDirection(DcMotor.Direction.FORWARD);
        motorVertical.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        encoderStartPosition = motorVertical.getCurrentPosition();
        linearOpMode.waitOneFullHardwareCycle();

        motorVertical.setTargetPosition(encoderStartPosition);
        motorVertical.setPower(1);
        linearOpMode.waitOneFullHardwareCycle();

        moveVertical(Direction.DOWN);
        linearOpMode.waitOneFullHardwareCycle();
    }

    public void moveVertical(Direction direction) {
        Log.d(TAG, "Moving vertical to " + direction);
        verticalPostiiton = direction;
        switch (direction) {
            case UP:
                motorVertical.setTargetPosition(encoderStartPosition + encoderHighDelta);
                //servoVertical.setPosition(VERTICAL_UP);

                setPosition(Direction.MID);
                break;
            case MID:
                motorVertical.setTargetPosition(encoderStartPosition + encoderMidDelta);
                //servoVertical.setPosition(VERTICAL_MID);
                break;
            case DOWN:
                motorVertical.setTargetPosition(encoderStartPosition);
                //servoVertical.setPosition(VERTICAL_DOWN);

                setPosition(Direction.MID);
                break;
            default:
                throw new IllegalArgumentException("Direction must be UP, DOWN, MID!");

        }
    }

    public void setPosition(Direction direction) {
        if (verticalPostiiton == Direction.MID) {
            Log.d(TAG, "Moving sides to " + direction);
            switch (direction) {
                case UP:
                    servoLeft.setPosition(LEFT_DOWN);
                    servoRight.setPosition(RIGHT_DOWN);
                    break;
                case MID:
                    servoLeft.setPosition(LEFT_MID);
                    servoRight.setPosition(RIGHT_MID);
                    break;
                case DOWN:
                    servoLeft.setPosition(LEFT_UP);
                    servoRight.setPosition(RIGHT_UP);
                    break;
                default:
                    throw new IllegalArgumentException("Direction must be UP, DOWN, MID!");
            }
            horizontalPosition = direction;
        } else {
            servoLeft.setPosition(LEFT_MID);
            servoRight.setPosition(RIGHT_MID);
            horizontalPosition = Direction.MID;
        }
    }
}
