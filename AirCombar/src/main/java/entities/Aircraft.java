package entities;

public class Aircraft extends DynamicEntity {
    private double x[];
    private static int DIMENSION = 7;

    public double getInitialState(int i) {
        return initialState[i];
    }

    private double[] initialState;

    public Aircraft() {
    }

    public Aircraft(double[] initialState) {
        this.initialState = initialState;
    }

    public int getDimension() { return 7; }

}
