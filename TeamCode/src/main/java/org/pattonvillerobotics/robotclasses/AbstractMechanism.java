package org.pattonvillerobotics.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AbstractMechanism {

    protected final HardwareMap hardwareMap;
    protected final LinearOpMode linearOpMode;

    public AbstractMechanism(HardwareMap, hardwareMap, LinearOpMode linearOpMode) {
        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
    }
}
