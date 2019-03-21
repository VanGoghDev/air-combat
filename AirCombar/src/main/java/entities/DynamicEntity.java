package entities;

import org.apache.commons.math3.linear.RealVector;

public class DynamicEntity implements DynamicEntityInterface{
    private double x[];
    private double[] initialState;
    private static int DIMENSION;

    public DynamicEntity() {
    }

    public DynamicEntity(double[] initialState) {
        this.initialState = initialState;
    }

    public double[] getRight(double[] x, int i, int j) {
        int count;
        double[] xDot = new double[DIMENSION];
        if (j == 0) {
            count = 0;
        } else {
            count = j * 6;
        }

        double mLa = 100;
        double kT = 100;
        double deltaM = 2.24;  // fuel consumption su-35

        xDot[count] = x[count + 3];     // x = vx
        xDot[count + 1] = x[count + 4]; // y = vy
        xDot[count + 2] = x[count + 5]; // z = vz
        xDot[count + 3] = kT * (deltaM) / mLa;
        xDot[count + 4] = kT * (deltaM) / mLa;
        xDot[count + 5] = kT * (deltaM) / mLa;
        xDot[count + 6] -= deltaM;
        return xDot;
    }

    public double getInitialState(int i) {
        return 0;
    }

    public void setX(double[] x, int j) {
        int count;
        this.x = new double[6];
        if (j == 0) {
            count = 0;
        } else {
            count = j * 6;
        }
        for (int i = 0; i < 6; i++) {
            this.x[i] = x[count + i];
        }
    }
}
