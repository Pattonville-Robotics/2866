package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by skaggsm on 1/12/17.
 */

public class LinearSlides {

    private final DcMotor bottomMotor, topMotor;

    public LinearSlides(HardwareMap hardwareMap) {
        bottomMotor = hardwareMap.dcMotor.get("bottom_motor");
        topMotor = hardwareMap.dcMotor.get("top_motor");
    }

    public void setTopMotorPower(double power) {
        topMotor.setPower(power);
    }

    public void setBottomMotorPower(double power) {
        bottomMotor.setPower(power);
    }

    public void stopMotors() {
        setTopMotorPower(0);
        setBottomMotorPower(0);
    }
}
