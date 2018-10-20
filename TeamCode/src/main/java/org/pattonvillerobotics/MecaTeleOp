package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

@TeleOp(name="MecaTeleOp", group=OpModeGroups.MAIN)
public class MecaTeleOp  extends LinearOpMode {

    private RobotParameters parameters = RobotParams.setParams();
    private MecanumEncoderDrive drive;
    private ListenableGamepad gamepad;
    private boolean fieldOrientedDriveMode;

    @Override
    public void runOpMode() throws InterruptedException {

        Vector2D polarCoordinates;

        waitForStart();

        while(opModeIsActive()) {

            polarCoordinates = toPolar(gamepad1.left_stick_x, -gamepad1.left_stick_y);

        }
    }

    private void initialize() {
        gamepad = new ListenableGamepad();

        gamepad.addButtonListener(GamepadData.Button.STICK_BUTTON_LEFT, ListenableButton.ButtonState.JUST_PRESSED, () -> fieldOrientedDriveMode = !fieldOrientedDriveMode);
    }

    public Vector2D toPolar(double x, double y) {
        return new Vector2D(FastMath.hypot(x, y), FastMath.atan2(y, x));
    }
}
