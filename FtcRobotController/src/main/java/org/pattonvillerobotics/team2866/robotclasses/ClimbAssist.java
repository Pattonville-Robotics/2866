package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Nathan Skelton on 10/19/15.
 */
public class ClimbAssist {

    private HardwareMap hardwareMap;
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorChain;

    public ClimbAssist(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        this.motorLeft = hardwareMap.dcMotor.get(Config.motorLiftLeft);
        this.motorRight = hardwareMap.dcMotor.get(Config.motorLiftRight);
        this.motorChain = hardwareMap.dcMotor.get(Config.motorChain);

        motorRight.setDirection(DcMotor.Direction.REVERSE);
    }

    public void moveLift(double power) {

        motorLeft.setPower(power);
        motorRight.setPower(power);
    }

    public void moveChain(double power) {

        motorChain.setPower(power);
    }

    public void stopLift() {

        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void stopChain() {

        motorChain.setPower(0);
    }

    @Override
    public String toString() {

        return "Lift Motors: " + motorLeft.getPower() + "\n" + "Chain Motor: " + motorChain.getPower();
    }
}