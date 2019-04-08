package ru.firsov.modelArch;

public class MMissile extends MDynamicEntity{
    private double[] targetsX;

    public MMissile(double[] initialState, double mLa, double deltaM, double kT) {
        super(initialState, mLa, deltaM, kT);
    }

    @Override
    public double[] getRight(double[] x) {
        double[] xDot =  super.getRight(x);
        double psi = Math.atan((this.targetsX[2] - x[2])/ (this.targetsX[0] - x[0]));
        double epsilon = Math.atan(Math.sqrt(Math.pow((this.targetsX[0] - x[0]), 2) + Math.pow((this.targetsX[2] - x[2]), 2)) / (this.targetsX[1] - x[1]));

        xDot[3] *= Math.cos(epsilon) * Math.cos(psi) - Math.sin(epsilon) + Math.cos(epsilon) * Math.sin(psi);
        xDot[4] *= 2 * Math.cos(epsilon) + Math.sin(epsilon) * Math.sin(psi);
        xDot[5] *= 2 * Math.cos(psi);
        return xDot;
    }

    public void setTargetsX(double[] x) {
        this.targetsX = x;
    }
}
