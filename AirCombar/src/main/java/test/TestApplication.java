package test;

import entities.Aircraft;
import entities.DynamicEntityInterface;
import entities.Missile;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator;
import scene.Scene;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class TestApplication {
    public static void main(String[] args) {
      FirstOrderIntegrator dp54 = new DormandPrince54Integrator(
              1.0e-8, 10.0, 1.0-10, 1.0e-10);
      Aircraft aircraft = new Aircraft(new double[] {-100, -100, 0, 10, 0, 0, 1000});
      Missile missile = new Missile(new double[]    {0, 0, 0, 15, 0, 0, 1000});
      FirstOrderDifferentialEquations scene = new Scene(new DynamicEntityInterface[] {aircraft, missile});
      double[] y = ((Scene) scene).getInitialState();
      String strFilePath = "C:/Users/User/IdeaProjects/MAI/air-combat-master/AirCombar/result.txt";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(strFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.close();

        dp54.integrate(scene, 0.0, y, 50, y);
        for (double v : y) {
            System.out.println(v);
        }

        ((Scene) scene).getAllX();
        int s = 2;
/*
        double[] array = new double[strFilePath.length()];
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(strFilePath)));
            for (int i = 0; i < array.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    array[i] = Double.parseDouble(line[j]);
                }
            }

            int f = 3;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
    }
}
