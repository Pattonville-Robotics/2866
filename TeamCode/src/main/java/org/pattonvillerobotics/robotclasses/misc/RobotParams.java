package org.pattonvillerobotics.robotclasses.misc;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.PhoneOrientation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;
import org.pattonvillerobotics.enums.ArmType;

/**
 * Parameters of the robot to be
 * accessed from different parts
 * of the package.
 * @author Samuel Vaclavik
 */
public class RobotParams {

    public static RobotParameters setParams() {
        return new RobotParameters.Builder()
                .wheelRadius(2.136)
                .wheelBaseRadius(10)
                .driveGearRatio(2)
                .encodersEnabled(true)
                .rightDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
                .leftDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
                .gyroEnabled(true)
                .build();
    }

    public static PhoneOrientation setPhoneParams() {
        return PhoneOrientation.PORTRAIT_INVERSE;
    }

    public static VuforiaParameters setVuforiaParams() {
        return new VuforiaParameters.Builder()
                .phoneLocation(0, 0, 0, AxesOrder.XYZ, 90, -90, 0)
                .cameraDirection(VuforiaLocalizer.CameraDirection.BACK)
                .cameraMonitorViewId(R.id.cameraMonitorViewId)
                .licenseKey("AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm")
                .build();
    }

    public static ArmParameters setArmParameters() {
        return new ArmParameters.Builder()
                .numberOfActuaries(1)
                .numberOfServos(2)
                .numberOfMotors(1)
                .encodersEnabled(false)
                .motorGearRatio(3)
                .armType(ArmType.DYNAMIC)
                .build();
    }

}
