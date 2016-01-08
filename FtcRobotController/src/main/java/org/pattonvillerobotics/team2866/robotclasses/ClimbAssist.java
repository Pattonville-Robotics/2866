package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

/**
 * Created by Nathan Skelton on 10/19/15.
 */
public class ClimbAssist implements Controllable {

    public DcMotor motorLiftLeft;
    public DcMotor motorLiftRight;
    public DcMotor motorChain;
    private HardwareMap hardwareMap;

    public ClimbAssist(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.motorLiftLeft = hardwareMap.dcMotor.get(Config.MOTOR_LIFT_LEFT);
        this.motorLiftRight = hardwareMap.dcMotor.get(Config.MOTOR_LIFT_RIGHT);
        this.motorChain = hardwareMap.dcMotor.get(Config.MOTOR_CHAIN);

        motorLiftRight.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public String toString() {

        return "Lift Motors: " + motorLiftLeft.getPower() + "\n" + "Chain Motor: " + motorChain.getPower();
    }

    @Override
    public boolean sendGamepadData(GamepadData gamepad1DataCurrent, GamepadData gamepad1DataHistory, GamepadData gamepad2DataCurrent, GamepadData gamepad2DataHistory) {
        if (gamepad1DataCurrent.y && !gamepad1DataCurrent.a) {
            this.moveLift(Config.LIFT_MOVEMENT_SPEED);
        } else if (gamepad1DataCurrent.a && !gamepad1DataCurrent.y) {
            this.moveLift(-Config.LIFT_MOVEMENT_SPEED);
        } else {
            this.stopLift();
        }
        if (gamepad1DataCurrent.right_bumper && !gamepad1DataCurrent.left_bumper) {
            this.moveChain(Config.CHAIN_MOVEMENT_SPEED);
        } else if (gamepad1DataCurrent.left_bumper && !gamepad1DataCurrent.right_bumper) {
            this.moveChain(-Config.CHAIN_MOVEMENT_SPEED);
        } else {
            this.stopChain();
        }
        return true;
    }

    public void moveLift(double power) {

        motorLiftLeft.setPower(power);
        motorLiftRight.setPower(power);
    }

    public void stopLift() {

        motorLiftLeft.setPower(0);
        motorLiftRight.setPower(0);
    }

    public void moveChain(double power) {

        motorChain.setPower(power);
    }

    public void stopChain() {

        motorChain.setPower(0);
    }

    @Override
    public GamepadFeature[] requestFeatures() {
        return new GamepadFeature[]{GamepadFeature.BUTTON_A, GamepadFeature.BUTTON_Y, GamepadFeature.BUMPER_RIGHT, GamepadFeature.BUMPER_LEFT};
    }
}