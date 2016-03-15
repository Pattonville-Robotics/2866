package org.pattonvillerobotics.team2866.robotclasses.controllables;

import android.util.Log;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;

/**
 * Created by James McMahon on 10/22/15.
 */
public class ZipRelease {

    //BLockers in the back orientation
    private static final double LEFT_UP = .8;
    private static final double LEFT_DOWN = 0;
    private static final double RIGHT_UP = .2;
    private static final double RIGHT_DOWN = 1;
    private static final String TAG = "ZipRelease";
    public final Servo servoReleaseLeft;
    public final Servo servoReleaseRight;

    private Direction servoLeft;
    private Direction servoRight;

    private boolean leftReleaseDown;
    private boolean rightReleaseDown;

    public ZipRelease(HardwareMap hardwareMap) {
        this.servoReleaseLeft = hardwareMap.servo.get(Config.SERVO_RELEASE_LEFT);
        this.servoReleaseRight = hardwareMap.servo.get(Config.SERVO_RELEASE_RIGHT);

        this.moveLeft(Direction.UP);
        this.moveRight(Direction.UP);
    }

    public void moveLeft(Direction direction) {
        Log.i(TAG, "Moving left to " + direction);
        switch (direction) {
            case UP:
                servoReleaseLeft.setPosition(LEFT_UP);
                servoLeft = Direction.UP;
                break;
            case DOWN:
                servoReleaseLeft.setPosition(LEFT_DOWN);
                servoLeft = Direction.DOWN;
                break;
            default:
                throw new IllegalArgumentException("Direction must be UP or DOWN!");
        }
    }

    public void moveRight(Direction direction) {
        Log.i(TAG, "Moving right to " + direction);
        switch (direction) {
            case UP:
                servoReleaseRight.setPosition(RIGHT_UP);
                servoRight = Direction.UP;
                break;
            case DOWN:
                servoReleaseRight.setPosition(RIGHT_DOWN);
                servoRight = Direction.DOWN;
                break;
            default:
                throw new IllegalArgumentException("Direction must be UP or DOWN!");
        }
    }

    public String toString() {

        return "Left Release: " + servoLeft + "\n" +
                "Right Release: " + servoRight;
    }
}