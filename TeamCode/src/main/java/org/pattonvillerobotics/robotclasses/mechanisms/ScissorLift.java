package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ScissorLift extends AbstractMechanism {

    private DcMotor lifter;

    public ScissorLift(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        super(linearOpMode, hardwareMap);

        lifter = hardwareMap.dcMotor.get("scissor_lift");
        lifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setPower(double power) {
        lifter.setPower(power);
    }

}
