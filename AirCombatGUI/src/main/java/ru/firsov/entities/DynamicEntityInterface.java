package ru.firsov.entities;

import java.util.ArrayList;

public interface DynamicEntityInterface {
    Double[] getRight(double[] x, int j);
    double getInitialState(int i);
    void setX(double[] x, int j);
    double[] getX(double[] x, int j);
    void addX(double[] x, int j);
    int checkIndex(int j);
    ArrayList<Double[]> getBigX();
    String getName();
    void setName(String name);
}
