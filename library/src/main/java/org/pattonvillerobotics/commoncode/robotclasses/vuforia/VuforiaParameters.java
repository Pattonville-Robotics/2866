package org.pattonvillerobotics.commoncode.robotclasses.vuforia;

import android.support.annotation.IdRes;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;
import java.util.List;

public class VuforiaParameters {

    private OpenGLMatrix phoneLocation;
    private List<OpenGLMatrix> trackableLocations;
    private int cameraMonitorViewId;
    private VuforiaLocalizer.CameraDirection cameraDirection;
    private String licenseKey;

    private VuforiaParameters(String licenseKey, VuforiaLocalizer.CameraDirection cameraDirection, OpenGLMatrix phoneLocation, List<OpenGLMatrix> trackableLocations, @IdRes int cameraMonitorViewId) {
        this.licenseKey = licenseKey;
        this.phoneLocation = phoneLocation;
        this.cameraDirection = cameraDirection;
        this.trackableLocations = trackableLocations;
        this.cameraMonitorViewId = cameraMonitorViewId;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public OpenGLMatrix getPhoneLocation() {
        return phoneLocation;
    }

    public List<OpenGLMatrix> getTrackableLocations() {
        return trackableLocations;
    }

    public VuforiaLocalizer.CameraDirection getCameraDirection() {
        return cameraDirection;
    }

    public int getCameraMonitorViewId() {
        return cameraMonitorViewId;
    }

    public static class Builder {
        private String licenseKey;
        private VuforiaLocalizer.CameraDirection cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        private OpenGLMatrix phoneLocation = createMatrix(0, 0, 0, AxesOrder.YZX, -90, 0, 0);
        private List<OpenGLMatrix> trackableLocations = new ArrayList<>();
        private
        @IdRes
        int cameraMonitorViewId = -1;

        public Builder() {
        }

        private OpenGLMatrix createMatrix(float x, float y, float z, AxesOrder o, float a, float b, float c) {
            return OpenGLMatrix
                    .translation(x, y, z)
                    .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, o, AngleUnit.DEGREES, a, b, c));
        }

        public Builder licenseKey(String licenseKey) {
            this.licenseKey = licenseKey;
            return this;
        }

        public Builder cameraDirection(VuforiaLocalizer.CameraDirection cameraDirection) {
            this.cameraDirection = cameraDirection;
            return this;
        }

        public Builder phoneLocation(float x, float y, float z, AxesOrder o, float a, float b, float c) {
            this.phoneLocation = createMatrix(x, y, z, o, a, b, c);
            return this;
        }

        /**
         * Should be called IN ORDER
         *
         * @param x
         * @param y
         * @param z
         * @param o
         * @param a
         * @param b
         * @param c
         * @return this
         */
        public Builder addTrackableLocation(float x, float y, float z, AxesOrder o, float a, float b, float c) {
            this.trackableLocations.add(createMatrix(x, y, z, o, a, b, c));
            return this;
        }

        public VuforiaParameters build() {
            if (trackableLocations.size() == 0) {
                OpenGLMatrix trackableOrientation = createMatrix(0, 0, 150, AxesOrder.XYZ, 90, 0, -90);
                trackableLocations.add(trackableOrientation);
            }
            if (licenseKey == null) {
                throw new IllegalArgumentException("Must provide a license key.");
            }
            return new VuforiaParameters(licenseKey, cameraDirection, phoneLocation, trackableLocations, cameraMonitorViewId);
        }

        public Builder cameraMonitorViewId(int cameraMonitorViewId) {
            this.cameraMonitorViewId = cameraMonitorViewId;
            return this;
        }
    }
}
