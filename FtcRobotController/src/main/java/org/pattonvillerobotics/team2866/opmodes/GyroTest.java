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

    private String startDate;
    private ElapsedTime runtime = new ElapsedTime();
    private GyroSensor gyro;
    private GyroHelper gyroHelper;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyroHelper.initialize(gyro);
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
        telemetry.addData("Angle", happy_angle);
    }
}