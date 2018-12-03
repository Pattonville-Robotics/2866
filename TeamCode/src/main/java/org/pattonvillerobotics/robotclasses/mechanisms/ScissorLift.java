package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * ScissorLift mechanism implementation.
 * @author Samuel Vaclavik
 */
public class ScissorLift extends AbstractMechanism {

    private DcMotor lifter;

    /**
     * Constrcutor for the <code>ScissorLift</code>.
     * @param linearOpMode  <code>LinearOpMode</code> of the calling process.
     * @param hardwareMap   <code>HardwareMap</code> for association with the right motor.
     */
    public ScissorLift(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        super(linearOpMode, hardwareMap);

        lifter = hardwareMap.dcMotor.get("scissor_lift");
        lifter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Sets the power of the motor controlling
     * the <code>ScissorLift</code>.
     * @param power Power at which the motor should operate.
     */
    public void setPower(double power) {
        lifter.setPower(power);
    }

}
