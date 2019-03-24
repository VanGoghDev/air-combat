package entities;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.linear.RealVector;

public class Missile extends DynamicEntity {
    private double[] initialState;
    private double x[];
    private double[] targetsX;

    public void setTargetsX(double[] targetsX) {
        this.targetsX = targetsX;
    }

    private static int DIMENSION = 7;

    public double getInitialState(int i) {
        return initialState[i];
    }

    public Missile() {
    }

    public Missile(double[] initialState) {
        this.initialState = initialState;
    }

    public int getDimension() {
        return 0;
    }

    @Override
    public Double[] getRight(double[] x, int j) {
        Double xDot[] = super.getRight(x, j);
        int count;
        if (j == 0) {
            count = 0;
        } else {
            count = j * DIMENSION;
        }

        double mLa = 100;
        double kT = 100;
        double deltaM = 0;  // fuel consumption su-35

        double psi = Math.atan((targetsX[2] - x[count + 2])/ (targetsX[0] - x[count]));
        double epsilon = Math.atan(Math.sqrt(Math.pow((targetsX[0] - x[count]), 2) + Math.pow((targetsX[2] - x[count + 2]), 2)) / (targetsX[1] - x[count + 1]));
        double b = kT * (deltaM) / mLa;

        xDot[3] = b * Math.cos(psi) * Math.cos(epsilon) - b * Math.sin(epsilon) + b * Math.cos(epsilon) * Math.sin(psi);
        xDot[4] = b * 2 * Math.cos(epsilon) + b * Math.sin(epsilon) * Math.sin(psi);
        xDot[5] = b * 2 * Math.cos(psi);
        return xDot;
    }

    /* @Override
    public double getRight(double[] x, int i, int j) {
        double[] xDot = new double[DIMENSION];
        int count;
        if (j == 0) {
            count = 0;
        } else {
            count = j * DIMENSION;
        }
        double mLa = 100;
        double kT = 100;
        double deltaM = 2.24;  // fuel consumption su-35

        xDot[0] = x[count + 3];     // x = vx
        xDot[1] = x[count + 4]; // y = vy
        xDot[2] = x[count + 5]; // z = vz
        double b = kT * (deltaM) / mLa;
        double psi = 200;
        double epsilon = 0;
        xDot[3] = b * Math.cos(psi) * Math.cos(epsilon) - b * Math.sin(epsilon) + b * Math.cos(epsilon) * Math.sin(psi);
        xDot[4] = b * 2 * Math.cos(epsilon) + b * Math.sin(epsilon) * Math.sin(psi);
        xDot[5] = b * 2 * Math.cos(psi);
        xDot[6] -= deltaM;
        return xDot[i];
    }*/
}
