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
    private final double verticalUP = 1;
    private final double verticalMID = .7;
    private final double verticalDOWN = .6;
    private final double leftUP = .33;
    private final double leftMID = .43;
    private final double leftDOWN = .6;
    private final double rightUP = .6;
    private final double rightMID = .52;
    private final double rightDOWN = .35;
    private final HardwareMap hardwareMap;

    private Direction verticalPostiiton;
    private Direction horizontalPosition;


    public SuperBlocker(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.servoLeft = hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_LEFT);
        this.servoRight = hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_RIGHT);
        this.servoVertical = hardwareMap.servo.get(Config.SERVO_SUPERBLOCKER_VERTICAL);

        moveVertical(Direction.MID);

        setPosition(Direction.DOWN);
        verticalPostiiton = Direction.DOWN;
        horizontalPosition = Direction.MID;
    }


    public void moveVertical(Direction direction) {
        switch (direction) {
            case UP:

                setPosition(Direction.MID);
                servoVertical.setPosition(verticalUP);
                break;
            case MID:

                servoVertical.setPosition(verticalMID);
                break;
            case DOWN:

                setPosition(Direction.MID);
                servoVertical.setPosition(verticalDOWN);
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

                    servoLeft.setPosition(leftUP);
                    servoRight.setPosition(rightUP);
                    break;
                case MID:

                    servoLeft.setPosition(leftMID);
                    servoRight.setPosition(rightMID);
                    break;
                case DOWN:

                    servoLeft.setPosition(leftDOWN);
                    servoRight.setPosition(rightDOWN);
                    break;
                default:
                    throw new IllegalArgumentException("Direction must be UP, DOWN, MID!");
            }
            horizontalPosition = direction;
        } else {
            servoLeft.setPosition(leftMID);
            servoRight.setPosition(rightMID);
            horizontalPosition = Direction.MID;
        }
    }
}
