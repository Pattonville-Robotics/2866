package org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer;

import org.apache.commons.math3.filter.MeasurementModel;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.CholeskyDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.FastMath;

/**
 * Created by skaggsm on 10/25/16.
 */

public class KalmanFilter {

    private RealMatrix F; // Current state transition matrix
    private RealMatrix Ft; //F transpose
    private RealVector x; // Current state vector
    private RealMatrix P; // error covariance matrix
    private RealMatrix processNoise;

    public KalmanFilter(RealMatrix processNoise, RealVector initialStateEstimate) {
        this.processNoise = processNoise;
        setStateTransitionMatrix(0);
        x = initialStateEstimate;
        P = new Array2DRowRealMatrix(new double[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
        });
    }

    public static RealMatrix getStateTransitionMatrix(double dt) {
        double dt2 = FastMath.pow(dt, 2) / 2;
        return new Array2DRowRealMatrix(new double[][]{
                {1, 0, dt, 0, dt2, 0, 0, 0},
                {0, 1, 0, dt, 0, dt2, 0, 0},
                {0, 0, 1, 0, dt, 0, 0, 0},
                {0, 0, 0, 1, 0, dt, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, dt},
                {0, 0, 0, 0, 0, 0, 0, 1}
        });
    }

    public RealVector predictNextState(final double dt) {
        setStateTransitionMatrix(dt);

        x = F.operate(x); // B_t * u_t is ignored since no point to control data
        P = F.multiply(P).multiply(Ft).add(processNoise);

        return x;
    }

    private void setStateTransitionMatrix(final double dt) {
        F = getStateTransitionMatrix(dt);
        Ft = F.transpose();
    }

    public RealVector getCurrentState() {
        return x;
    }

    /**
     * Not thread-safe! Synchronize on the object for safety.
     *
     * @param measurementModel contains the matrix that maps the state TO THE measurement of the state
     * @param z                the measurement of the state
     * @return The new state
     */

    public RealVector measureAndGetState(MeasurementModel measurementModel, RealVector z) {
        /*
        RealMatrix H = measurementModel.getMeasurementMatrix();
        RealMatrix Ht = H.transpose();
        RealMatrix PHt = P.multiply(Ht);

        RealVector y = z.subtract(H.operate(x));

        //RealMatrix S = H.multiply(PHt).add(measurementModel.getMeasurementNoise());
        RealMatrix S = H.multiply(P).multiply(Ht).add(measurementModel.getMeasurementNoise());
        RealMatrix Si = new LUDecomposition(S).getSolver().getInverse();

        RealMatrix K = PHt.multiply(Si);

        x = x.add(K.operate(y));
        P = P.subtract(K.multiply(H).multiply(P));
        */

        RealMatrix measurementMatrix = measurementModel.getMeasurementMatrix();

        // S = H * P(k) * H' + R
        RealMatrix innovationCovariance = measurementMatrix.multiply(P)
                .multiply(measurementMatrix.transpose())
                .add(measurementModel.getMeasurementNoise());

        // Inn = z(k) - H * xHat(k)-
        RealVector innovation = z.subtract(measurementMatrix.operate(x));

        // calculate gain matrix
        // K(k) = P(k)- * H' * (H * P(k)- * H' + R)^-1
        // K(k) = P(k)- * H' * S^-1

        // instead of calculating the inverse of S we can rearrange the formula,
        // and then solve the linear equation A x X = B with A = S', X = K' and B = (H * P)'

        // K(k) * S = P(k)- * H'
        // S' * K(k)' = H * P(k)-'
        RealMatrix kalmanGain = new CholeskyDecomposition(innovationCovariance).getSolver()
                .solve(measurementMatrix.multiply(P.transpose()))
                .transpose();

        // update estimate with measurement z(k)
        // xHat(k) = xHat(k)- + K * Inn
        x = x.add(kalmanGain.operate(innovation));

        // update covariance of prediction error
        // P(k) = (I - K * H) * P(k)-
        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(kalmanGain.getRowDimension());
        P = identity.subtract(kalmanGain.multiply(measurementMatrix)).multiply(P);

        return x;
    }

}
