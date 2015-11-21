package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by James McMahon on 10/22/15.
 */
public class ZipRelease {

    private static final double LEFT_UP = 0;
    private static final double LEFT_DOWN = 1;
    private static final double RIGHT_UP = 1;
    private static final double RIGHT_DOWN = 0;
    public Servo servoReleaseLeft;
    public Servo servoReleaseRight;
    private HardwareMap hardwareMap;

    private Direction servoLeft;
    private Direction servoRight;

    public ZipRelease(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.servoReleaseLeft = hardwareMap.servo.get(Config.SERVO_RELEASE_LEFT);
        this.servoReleaseRight = hardwareMap.servo.get(Config.SERVO_RELEASE_RIGHT);
    }

    public void moveLeft(Direction direction) {

        switch (direction) {

            case UP:
                servoReleaseLeft.setPosition(LEFT_UP);
                servoLeft = Direction.UP;
                break;

            case DOWN:
                servoReleaseLeft.setPosition(LEFT_DOWN);
                servoLeft = Direction.DOWN;
                break;
        }
    }

    public void moveRight(Direction direction) {
        switch (direction) {

            case UP:
                servoReleaseRight.setPosition(RIGHT_UP);
                servoRight = Direction.UP;
                break;

            case DOWN:
                servoReleaseRight.setPosition(RIGHT_DOWN);
                servoRight = Direction.DOWN;
                break;
        }
    }

    public String toString() {

        return "Left Release: " + servoLeft + "\n" +
                "Right Release: " + servoRight;
    }
}