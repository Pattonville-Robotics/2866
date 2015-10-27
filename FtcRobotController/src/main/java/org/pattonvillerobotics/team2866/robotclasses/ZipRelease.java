package org.pattonvillerobotics.team2866.robotclasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by James McMahon on 10/22/15.
 *
 */
public class ZipRelease {

    private HardwareMap hardwareMap;

    private Servo servoLeft;
    private Servo servoRight;

    private static final double LEFT_UP = 0;
    private static final double LEFT_DOWN = 0;
    private static final double RIGHT_UP = 0;
    private static final double RIGHT_DOWN = 0;


    public ZipRelease(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
    }

    public void moveLeft(DirectionEnum direction) {
        servoLeft.setPosition(LEFT_UP);
        servoLeft.setPosition(LEFT_DOWN);
    }

    public void moveRight(DirectionEnum direction) {
        servoRight.setPosition(RIGHT_UP);
        servoRight.setPosition(RIGHT_DOWN);
    }
}