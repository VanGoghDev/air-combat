package ru.firsov.modelArch;

import ru.firsov.integrator.Euler;

public class MMain {

    public static void main(String[] args) {
        Euler euler = new Euler();
        MAircraft aircraft = new MAircraft(new double[] {100, 100, 0, 100, 0, 0, 50}, 200, 0.1, 100);
        aircraft.setName("aircraft");
        MMissile missile = new MMissile(new double[] {200, 100, 0, 50, 0, 0, 100}, 100, 0.1, 500);
        missile.setName("missile");

        MScene mScene = new MScene(new MDynamicEntity[] {aircraft, missile});
        double[] y = mScene.setSceneInitialState();
        euler.run(mScene, 0, y, 50, y);
        for (int i = 0; i < y.length; i++) {
            System.out.println(y[i]);
        }
    }
}
