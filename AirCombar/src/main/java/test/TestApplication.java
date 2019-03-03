package test;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator;

public class TestApplication {
    public static void main(String[] args) {
        FirstOrderIntegrator dp45 = new DormandPrince54Integrator(
                1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        FirstOrderDifferentialEquations ode = new CircleODE(new double[] {1.0, 1.0}, 0.1);
        double[] y = new double[] {0.0, 1.0}; // initial state
        dp45.integrate(ode, 0.0, y, 16.0, y);
        int h = 2;
    }
}
