package org.pattonvillerobotics.commoncode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by skaggsw on 9/7/17.
 */
@Autonomous(name="EncoderTest")
public class EncoderTest extends LinearOpMode {

    public DcMotor testMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        testMotor = hardwareMap.dcMotor.get("test_motor");

        testMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        testMotor.setTargetPosition(3000);

        waitForStart();

        testMotor.setPower(0.5);

        while (testMotor.isBusy() && !this.isStopRequested() && this.opModeIsActive()) {
            idle();
        }

        testMotor.setTargetPosition(0);

        while (testMotor.isBusy() && !this.isStopRequested() && this.opModeIsActive()) {
            idle();
        }

        testMotor.setPower(0);

    }

}
