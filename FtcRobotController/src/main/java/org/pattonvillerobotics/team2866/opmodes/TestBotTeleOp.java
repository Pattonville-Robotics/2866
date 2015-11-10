package org.pattonvillerobotics.team2866.opmodes;

import org.pattonvillerobotics.team2866.robotclasses.ArmController;
import org.pattonvillerobotics.team2866.robotclasses.ClimbAssist;
import org.pattonvillerobotics.team2866.robotclasses.ClimberDumper;
import org.pattonvillerobotics.team2866.robotclasses.Drive;
import org.pattonvillerobotics.team2866.robotclasses.OpMode;
import org.pattonvillerobotics.team2866.robotclasses.ZipRelease;

/**
 * Created by stewartk02 on 11/5/15.
 */
@org.pattonvillerobotics.team2866.robotclasses.OpMode("TeleOp")
public class TestBotTeleOp extends OpMode {

    public static final String TAG = "TeleOp";
    private Drive drive;
    private ClimbAssist climbAssist;
    private ArmController armController;
    private ZipRelease zipRelease;
    private ClimberDumper climberDumper;

    private boolean leftReleaseDown = false;
    private boolean leftReleaseTriggered = false;
    private boolean rightReleaseDown = false;
    private boolean rightReleaseTriggered = false;

    private boolean dumperTriggered = false;
    private boolean dumperDown = false;

    public void init() {

        drive = new Drive(hardwareMap, null);
        climbAssist = new ClimbAssist(hardwareMap);
        armController = new ArmController(hardwareMap);
        zipRelease = new ZipRelease(hardwareMap);
        climberDumper = new ClimberDumper(hardwareMap);

        gamepad1.setJoystickDeadzone(0.05f);
        gamepad2.setJoystickDeadzone(0.05f);
    }

    public void loop() {
        float right = gamepad1.right_stick_y;
        float left = gamepad1.left_stick_y;

        //TODO: Establish control for each stick correlating to a motor
        //TODO: Establish
    }
}
