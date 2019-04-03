package entities;

public class DynamicEntity implements DynamicEntityInterface{
    private double x[];
    double[] initialState;
    private static int DIMENSION = 7;
    double mLa;
    double deltaM;
    double kT;

    public DynamicEntity() {
    }

    public DynamicEntity(double[] initialState) {
        this.initialState = initialState;
    }


    public DynamicEntity(double[] initialState, double mLa, double deltaM, double kT) {
        this.initialState = initialState;
        this.mLa = mLa;
        this.deltaM = deltaM;
        this.kT = kT;
    }

    int checkIndex(int j) {
        int count;

        if (j == 0) {
            count = 0;
        } else {
            count = j * DIMENSION;
        }
        return count;
    }

    public Double[] getRight(double[] x, int j) {
        this.setX(x, j);
        Double[] xDot = new Double[DIMENSION];

        // initialization of Double[] in order to avoid NullExceptions
        for (int i = 0; i < xDot.length; i++) {
            xDot[i] = 0.0;
        }

        int count = checkIndex(j);
        this.mLa += x[count + 6];
        //this.deltaM = 0.1;  // fuel consumption su-35
        if (x[count + 6] <= 0.5) {
            this.deltaM = 0.0;
        }
        //this.kT = 0;

        xDot[0] = x[count + 3];     // x = vx
        xDot[1] = x[count + 4];     // y = vy
        xDot[2] = x[count + 5];     // z = vz
        xDot[3] = this.kT * (this.deltaM) / this.mLa;
        xDot[4] = this.kT * (this.deltaM) / this.mLa;
        xDot[5] = this.kT * (this.deltaM) / this.mLa;
        xDot[6] = x[count + 6] - this.deltaM;
        return xDot;
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
