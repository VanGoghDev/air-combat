package entities;

import org.apache.commons.math3.linear.RealVector;

public class DynamicEntity implements DynamicEntityInterface{
    private double x[];
    private double[] initialState;

    public DynamicEntity() {
    }

    public DynamicEntity(double[] initialState) {
        this.initialState = initialState;
    }

    public double getRight(double[] x, double[] xDot, int i, int j) {
        int count;
        if (j == 0) {
            count = 0;
        } else {
            count = j * 6;
        }
        double mu = 132712.43994;
        double moduleX = Math.sqrt(x[count] * x[count] + x[count + 1] * x[count + 1] + x[count + 2] * x[count + 2]);
        xDot[count] = x[count];
        xDot[count + 1] = x[count + 1];
        xDot[count + 2] = x[count + 2];
        xDot[count + 3] = -mu * x[count] / Math.pow(moduleX, 3);
        xDot[count + 4] = -mu * x[count + 1] / Math.pow(moduleX, 3);
        xDot[count + 5] = -mu * x[count + 2] / Math.pow(moduleX, 3);
        return xDot[i];
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
