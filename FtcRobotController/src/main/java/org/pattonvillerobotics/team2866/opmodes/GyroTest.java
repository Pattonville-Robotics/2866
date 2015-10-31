package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.pattonvillerobotics.team2866.robotclasses.GyroHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kevin Stewart on 10/22/15.
 */

public class GyroTest extends OpMode {

    private GyroHelper gyroHelper;

    @Override
    public void init() {

        gyroHelper = new GyroHelper(hardwareMap);

    }

    /*
       * Code to run when the op mode is first enabled goes here
       * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
       */
    @Override
    public void init_loop() {
        double gyroOffset;

        gyroOffset = gyroHelper.calibrate();
        telemetry.addData("Initial Offset", gyroOffset);
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        double happy_angle;
        happy_angle = gyroHelper.getAngle();
        telemetry.addData("Happy Angle", happy_angle);
    }
}