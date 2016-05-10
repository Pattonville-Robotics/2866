package org.pattonvillerobotics.team2866.opmodes.testing;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.OpMode;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by skaggsm on 1/14/16.
 */
@OpMode("RPM Test")
public class RPMTest extends LinearOpMode {
    public static final String TAG = RPMTest.class.getName();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.setTag(TAG);
        telemetry.addData("RPMTest", "Starting write");
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/test.txt");
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write("test".getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception e) {
            telemetry.addData("RPMTest", "Failed to write file!");
            throw new AssertionError(e);
        }
        telemetry.addData("RPMTest", "Finished write");
    }
}
