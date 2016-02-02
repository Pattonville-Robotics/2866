package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.team2866.robotclasses.Config;

/**
 * Created by Nathan Skelton on 10/19/15.
 */
public class ClimbAssist {

    public final DcMotor motorLiftLeft;
    public final DcMotor motorLiftRight;
    public final DcMotor motorChain;
    private final HardwareMap hardwareMap;

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
}