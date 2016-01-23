package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.GamepadData;
import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

/**
 * Created by skeltonn on 11/21/15.
 * TODO: Find servo values
 * TODO: Create a control method similar to ZipRelease
 */
public class Blocker implements Controllable {

    private static final double UP_LEFT = 1 - .3;
    private static final double UP_RIGHT = 0 + .3;
    private static final double DOWN_LEFT = 0 + .05;
    private static final double DOWN_RIGHT = 1 - .05;
    public final Servo servoLeft;
    public final Servo servoRight;
    private final HardwareMap hardwareMap;
    private final Direction currentDirection;

    public Blocker(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.servoLeft = hardwareMap.servo.get(Config.SERVO_BLOCKER_LEFT);
        this.servoRight = hardwareMap.servo.get(Config.SERVO_BLOCKER_RIGHT);
        this.move(Direction.DOWN);
        this.currentDirection = Direction.DOWN;
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

    public void toggle() {
        if (currentDirection == Direction.DOWN)
            this.move(Direction.UP);
        else
            this.move(Direction.DOWN);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public boolean sendGamepadData(GamepadData gamepad1DataCurrent, GamepadData gamepad1DataHistory, GamepadData gamepad2DataCurrent, GamepadData gamepad2DataHistory) {

        if (gamepad1DataCurrent.left_trigger > .5 && !(gamepad1DataHistory.left_trigger > .5)) {
            this.move(Direction.UP);
        } else if (gamepad1DataCurrent.right_trigger > .5 && !(gamepad1DataHistory.left_trigger > .5)) {
            this.move(Direction.DOWN);
        }
        return true;
    }

    @Override
    public GamepadFeature[] requestFeatures() {
        return new GamepadFeature[]{GamepadFeature.GAMEPAD_1_TRIGGER_LEFT, GamepadFeature.GAMEPAD_1_TRIGGER_RIGHT};
    }
}
