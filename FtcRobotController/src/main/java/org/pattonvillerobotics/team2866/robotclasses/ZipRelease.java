package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by James McMahon on 10/22/15.
 */
public class ZipRelease {

    private static final double LEFT_UP = 0;
    private static final double LEFT_DOWN = 0;
    private static final double RIGHT_UP = 0;
    private static final double RIGHT_DOWN = 0;
    private HardwareMap hardwareMap;
    private Servo servoLeft;
    private Servo servoRight;

    public ZipRelease(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.servoLeft = hardwareMap.servo.get(Config.SERVO_RELEASE_LEFT);
        this.servoRight = hardwareMap.servo.get(Config.SERVO_RELEASE_RIGHT);
    }

    public void moveLeft(DirectionEnum direction) {

        switch (direction) {

            case UP:
                servoLeft.setPosition(LEFT_UP);
                break;

            case DOWN:
                servoLeft.setPosition(LEFT_DOWN);
                break;
        }
    }

    public void moveRight(DirectionEnum direction) {
        switch (direction) {

            case UP:
                servoRight.setPosition(RIGHT_UP);
                break;

            case DOWN:
                servoRight.setPosition(RIGHT_DOWN);
                break;
        }
    }
}