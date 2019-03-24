package test;

import entities.Aircraft;
import entities.DynamicEntityInterface;
import entities.Missile;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator;
import scene.Scene;

public class TestApplication {
    public static void main(String[] args) {
      FirstOrderIntegrator dp54 = new DormandPrince54Integrator(
              1.0e-8, 100.0, 1.0-10, 1.0e-10);
      Aircraft aircraft = new Aircraft(new double[] {100, 100, 0, 10, 0, 0, 1000});
      Missile missile = new Missile(new double[] {0, 0, 0, 10, 0, 0, 1000});
      FirstOrderDifferentialEquations scene = new Scene(new DynamicEntityInterface[] {aircraft, missile});
      double[] y = ((Scene) scene).getInitialState();
      dp54.integrate(scene, 0.0, y, 150, y);
        for (int i = 0; i < y.length; i++) {
            System.out.println(y[i]);
        }
    }
}
