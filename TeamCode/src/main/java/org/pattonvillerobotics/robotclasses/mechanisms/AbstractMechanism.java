package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class AbstractMechanism {

    protected final LinearOpMode linearOpMode;
    protected final HardwareMap hardwareMap;

    public AbstractMechanism(LinearOpMode linearOpMode, HardwareMap hardwareMap) {

        this.linearOpMode = linearOpMode;
        this.hardwareMap = hardwareMap;

    }

}
