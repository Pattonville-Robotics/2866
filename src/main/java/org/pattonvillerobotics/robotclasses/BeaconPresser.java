package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by skaggsm on 11/10/16.
 */

@SuppressWarnings("PointlessArithmeticExpression")
public class BeaconPresser {
    private final Servo leftServo, rightServo;

    public BeaconPresser(HardwareMap hardwareMap) {
        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        setLeftServoDown();
        setRightServoDown();
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
        setLeftServoPosition(1);
        return this;
    }

    public BeaconPresser setLeftServoDown() {
        setLeftServoPosition(.5);
        return this;
    }

    public BeaconPresser setRightServoUp() {
        setRightServoPosition(1 - 1);
        return this;
    }

    public BeaconPresser setRightServoDown() {
        setRightServoPosition(1 - .5);
        return this;
    }
}
