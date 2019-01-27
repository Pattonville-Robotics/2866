package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.robotclasses.mechanisms.ArmUtilities.Joint;
import org.pattonvillerobotics.robotclasses.misc.ArmParameters;

import java.util.ArrayList;

/**
 * Abstract representation of an arm.
 * @author Samuel Vaclavik
 */
public abstract class AbstractArm extends AbstractMechanism {

    protected ArrayList<Joint> joints;
    protected final ArmParameters armParameters;

    /**
     * Constructor for the <code>AbstractArm</code> class.
     * @param linearOpMode  The <code>LinearOpMode</code> of the calling process.
     * @param hardwareMap   The <code>HardwareMap</code> of the calling process.
     * @param joints        The ArrayList of joints that coordinate to the arm's joints.
     * @param armParameters The parameters for the arm. (To be implemented)
     */
    public AbstractArm(LinearOpMode linearOpMode, HardwareMap hardwareMap, ArrayList<Joint> joints, ArmParameters armParameters) {
        super(linearOpMode, hardwareMap);
        this.armParameters = armParameters;
    }

    /**
     * Needed for rotation of the base. Not needed
     * if there is no waist to the arm.
     * @param degrees   The amount of degrees to rotate.
     */
    public abstract void rotateWaist(double degrees);

    /**
     * Flex the 'Bicep' of the arm.
     */
    public abstract void flexBicep(int millis);

    /**
     * Extend the 'Bicep' of the arm.
     */
    public abstract void extendBicep(double position);

    /**
     * Flex the 'Forearm' of the arm.
     */
    public abstract void flexForearm();

    /**
     * Extend the 'Forearm' of the arm.
     */
    public abstract void extendForearm();

    /**
     * Rotates the shoulder in a certain fashion
     * based on power input.
     * @param power The power to rotate the shoulder at.
     */
    public abstract void rotateShoulder(double power);
}
