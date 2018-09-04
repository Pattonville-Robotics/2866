package org.pattonvillerobotics.commoncode.robotclasses.opencv.util;

/**
 * Created by greg on 10/29/2017.
 */

public enum PhoneOrientation {
    LANDSCAPE(0),
    PORTRAIT(270),
    LANDSCAPE_INVERSE(180),
    PORTRAIT_INVERSE(90);

    public final double rotation;

    PhoneOrientation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }
}
