package entities;

public class Aircraft extends DynamicEntity {
    private double x[];

    public double getInitialState(int i) {
        return initialState[i];
    }

    private double[] initialState;

    public Aircraft() {
    }

    public Aircraft(double[] initialState) {
        this.initialState = initialState;
    }

    public int getDimension() { return 6; }
}
