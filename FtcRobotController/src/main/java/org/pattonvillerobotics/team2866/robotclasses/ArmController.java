package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * Created by James McMahon on 10/20/15.
 */
public class ArmController {

    private HardwareMap hardwareMap;
    private DcMotor motorArm;

    public ArmController(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.motorArm = hardwareMap.dcMotor.get("motor_arm");
    }

    public void moveArm(double power) {
        motorArm.setPower(power);
    }

    public void stopArm() {
        motorArm.setPower(0);
    }

    @Override
    public String toString() {

        return "Arm Motor: " + motorArm.getPower();
    }
}
