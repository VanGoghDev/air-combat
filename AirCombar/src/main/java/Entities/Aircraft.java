package Entities;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

public class Aircraft implements FirstOrderDifferentialEquations, DynamicEntity {

    private double[] x;

    public Aircraft(double[] x) {
        this.x = x;
    }

    public int getDimension() { return 6; }

    public void computeDerivatives(double t, double[] x, double[] xDot) {
        double mu = 132712.43994e6 * 3600;
        double moduleX = Math.sqrt(x[0] * x[0] + x[1] * x[1] + x[2] * x[2]);
        xDot[0] = x[0];
        xDot[1] = x[1];
        xDot[2] = x[2];
        xDot[3] = -mu * x[0] / Math.pow(moduleX, 3);
        xDot[4] = -mu * x[1] / Math.pow(moduleX, 3);
        xDot[5] = -mu * x[2] / Math.pow(moduleX, 3);
    }

    // заглушка
    public RealVector getRight() {
        return null;
    }
}
