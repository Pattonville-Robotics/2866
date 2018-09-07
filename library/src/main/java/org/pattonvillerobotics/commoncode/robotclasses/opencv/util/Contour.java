package org.pattonvillerobotics.commoncode.robotclasses.opencv.util;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.List;

public final class Contour {

    private Contour() {
    }

    public static MatOfPoint findLargestContour(List<MatOfPoint> contours) {
        double largestArea = 0;
        MatOfPoint largestContour = null;

        for (MatOfPoint contour : contours) {
            double contourArea = Imgproc.contourArea(contour);
            if (contourArea > largestArea) {
                largestArea = contourArea;
                largestContour = contour;
            }
        }

        return largestContour;
    }

    public static Point centroid(MatOfPoint contour) {
        Moments moments = Imgproc.moments(contour);
        return new Point(moments.get_m10() / moments.get_m00(), moments.get_m01() / moments.get_m00());
    }
}
