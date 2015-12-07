package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by skeltonn on 11/21/15.
 * TODO: Find servo values
 * TODO: Create a control method similar to ZipRelease
 */
public class Blocker {

    private static final double UP_LEFT = 1 - .3;
    private static final double UP_RIGHT = 0 + .3;
    private static final double DOWN_LEFT = 0 + .05;
    private static final double DOWN_RIGHT = 1 - .05;
    public Servo servoLeft;
    public Servo servoRight;
    private HardwareMap hardwareMap;
    private Direction currentDirection;

    public Blocker(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.servoLeft = hardwareMap.servo.get(Config.SERVO_BLOCKER_LEFT);
        this.servoRight = hardwareMap.servo.get(Config.SERVO_BLOCKER_RIGHT);
        this.move(Direction.DOWN);
        this.currentDirection = Direction.DOWN;
    }

    public void toggle() {
        if (currentDirection == Direction.DOWN)
            this.move(Direction.UP);
        else
            this.move(Direction.DOWN);
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                servoLeft.setPosition(UP_LEFT);
                servoRight.setPosition(UP_RIGHT);
                break;
            case DOWN:
                servoLeft.setPosition(DOWN_LEFT);
                servoRight.setPosition(DOWN_RIGHT);
                break;
            default:
                throw new IllegalArgumentException("Direction must be UP or DOWN!");

        }
    }

}
