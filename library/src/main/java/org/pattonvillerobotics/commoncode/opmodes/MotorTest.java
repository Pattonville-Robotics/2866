package org.pattonvillerobotics.commoncode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by skaggsw on 9/5/17.
 */
@Autonomous(name="MotorTest")
public class MotorTest extends LinearOpMode {

    public DcMotor testMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        testMotor = hardwareMap.dcMotor.get("test_motor");

        testMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        testMotor.setPower(1);

        sleep(2000);

        testMotor.setPower(0);

        testMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        testMotor.setPower(1);

        sleep(2000);

        testMotor.setPower(0);

    }
}
