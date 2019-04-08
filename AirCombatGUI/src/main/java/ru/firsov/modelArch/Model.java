package ru.firsov.modelArch;

import java.util.ArrayList;

public interface Model {
    double[] getRight(double[] x);
    double[] getInitialState();
    void setX(double[] x);
    double[] getX(double[] x);
    void addX(double[] x);
    ArrayList<double[]> getBigX();
    int checkIndex(int j);
    void setName(String name);
    String getName();

}
