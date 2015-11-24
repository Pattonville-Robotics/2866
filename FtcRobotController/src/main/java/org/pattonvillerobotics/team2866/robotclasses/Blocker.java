package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by skeltonn on 11/21/15.
 * TODO: Find servo values
 * TODO: Create a control method similar to ZipRelease
 */
public class  Blocker {

    public Servo servoLeft;
    public Servo servoRight;
    private HardwareMap hardwareMap;
    private double upLeft = 0;
    private double upRight = 0;
    private double downLeft = 0;
    private double downRight = 0;

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
                servoLeft.setPosition(upLeft);
                servoRight.setPosition(upRight);
                break;
            case DOWN:
                servoLeft.setPosition(downLeft);
                servoRight.setPosition(downRight);
                break;
            default:
                throw new IllegalArgumentException("Direction must be UP or DOWN!");

        }
    }

}
