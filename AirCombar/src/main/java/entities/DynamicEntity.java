package entities;

public class DynamicEntity implements DynamicEntityInterface{
    private double x[];
    private double[] initialState;
    private static int DIMENSION = 7;

    public DynamicEntity() {
    }

    public DynamicEntity(double[] initialState) {
        this.initialState = initialState;
    }

    /*public double getRight(double[] x, int i, int j) {
        double[] xDot = new double[DIMENSION];
        int count;
        if (j == 0) {
            count = 0;
        } else {
            count = j * DIMENSION;
        }
        double mLa = 100;
        double kT = 100;
        double deltaM = 2.24;  // fuel consumption su-35

        xDot[0] = x[count + 3];     // x = vx
        xDot[1] = x[count + 4]; // y = vy
        xDot[2] = x[count + 5]; // z = vz
        xDot[3] = kT * (deltaM) / mLa;
        xDot[4] = kT * (deltaM) / mLa;
        xDot[5] = kT * (deltaM) / mLa;
        xDot[6] -= deltaM;
        return xDot[i];
    }*/

    public Double[] getRight(double[] x, int j) {
        Double[] xDot1 = new Double[DIMENSION];

        // initialization of Double[] in order to avoid NullExceptions
        for (int i = 0; i < xDot1.length; i++) {
            xDot1[i] = 0.0;
        }

        int count;
        if (j == 0) {
            count = 0;
        } else {
            count = j * DIMENSION;
        }
        double mLa = 100;
        double kT = 100;
        double deltaM = 0;  // fuel consumption su-35

        xDot1[0] = x[count + 3];     // x = vx
        xDot1[1] = x[count + 4];     // y = vy
        xDot1[2] = x[count + 5];     // z = vz
        xDot1[3] = kT * (deltaM) / mLa;
        xDot1[4] = kT * (deltaM) / mLa;
        xDot1[5] = kT * (deltaM) / mLa;
        xDot1[6] -= deltaM;
        return xDot1;
    }


    public double getInitialState(int i) {
        return 0;
    }

    public void setX(double[] x, int j) {
        int count;
        this.x = new double[DIMENSION];
        if (j == 0) {
            count = 0;
        } else {
            count = j * DIMENSION;
        }
        for (int i = 0; i < DIMENSION; i++) {
            this.x[i] = x[count + i];
        }
    }
}
