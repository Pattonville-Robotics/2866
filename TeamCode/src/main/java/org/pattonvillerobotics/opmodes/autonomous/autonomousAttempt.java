package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups.;

@Autonomous(name = "AutoINSERTNAME", group = OpModeGroups.MAIN)


public class autonomousAttempt extends LinearOpMode{

    private

    @Override
    public void runOpMode() throws InterruptedException {
        init();


        telemetry.addData("//Autoname", "Initialization Complete");
        telemetry.update();

                waitForStart();

    }
}

