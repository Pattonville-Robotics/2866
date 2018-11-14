package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LunEx extends AbstractMechanism {

    private Servo waist, elbow, wrist;
    private DcMotor shoulder;

    public LunEx(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        super(linearOpMode, hardwareMap);

        waist = hardwareMap.servo.get("waist");
        elbow = hardwareMap.servo.get("elbow");
        wrist = hardwareMap.servo.get("wrist");
        shoulder = hardwareMap.dcMotor.get("shoulder");

    }

    public void rotateWaist(double degrees, double power) {

    }

}
