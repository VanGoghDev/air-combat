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

    @Override
    public double[] getRight(double[] x, int i, int j) {
        /*int count;
        if (j == 0) {
            count = 0;
        } else {
            count = j * 6;
        }

        double mLa = 100;
        double kT = 100;
        double deltaM = 2.24;  // fuel consumption su-35

        xDot[count] = x[count + 3];     // x = vx
        xDot[count + 1] = x[count + 4]; // y = vy
        xDot[count + 2] = x[count + 5]; // z = vz
        xDot[count + 3] = kT * (deltaM) / mLa;
        xDot[count + 4] = kT * (deltaM) / mLa;
        xDot[count + 5] = kT * (deltaM) / mLa;
        xDot[count + 6] -= deltaM;
        return xDot;  // можно сделать, чтобы вектор возвращать*/
    }
}
