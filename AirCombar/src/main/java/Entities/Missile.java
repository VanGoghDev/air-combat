package Entities;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class Missile implements DynamicEntity{
    private RealVector initialVector;

    public Missile() {
        initialVector = new ArrayRealVector(
                            //x, y, vx, vy
                new double[] {1, 0, 1, 0}, false);
    }

    public RealVector getRight() {
        RealVector result = initialVector;
        for (int i = 0; i < result.getDimension(); i++) {
            result.addToEntry(i, 2);
        }
        return result;
    }

    public RealVector getInitialVector() {
        return initialVector;
    }

    public void setInitialVector(RealVector initialVector) {
        this.initialVector = initialVector;
    }
}
