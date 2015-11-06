package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.pattonvillerobotics.team2866.robotclasses.OpMode;

/**
 * Created by mcmahonj on 10/29/15.
 */
@OpMode("Test Bot")
public class TestBotOp extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("left");
        motorRight = hardwareMap.dcMotor.get("right");

        waitForStart();

        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(1000);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}
