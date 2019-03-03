# air-combat

The user should describe his problem in his own classes which should implement the FirstOrderDifferentialEquations interface (or FirstOrderFieldDifferentialEquations interface). Then he should pass it to the integrator he prefers among all the classes that implement the FirstOrderIntegrator interface (or the FirstOrderFieldIntegrator interface). The following example shows how to implement the simple two-dimensional problem using double primitives:

y'0(t) = ω × (c1 - y1(t))

y'1(t) = ω × (y0(t) - c0)

with some initial state y(t0) = (y0(t0), y1(t0)). In fact, the exact solution of this problem is that y(t) moves along a circle centered at c = (c0, c1) with constant angular rate ω.

```Java
  private static class CircleODE implements FirstOrderDifferentialEquations {

    private double[] c;
    private double omega;

    public CircleODE(double[] c, double omega) {
        this.c     = c;
        this.omega = omega;
    }

    public int getDimension() {
        return 2;
    }

    public void computeDerivatives(double t, double[] y, double[] yDot) {
        yDot[0] = omega * (c[1] - y[1]);
        yDot[1] = omega * (y[0] - c[0]);
    }
}
```

Computing the state y(16.0) starting from y(0.0) = (0.0, 1.0) and integrating the ODE is done as follows (using Dormand-Prince 8(5,3) integrator as an example):

```Java
FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
FirstOrderDifferentialEquations ode = new CircleODE(new double[] { 1.0, 1.0 }, 0.1);
double[] y = new double[] { 0.0, 1.0 }; // initial state
dp853.integrate(ode, 0.0, y, 16.0, y); // now y contains final state at time t=16.0
```
