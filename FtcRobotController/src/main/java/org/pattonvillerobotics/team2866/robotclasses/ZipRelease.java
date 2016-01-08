package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

/**
 * Created by James McMahon on 10/22/15.
 */
public class ZipRelease implements Controllable {

    private static final double LEFT_UP = 0;
    private static final double LEFT_DOWN = 1;
    private static final double RIGHT_UP = 1;
    private static final double RIGHT_DOWN = 0;
    public Servo servoReleaseLeft;
    public Servo servoReleaseRight;
    private HardwareMap hardwareMap;

    private Direction servoLeft;
    private Direction servoRight;

    private boolean leftReleaseDown;
    private boolean rightReleaseDown;

    public ZipRelease(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.servoReleaseLeft = hardwareMap.servo.get(Config.SERVO_RELEASE_LEFT);
        this.servoReleaseRight = hardwareMap.servo.get(Config.SERVO_RELEASE_RIGHT);
    }

    public String toString() {

        return "Left Release: " + servoLeft + "\n" +
                "Right Release: " + servoRight;
    }

    @Override
    public boolean sendGamepadData(GamepadData gamepad1DataCurrent, GamepadData gamepad1DataHistory, GamepadData gamepad2DataCurrent, GamepadData gamepad2DataHistory) {
        if (gamepad2DataCurrent.x && !gamepad2DataHistory.x) {
            if (leftReleaseDown) {
                this.moveLeft(Direction.UP);
                leftReleaseDown = false;
            } else {
                leftReleaseDown = true;
            }
            this.moveLeft(Direction.DOWN);
        }
        if (gamepad2DataCurrent.b && !gamepad2DataHistory.b) {
            if (rightReleaseDown) {
                this.moveRight(Direction.UP);
                rightReleaseDown = false;
            } else {
                this.moveRight(Direction.DOWN);
                rightReleaseDown = true;
            }
        }
        return true;
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
            default:
                throw new IllegalArgumentException("Direction must be UP or DOWN!");
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
            default:
                throw new IllegalArgumentException("Direction must be UP or DOWN!");
        }
    }

    @Override
    public GamepadFeature[] requestFeatures() {
        return new GamepadFeature[]{GamepadFeature.BUTTON_X, GamepadFeature.BUTTON_B};
    }
}