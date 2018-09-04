package org.pattonvillerobotics.commoncode.robotclasses.vuforia;

import android.graphics.Bitmap;

import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.List;

public class VuforiaNavigation {

    public static final String TAG = "VuforiaNavigation";
    public static final float MM_PER_INCH = 25.4f;

    private VuforiaTrackables trackables;
    private VuforiaLocalizer vuforia;
    private VuforiaLocalizer.Parameters parameters;
    private VuforiaTrackable relicTemplate;

    private OpenGLMatrix lastTrackedLocation;

    public VuforiaNavigation(VuforiaParameters parameters) {
        this.parameters = new VuforiaLocalizer.Parameters(parameters.getCameraMonitorViewId());
        this.parameters.cameraDirection = parameters.getCameraDirection();
        this.parameters.vuforiaLicenseKey = parameters.getLicenseKey();
        this.parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        this.parameters.useExtendedTracking = false;

        vuforia = ClassFactory.createVuforiaLocalizer(this.parameters);
        trackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = trackables.get(0);

        setTrackableLocation(parameters.getTrackableLocations());
        setPhoneLocation(parameters.getPhoneLocation());

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        vuforia.setFrameQueueCapacity(1);

        lastTrackedLocation = OpenGLMatrix
                .translation(0, 0, 0)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 0, 0, 0));
    }

    private void setTrackableLocation(List<OpenGLMatrix> locations) {
        for(int i = 0; i < locations.size(); i++) {
            trackables.get(i).setLocation(locations.get(i));
        }
    }

    private void setPhoneLocation(OpenGLMatrix location) {
        for(VuforiaTrackable trackable : trackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(location, parameters.cameraDirection);
        }
    }

    public void activateTracking() {
        if(trackables != null) trackables.activate();
    }

    public void deactivateTracking() {
        trackables.deactivate();
    }

    public VuforiaTrackables getTrackables() {
        return trackables;
    }

    private boolean trackableIsVisible(VuforiaTrackable trackable) {
        return ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible();
    }

    public RelicRecoveryVuMark getCurrentVisibleRelic() {
        return RelicRecoveryVuMark.from(relicTemplate);
    }

    public OpenGLMatrix getVisibleTrackableLocation() {
        for(VuforiaTrackable trackable : trackables) {
            if(trackableIsVisible(trackable)) {
                OpenGLMatrix trackedLocation = ((VuforiaTrackableDefaultListener) trackable.getListener()).getRobotLocation();
                if(trackedLocation != null) lastTrackedLocation = trackedLocation;
                return lastTrackedLocation;
            }
        }
        return null;
    }



    public double getRobotX() {
        VectorF translation = lastTrackedLocation.getTranslation();
        return translation.get(0) / MM_PER_INCH;
    }

    public double getRobotY() {
        VectorF translation = lastTrackedLocation.getTranslation();
        return translation.get(1) / MM_PER_INCH;
    }

    public double getDistanceFromTrackable() {
        return FastMath.hypot(getRobotX(), getRobotY());
    }

    /**
     * @return Angle between robot and x axis
     */
    public double getRobotBearing() {
        return Orientation.getOrientation(lastTrackedLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;
    }

    /**
     * @return Angle between x axis and line formed between robot and trackable
     */
    public double getTrackableBearing() {
        return FastMath.toDegrees(-FastMath.asin(getRobotY()/getDistanceFromTrackable()));
    }

    /**
     * @return angle robot needs to rotate to face trackable
     */
    public double getAngleToTrackable() {
        return getTrackableBearing() - getRobotBearing();
    }

    public Bitmap getImage() {
        VuforiaLocalizer.CloseableFrame frame = null;
        Image rgbImage = null;

        try {
            frame = new VuforiaLocalizer.CloseableFrame(vuforia.getFrameQueue().take());
            for(int i = 0; i < frame.getNumImages(); i++) {
                Image img = frame.getImage(i);
                if(img.getFormat() == PIXEL_FORMAT.RGB565) {
                    rgbImage = img;
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(frame != null) frame.close();
        if(rgbImage != null) {
            Bitmap bm = Bitmap.createBitmap(rgbImage.getWidth(), rgbImage.getHeight(), Bitmap.Config.RGB_565);
            bm.copyPixelsFromBuffer(rgbImage.getPixels());
            return bm;
        }
        return null;
    }
}
