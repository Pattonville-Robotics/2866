package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Abstract representation of every possible mechanism.
 * @author Samuel Vaclavik
 */
public abstract class AbstractMechanism {

    protected final LinearOpMode linearOpMode;
    protected final HardwareMap hardwareMap;

    /**
     * Constructor for an <code>AbstractMechanism</code>.
     * @param linearOpMode  The <code>LinearOpMode</code> of the calling process.
     * @param hardwareMap   The <code>HardwareMap</code> of the calling process.
     */
    public AbstractMechanism(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        this.linearOpMode = linearOpMode;
        this.hardwareMap = hardwareMap;
    }

}
