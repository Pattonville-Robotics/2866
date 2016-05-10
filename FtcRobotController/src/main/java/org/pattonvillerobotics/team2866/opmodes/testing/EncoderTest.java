package org.pattonvillerobotics.team2866.opmodes.testing;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

import org.pattonvillerobotics.commoncode.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.controllables.Drive;

/**
 * Created by skaggsm on 2/23/16.
 */
@OpMode("Encoder Test")
public class EncoderTest extends LinearOpMode {

    public static final String TAG = "DATA";

    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(this.hardwareMap, this);
        waitForStart();

        motorTest(drive.motorLeft, "Left Motor");
        motorTest(drive.motorRight, "Right Motor");
    }

    private void motorTest(DcMotor motor, String name) throws InterruptedException {
        Log.e(TAG, "----------------START {" + name + "} TEST----------------");
        motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        waitOneFullHardwareCycle();
        motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        waitOneFullHardwareCycle();

        int startPosition = motor.getCurrentPosition();

        Thread.sleep(100);
        Log.e(TAG, name + " starting encoder position: " + (motor.getCurrentPosition() - startPosition));
        Thread.sleep(100);

        motor.setDirection(DcMotor.Direction.REVERSE);
        waitOneFullHardwareCycle();
        motor.setPower(1);
        waitOneFullHardwareCycle();

        Thread.sleep(3000);

        motor.setPower(0);
        waitOneFullHardwareCycle();

        Thread.sleep(100);
        Log.e(TAG, name + " second encoder position: " + (motor.getCurrentPosition() - startPosition));
        Thread.sleep(100);

        motor.setDirection(DcMotor.Direction.FORWARD);
        waitOneFullHardwareCycle();
        motor.setPower(1);
        waitOneFullHardwareCycle();

        Thread.sleep(3000);

        motor.setPower(0);
        waitOneFullHardwareCycle();

        Thread.sleep(100);
        Log.e(TAG, name + " final encoder position: " + (motor.getCurrentPosition() - startPosition));
        Thread.sleep(100);
        Log.e(TAG, "----------------END {" + name + "} TEST----------------");
    }
}