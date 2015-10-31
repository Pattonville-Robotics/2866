package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * Created by James McMahon on 10/20/15.
 *
 *
 */
public class ArmController {

    private HardwareMap hardwareMap;
    private DcMotor motorArmRight;
    private DcMotor motorArmLeft;

    public ArmController(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.motorArmRight = hardwareMap.dcMotor.get(Config.motorArmRight);
        this.motorArmLeft = hardwareMap.dcMotor.get(Config.motorArmLeft);
    }

    public void moveArm(double power) {
        motorArmRight.setPower(power);
        motorArmLeft.setPower(power);
    }

    public void stopArm() {
        motorArmRight.setPower(0);
        motorArmLeft.setPower(0);
    }

    @Override
    public String toString() {

        return "Right Arm Motor:" + motorArmRight.getPower() + "Left Arm Motor:" + motorArmLeft.getPower();
    }
}
