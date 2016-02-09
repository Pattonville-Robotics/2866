package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;

/**
 * Created by skaggsm on 1/28/16.
 */
public class SuperBlocker {

    public final Servo servoLeft;
    public final Servo servoRight;
    public final Servo servoVertical;
    private static final double VERTICAL_UP = .6;
    private static final double VERTICAL_MID = .325;
    private static final double VERTICAL_DOWN = .2;

    private static final double LEFT_DOWN = .31;
    private static final double LEFT_MID = .38;
    private static final double LEFT_UP = .58;

    private static final double RIGHT_DOWN = .65;
    private static final double RIGHT_MID = .57;
    private static final double RIGHT_UP = .35;
    private final HardwareMap hardwareMap;

    private Direction verticalPostiiton;
    private Direction horizontalPosition;


    public SuperBlocker(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.servoLeft = hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_LEFT);
        this.servoRight = hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_RIGHT);
        this.servoVertical = hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_VERTICAL);

        moveVertical(Direction.DOWN);
    }


    public void moveVertical(Direction direction) {
        switch (direction) {
            case UP:

                setPosition(Direction.MID);
                servoVertical.setPosition(VERTICAL_UP);
                break;
            case MID:

                servoVertical.setPosition(VERTICAL_MID);
                break;
            case DOWN:

                setPosition(Direction.MID);
                servoVertical.setPosition(VERTICAL_DOWN);
                break;
            default:
                throw new IllegalArgumentException("Direction must be UP, DOWN, MID!");

        }
        verticalPostiiton = direction;
    }

    public void setPosition(Direction direction) {

        if (verticalPostiiton == Direction.MID) {
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
