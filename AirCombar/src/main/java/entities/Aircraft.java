package entities;

public class Aircraft extends DynamicEntity {
    private static int DIMENSION = 7;

    public double getInitialState(int i) {
        return initialState[i];
    }

    public Aircraft(double[] initialState, double mLa, double deltaM, double kT) {
        super(initialState, mLa, deltaM, kT);
    }

    public int getDimension() { return 7; }

}
