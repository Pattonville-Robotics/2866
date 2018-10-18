package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class hookLiftingMechanism extends AbstractMechanism {

    public DcMotor motor1;

    public DcMotor motor2;

    public hookLiftingMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2") ;

    }

    public  void lower() {

        motor1.setTargetPosition(1440*6);
        while(Math.abs(motor1.getCurrentPosition()-motor1.getTargetPosition()) >16 ) {
            motor1.setPower(0.7);
        }
        motor1.setPower(0);
    }

    public void raise() {

        motor1.setTargetPosition(1440*6);
        while(Math.abs(motor1.getCurrentPosition()-motor1.getTargetPosition()) >16 ) {
            motor1.setPower(-0.7);
        }
        motor1.setPower(0);

    }
}
