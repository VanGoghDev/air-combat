package ru.firsov.test;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator;
import ru.firsov.entities.Aircraft;
import ru.firsov.entities.DynamicEntityInterface;
import ru.firsov.entities.Missile;
import ru.firsov.scene.Scene;

public class TestApplication {
    public static void main(String[] args) {
      FirstOrderIntegrator dp54 = new DormandPrince54Integrator(
              1.0e-8, 100.0, 1.0-10, 1.0e-10);
      Aircraft aircraft = new Aircraft(new double[] {100, 100, 0, 100, 0, 0, 150}, 300, 0.1, 100);
      Missile missile = new Missile(new double[] {200, 100, 0, 0, 0, 0, 100}, 100, 0.1, 500);
      FirstOrderDifferentialEquations scene = new Scene(new DynamicEntityInterface[] {aircraft, missile});
      /*double[] y = ((Scene) scene).getInitialState();
        String strFilePath = "C:/Users/User/Desktop/result.txt";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(strFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.close();
        dp54.integrate(scene, 0.0, y, 50, y);
        for (int i = 0; i < y.length; i++) {
            System.out.println(y[i]);
        }*/

        /*BuildXYChart buildXYChart = new BuildXYChart(strFilePath, 1, 2);
        buildXYChart.buildPlot();*/
    }
}
