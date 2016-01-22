package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;
import org.pattonvillerobotics.team2866.robotclasses.GamepadData;
import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

/**
 * Created by Nathan Skelton on 10/25/15.
 * <p/>
 */
public class ClimberDumper implements Controllable {

    public static final double UP = 0.6;
    public static final double MID = 0.75;
    public static final double DOWN = 1;

    public Servo servoDumper;
    private boolean dumperDown;

    private HardwareMap hardwareMap;

    public ClimberDumper(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.servoDumper = this.hardwareMap.servo.get(Config.SERVO_DUMPER);
    }

    @Override
    public boolean sendGamepadData(GamepadData gamepad1DataCurrent, GamepadData gamepad1DataHistory, GamepadData gamepad2DataCurrent, GamepadData gamepad2DataHistory) {
        if (gamepad1DataCurrent.x && !gamepad1DataHistory.x) {
            if (dumperDown) {
                this.move(Direction.UP);
                dumperDown = false;
            } else {
                this.move(Direction.DOWN);
                dumperDown = true;
            }
        }
        return true;
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                servoDumper.setPosition(UP);
                break;
            case MID:
                servoDumper.setPosition(MID);
                break;
            case DOWN:
                servoDumper.setPosition(DOWN);
                break;
        }
    }

    @Override
    public GamepadFeature[] requestFeatures() {
        return new GamepadFeature[]{GamepadFeature.GAMEPAD_1_BUTTON_X};
    }
}