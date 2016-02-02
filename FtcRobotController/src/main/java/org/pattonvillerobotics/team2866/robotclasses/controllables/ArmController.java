package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by James McMahon on 10/20/15.
 * <p/>
 * TODO Add encoders to the arm motors
 */
@Deprecated
public class ArmController {

    public final DcMotor motorArmRight;
    public final DcMotor motorArmLeft;
    private final HardwareMap hardwareMap;
    private int target = 0;

    public ArmController(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        this.motorArmRight = null;//hardwareMap.dcMotor.get(Config.MOTOR_ARM_RIGHT);
        this.motorArmLeft = null;//hardwareMap.dcMotor.get(Config.MOTOR_ARM_LEFT);

        //this.motorArmRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        //this.motorArmLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        //this.motorArmRight.setDirection(DcMotor.Direction.REVERSE);
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
}
