package org.pattonvillerobotics.team2866.robotclasses;

import android.widget.Button;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by skeltonn on 11/21/15.
 * TODO: Find servo values
 * TODO: Create a control method similar to ZipRelease
 */
public class  Blocker {

    private HardwareMap hardwareMap;

    public Servo servoLeft;
    public Servo servoRight;

    private double upLeft = 0;
    private double upRight = 0;
    private double downLeft = 0;
    private double downRight = 0;

    public Blocker(HardwareMap hardwareMap) {

        this.hardwareMap = hardwareMap;
        servoLeft = hardwareMap.servo.get(Config.SERVO_BLOCKER_LEFT);
        servoRight = hardwareMap.servo.get(Config.SERVO_BLOCKER_RIGHT);
    }

}
