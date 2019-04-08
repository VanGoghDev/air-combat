package ru.firsov.integrator;

import ru.firsov.modelArch.Model;

public class Euler {
    double h = 0.1;

    public void run(Model model, double t0, double[] yIn, double t, double[] yOut) {
        while (t0 <= t) {
            //scene.addResult(yIn);
            double[] f = model.getRight(yIn);

            for (int j = 0; j < yIn.length; j++) {
                f[j] = yIn[j] + f[j] * h / 2;
            }

            for (int i = 0; i < yIn.length; i++) {
                yOut[i] = yIn[i] + h * model.getRight(f)[i];
            }
            t0 += h;
        }
    }

    /*public void run(Scene scene, double t0, double[] yIn, double t, double[] yOut) {
        while (t0 <= t) {
            scene.addResult(yIn);
            for (int i = 0; i < yIn.length; i++) {
                yOut[i] = yIn[i] + h * scene.getRight(yIn, t0)[i];
            }
            t0 += h;
        }
    }*/
}
