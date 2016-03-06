package org.pattonvillerobotics.team2866.robotclasses.controllables;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.team2866.robotclasses.Config;
import org.pattonvillerobotics.team2866.robotclasses.Direction;

/**
 * Created by Nathan Skelton on 10/25/15.
 * <p/>
 */
public class ClimberDumper {

    public static final double UP = 0.41;
    public static final double MID = 0.75;
    public static final double DOWN = .94;

    public final Servo servoDumper;
    private final HardwareMap hardwareMap;
    private boolean dumperDown;

    public ClimberDumper(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.servoDumper = this.hardwareMap.servo.get(Config.SERVO_DUMPER);
        this.move(Direction.DOWN);
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                servoDumper.setPosition(UP);
                break;
            case MID:
                servoDumper.setPosition(MID);
                break;
            case DOWN:
                servoDumper.setPosition(DOWN);
                break;
        }
    }
}