package org.pattonvillerobotics.team2866.opmodes.testing;

import android.app.Application;
import android.content.Context;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.team2866.robotclasses.OpMode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by skaggsm on 1/14/16.
 */
@OpMode("RPM Test")
public class RPMTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        try {
            FileOutputStream outputStream = getApplicationUsingReflection().openFileOutput("test.txt", Context.MODE_APPEND);
            outputStream.write("test".getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Application getApplicationUsingReflection() throws Exception {
        return (Application) Class.forName("android.app.ActivityThread")
                .getMethod("currentApplication").invoke(null, (Object[]) null);
    }
}
