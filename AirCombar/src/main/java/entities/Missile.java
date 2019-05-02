package entities;

public class Missile extends DynamicEntity {
    private double[] targetsX;

    private static int DIMENSION = 7;

    public void setTargetsX(double[] targetsX) {
        this.targetsX = targetsX;
    }

    public double getInitialState(int i) {
        return initialState[i];
    }

    public Missile(double[] initialState, double mLa, double deltaM, double kT) {
        super(initialState, mLa, deltaM, kT);
    }

    @Override
    public Double[] getRight(double[] x, int j) {
        Double xDot[] = super.getRight(x, j);
        int count = checkIndex(j);

        double psi = Math.atan((targetsX[2] - x[count + 2])/ (targetsX[0] - x[count]));
        double epsilon = Math.atan(Math.sqrt(Math.pow((targetsX[0] - x[count]), 2) + Math.pow((targetsX[2] - x[count + 2]), 2)) / (targetsX[1] - x[count + 1]));

        xDot[3] *= Math.cos(epsilon) * Math.cos(psi) - Math.sin(epsilon) + Math.cos(epsilon) * Math.sin(psi);
        xDot[4] *= 2 * Math.cos(epsilon) + Math.sin(epsilon) * Math.sin(psi);
        xDot[5] *= 2 * Math.cos(psi);
        return xDot;
    }
}
