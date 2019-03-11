package entities;

import org.apache.commons.math3.linear.RealVector;

public interface DynamicEntityInterface{
    double getRight(double[] x, double[] xDot, int i, int j);
    double getInitialState(int i);
    void setX(double[] x, int j);
}
