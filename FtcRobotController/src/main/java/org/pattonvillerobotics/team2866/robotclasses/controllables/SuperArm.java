package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.team2866.robotclasses.Config;

/**
 * Created by skaggsm on 3/1/16.
 */
public class SuperArm {
    private final DcMotor motorLift, motorRetract;

    public SuperArm(HardwareMap hardwareMap) {
        motorLift = hardwareMap.dcMotor.get(Config.MOTOR_ARM_LIFT);
        motorRetract = hardwareMap.dcMotor.get(Config.MOTOR_ARM_RETRACT);
    }

    public void setLiftPower(double power) {
        motorLift.setPower(power);
    }

    public void stopLift() {
        motorLift.setPower(0);
    }

    public void setRetractPower(double power) {
        motorRetract.setPower(power);
    }

    public void stopRetract() {
        motorRetract.setPower(0);
    }
}
