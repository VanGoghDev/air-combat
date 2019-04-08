package ru.firsov.modelArch;

import java.util.ArrayList;

public class MDynamicEntity implements Model{
    private String name = "Null";
    private double[] initialState;
    private double mLa;
    private double deltaM;
    private double kT;
    private ArrayList<double[]> bigX = new ArrayList<>();

    private static final int DIMENSION = 7;

    public MDynamicEntity(double[] initialState, double mLa, double deltaM, double kT) {
        this.initialState = initialState;
        this.mLa = mLa;
        this.deltaM = deltaM;
        this.kT = kT;
    }

    @Override
    public double[] getRight(double[] x) {
        addX(x); // store current position in history (for plots)

        double[] xDot = new double[DIMENSION];

        //this.mLa += x[6];
        if (x[6] <= 0.5) {
            this.deltaM = 0.0;
        }

        xDot[0] = x[3];     // x = vx
        xDot[1] = x[4];     // y = vy
        xDot[2] = x[5];     // z = vz
        xDot[3] = this.kT * (this.deltaM) / (this.mLa + x[6]);
        xDot[4] = this.kT * (this.deltaM) / (this.mLa + x[6]);
        xDot[5] = this.kT * (this.deltaM) / (this.mLa + x[6]);
        xDot[6] = x[6] - this.deltaM;
        return xDot;
    }

    @Override
    public double[] getInitialState() {
        return this.initialState;
    }

    @Override
    public void addX(double[] x) {
        bigX.add(x);
    }

    @Override
    public ArrayList<double[]> getBigX() {
        return bigX;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int checkIndex(int j) {
        return 0;
    }

    @Override
    public void setX(double[] x) {
    }

    @Override
    public double[] getX(double[] x) {
        return new double[0];
    }
}
