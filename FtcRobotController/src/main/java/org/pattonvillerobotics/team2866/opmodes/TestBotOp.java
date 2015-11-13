package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.pattonvillerobotics.team2866.robotclasses.ArmController;
import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.ZipRelease;

/**
 * Created by mcmahonj on 10/29/15.
 */
@OpMode("Test Bot")
public class TestBotOp extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;

    private Drive drive;
    private ClimbAssist climbAssist;
    private ArmController armController;
    private ZipRelease zipRelease;
    private ClimberDumper climberDumper;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("left");
        motorRight = hardwareMap.dcMotor.get("right");

        float right = gamepad1.right_stick_y;
        float left = gamepad1.left_stick_y;


        waitForStart();

        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);
        sleep(1000);
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}
