package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by skaggsm on 11/10/16.
 */

public class BeaconPresser {
    private Servo leftServo, rightServo;

    public BeaconPresser(HardwareMap hardwareMap) {
        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        setLeftServoUp();
        setRightServoUp();
    }

    public BeaconPresser setLeftServoPosition(double position) {
        leftServo.setPosition(position);
        return this;
    }

    public BeaconPresser setRightServoPosition(double position) {
        rightServo.setPosition(position);
        return this;
    }

    public BeaconPresser setLeftServoUp() {
        setLeftServoPosition(0);
        return this;
    }

    public BeaconPresser setLeftServoDown() {
        setLeftServoPosition(1);
        return this;
    }

    public BeaconPresser setRightServoUp() {
        setRightServoPosition(0);
        return this;
    }

    public BeaconPresser setRightServoDown() {
        setRightServoPosition(1);
        return this;
    }
}
