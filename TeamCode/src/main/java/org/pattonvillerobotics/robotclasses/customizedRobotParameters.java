package org.pattonvillerobotics.robotclasses;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.PhoneOrientation;

public class customizedRobotParameters {
    public static final customizedRobotParameters
    public static final PhoneOrientation PHONE_ORIENTATION
    public static final VuforiaParameters

    static {
        PHONE_ORIENTATION = PhoneOrientation.PORTRAIT_INVERSE;

        ROBOT_PARAMETERS = new RobotParameters.Builder()
                .wheelBaseRadius(15)
                .encodersEnabled(true)
                .wheelRadius(2)
                .gyroEnabled(true)
                .driveGearRatio(2)
                .build();

        VUFORIA_PARAMETERS = new VuforiaParameters.Builder()
                .phoneLocation( x: 0, y: 0, z: 0, AxesOrder.XYZ, a: 90 b: -90 c: 0)
                .cameraDirection(VufoiaLocalizer.CaermaDirection.BACK)
                .cameraMonitorViewID(R.id.cameraMonitorViewId)
                .licenseKey("INSERTLICENSEKEYHERE")
                .build;
    }
}
