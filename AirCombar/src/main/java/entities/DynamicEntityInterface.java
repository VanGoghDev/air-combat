package entities;

public interface DynamicEntityInterface{
    //double getRight(double[] x, int i, int j);
    Double[] getRight(double[] x, int j);
    double getInitialState(int i);
    void setX(double[] x, int j);
}
