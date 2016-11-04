package org.pattonvillerobotics.opmodes;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaParameters;

import static org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav.MM_PER_INCH;

/**
 * Created by skaggsm on 9/27/16.
 */

public class CustomizedRobotParameters {
    public static final RobotParameters ROBOT_PARAMETERS;
    public static final VuforiaParameters VUFORIA_PARAMETERS;

    static {
        ROBOT_PARAMETERS = new RobotParameters.Builder()
                .encodersEnabled(true)
                .gyroEnabled(false)
                .driveGearRatio(92 / 42d)
                .wheelBaseRadius(7.8306997409)
                .wheelRadius(1.55719275)
                .dcMotorMaxSpeed(RobotParameters.TICKS_PER_REVOLUTION * 2)
                .rightDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
                .leftDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
                .build();

        VUFORIA_PARAMETERS = new VuforiaParameters.Builder()
                .licenseKey("AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm")
                .cameraDirection(VuforiaLocalizer.CameraDirection.BACK)
                .phoneLocation(0 * MM_PER_INCH, 0 * MM_PER_INCH, 0 * MM_PER_INCH, AxesOrder.XYZ, 90, 0, 0)
                .addBeaconLocation(72 * MM_PER_INCH, -12 * MM_PER_INCH, 0 * MM_PER_INCH, AxesOrder.XYZ, 90, 0, -90) //Wheels
                .addBeaconLocation(36 * MM_PER_INCH, 72 * MM_PER_INCH, 0 * MM_PER_INCH, AxesOrder.XYZ, 90, 0, 0) //Tools
                .addBeaconLocation(72 * MM_PER_INCH, 36 * MM_PER_INCH, 0 * MM_PER_INCH, AxesOrder.XYZ, 90, 0, -90) //Legos
                .addBeaconLocation(-12 * MM_PER_INCH, 72 * MM_PER_INCH, 0 * MM_PER_INCH, AxesOrder.XYZ, 90, 0, 0) //Gears
                .cameraMonitorViewId(R.id.cameraMonitorViewId)
                .build();
    }
}