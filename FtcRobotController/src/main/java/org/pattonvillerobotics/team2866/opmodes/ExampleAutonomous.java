package org.pattonvillerobotics.team2866.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by skaggsm on 10/22/15.
 * <p/>
 * This class is designed to teach how to use each part of the robot.
 */
public class
        ExampleAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo1 = hardwareMap.servo.get("servo_1");
        DcMotor motor1 = hardwareMap.dcMotor.get("motor_1");
        DcMotorController motorController1 = hardwareMap.dcMotorController.get("motor_controller_1");
        GyroSensor gyroSensor = hardwareMap.gyroSensor.get("gyro_1");

        motor1.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        motor1.setTargetPosition(1440);
        motor1.getChannelMode().equals(DcMotorController.RunMode.RUN_TO_POSITION);
    }
}