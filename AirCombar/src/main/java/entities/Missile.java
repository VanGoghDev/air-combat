package entities;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.linear.RealVector;

public class Missile extends DynamicEntity {
    private double[] initialState;
    private double x[];

    public double getInitialState(int i) {
        return initialState[i];
    }

    public Missile() {
    }

    public Missile(double[] initialState) {
        this.initialState = initialState;
    }

    public int getDimension() {
        return 0;
    }
}
