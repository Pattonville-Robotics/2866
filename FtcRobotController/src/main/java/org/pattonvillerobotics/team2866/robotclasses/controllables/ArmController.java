package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.GamepadData;
import org.pattonvillerobotics.team2866.robotclasses.controller.GamepadFeature;

/**
 * Created by James McMahon on 10/20/15.
 * <p/>
 * TODO Add encoders to the arm motors
 */
public class ArmController implements Controllable {

    public DcMotor motorArmRight;
    public DcMotor motorArmLeft;
    private HardwareMap hardwareMap;
    private int target = 0;

    public ArmController(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        this.motorArmRight = hardwareMap.dcMotor.get(Config.MOTOR_ARM_RIGHT);
        this.motorArmLeft = hardwareMap.dcMotor.get(Config.MOTOR_ARM_LEFT);

        //this.motorArmRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        //this.motorArmLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        this.motorArmRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public void advanceArm(int deltaTarget) {
        target += deltaTarget;
        motorArmRight.setTargetPosition(target);
        motorArmLeft.setTargetPosition(target);
    }

    @Override
    public String toString() {

        return "Right Arm Motor:" + motorArmRight.getPower() + "Left Arm Motor:" + motorArmLeft.getPower();
    }

    @Override
    public boolean sendGamepadData(GamepadData gamepad1DataCurrent, GamepadData gamepad1DataHistory, GamepadData gamepad2DataCurrent, GamepadData gamepad2DataHistory) {
        if (gamepad1DataCurrent.dpad_up && !gamepad1DataCurrent.dpad_down) {
            this.moveArm(.75);
            //armController.advanceArm(Config.ARM_MOVEMENT_SPEED);
        } else if (!gamepad1DataCurrent.dpad_up && gamepad1DataCurrent.dpad_down) {
            this.moveArm(-.75);
            //armController.advanceArm(-Config.ARM_MOVEMENT_SPEED);
        } else {
            this.stopArm();
        }
        return true;
    }

    @Deprecated
    public void moveArm(double power) {
        motorArmRight.setPower(power);
        motorArmLeft.setPower(power);
    }

    @Deprecated
    public void stopArm() {
        //Motors will try to remain in the correct position, stopping is bad.

        motorArmRight.setPower(0);
        motorArmLeft.setPower(0);
    }

    @Override
    public GamepadFeature[] requestFeatures() {
        return new GamepadFeature[]{GamepadFeature.GAMEPAD_1_BUTTON_A, GamepadFeature.GAMEPAD_1_BUTTON_Y};
    }
}
