package org.pattonvillerobotics.commoncode.robotclasses.opencv.relicrecovery.jewels;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.Contour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregbahr on 11/28/17.
 */

public class JewelAnalyzer {

    private JewelAnalyzer() {
    }

    /**
     * Uses the Douglas-Peucker algorithm to approximate shapes of the contours and finds the largest
     * circular contour.
     *
     * @param contours possible jewel contours
     * @return {@link Vector3D} containing the center coordinates and radius of the jewel.
     * @see <a href="https://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm">https://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm</a>
     */
    public static Vector3D analyzeFast(List<MatOfPoint> contours) {
        MatOfPoint2f approx = new MatOfPoint2f();

        ArrayList<MatOfPoint> filtered = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            double peri = Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true);
            Imgproc.approxPolyDP(new MatOfPoint2f(contour.toArray()), approx, 0.01 * peri, true);

            if (approx.total() > 8) {
                filtered.add(contour);
            }
        }
        MatOfPoint largest = Contour.findLargestContour(filtered);
        if (largest != null) {
            Point center = new Point();
            float[] radius = new float[1];
            Imgproc.minEnclosingCircle(new MatOfPoint2f(largest.toArray()), center, radius);
            return new Vector3D(center.x, center.y, radius[0]);
        }

        return null;
    }

    /**
     * Uses a Hough transform to find circular shapes on the threshold. Then checks for hough circles
     * and contours that overlap, as an overlapping circle and contour is likely to be a jewel.
     *
     * @param contours  the possible jewel contours
     * @param threshold the color threshold
     * @return a {@link Vector3D} containing the center coordinates and radius of the jewel.
     * @see <a href="https://en.wikipedia.org/wiki/Hough_transform">https://en.wikipedia.org/wiki/Hough_transform</a>
     */
    public static Vector3D analyzeComplex(List<MatOfPoint> contours, Mat threshold) {
        Mat circles = new Mat();
        Vector3D jewel = null;

        Mat upscaled = new Mat();

        Imgproc.pyrUp(threshold, upscaled);
        Imgproc.pyrUp(upscaled, upscaled);

        Imgproc.HoughCircles(upscaled, circles, Imgproc.HOUGH_GRADIENT, 3.5, 10000);

        for (int i = 0; i < circles.cols(); i++) {
            double[] circle = circles.get(0, i);

            for (MatOfPoint contour : contours) {
                if (Imgproc.pointPolygonTest(new MatOfPoint2f(contour.toArray()), new Point(circle[0], circle[1]), false) == 1) {
                    jewel = new Vector3D(circle);
                }
            }
        }
        if (jewel == null && Contour.findLargestContour(contours) != null) {
            Point center = Contour.centroid(Contour.findLargestContour(contours));
            jewel = new Vector3D(center.x, center.y, 20);
        }

        return jewel;
    }
}
