package org.pattonvillerobotics.commoncode.robotclasses;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.ColorSensorColor;

/**
 * Created by Josh Zahner on 9/10/16.
 */

/**
 * This extension on the typical ColorSensor class includes methods and functions to determine
 * the dominant color found by a color sensor, and act upon that value given a color it should
 * be look for.
 *
 * @see ColorSensor
 * @see ColorSensorColor
 */
public class BeaconColorSensor {

    public ColorSensor colorSensor;

    /**
     * sets up a new BeaconColorSensor with a color sensor objects and disables the LED to
     * enable passive mode for reading of the beacon.
     *
     * @param colorSensor a color sensor object from which to get data
     */
    public BeaconColorSensor(ColorSensor colorSensor) {

        this.colorSensor = colorSensor;
        this.colorSensor.enableLed(false);

    }

    public BeaconColorSensor(HardwareMap hardwareMap) {
        this(hardwareMap.colorSensor.get("color_sensor"));
    }

    /**
     * converts a ColorSensorColor to an AllianceColor
     *
     * @param colorSensorColor the ColorSensorColor to convert
     * @return the corresponding AllianceColor
     * @see ColorSensorColor
     * @see AllianceColor
     */
    public static AllianceColor toAllianceColor(ColorSensorColor colorSensorColor) {
        switch (colorSensorColor) {
            case RED:
                return AllianceColor.RED;
            case BLUE:
                return AllianceColor.BLUE;
            default:
                throw new IllegalArgumentException("ColorSensorColor must be blue or red in order to convert to AllianceColor!");
        }
    }

    /**
     * reads the color of the beacon's right side then acts on that value given the alliance
     * color, and thus the color it should be looking for
     *
     * @param allianceColor the alliance color, which dictates the color the robot
     *                      should be looking for
     * @param ifPositiveID  the function to run if a positive color ID is found on the
     *                      right side of the beacon
     * @param ifNegativeID  the functions to run if a negative color ID is found on the
     *                      right side of the beacon
     * @param ifNeither     the function to run if the color sensor can not find a color
     */
    public void determineColor(AllianceColor allianceColor, Runnable ifPositiveID, Runnable ifNegativeID, Runnable ifNeither) {

        ColorSensorColor dominantColor = dominantColor();

        switch (allianceColor) {
            case BLUE:

                if (dominantColor == ColorSensorColor.BLUE) {
                    ifPositiveID.run();
                } else if (dominantColor == ColorSensorColor.RED) {
                    ifNegativeID.run();
                } else {
                    ifNeither.run();
                }

                break;

            case RED:

                if (dominantColor == ColorSensorColor.RED) {
                    ifPositiveID.run();
                } else if (dominantColor == ColorSensorColor.BLUE) {
                    ifNegativeID.run();
                } else {
                    ifNeither.run();
                }

                break;
        }

    }

    /**
     * gets the red color found by the color sensor
     *
     * @return the red value
     */
    public int red() {
        return colorSensor.red();
    }

    /**
     * gets the green color found by the color sensor
     *
     * @return the green value
     */
    public int green() {
        return colorSensor.green();
    }

    /**
     * gets the blue color found by the color sensor
     *
     * @return the blue value
     */
    public int blue() {
        return colorSensor.blue();
    }

    /**
     * gets the alpha color found by the color sensor
     *
     * @return the alpha value
     */
    public int alpha() {
        return colorSensor.alpha();
    }

    /**
     * determines the dominant color found by the color sensor on one side of the beacon
     *
     * @return the dominant color
     * @see ColorSensorColor
     */
    public ColorSensorColor dominantColor() {

        int blue = blue(), red = red(), green = green();

        if (blue > red && blue > green) {
            return ColorSensorColor.BLUE;

        } else if (red > blue && red > green) {
            return ColorSensorColor.RED;

        } else if (green > blue && green > red) {
            return ColorSensorColor.GREEN;

        } else {
            return null;
        }
    }
}
