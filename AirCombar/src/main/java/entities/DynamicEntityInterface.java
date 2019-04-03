package entities;

public interface DynamicEntityInterface {
    Double[] getRight(double[] x, int j);
    double getInitialState(int i);
    void setX(double[] x, int j);
}
