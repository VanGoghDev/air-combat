package ru.firsov.entities;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Dictionary;

public class DynamicEntity implements DynamicEntityInterface{
    private double x[];
    double[] initialState;
    private static int DIMENSION = 7;
    private double mLa;
    private double deltaM;
    private double kT;
    private ArrayList<Double[]> bigX = new ArrayList<Double[]>();
    private String name = "No name";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int checkIndex(int j) {
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
        this.addX(x, j);
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
        int count = checkIndex(j);
        this.x = new double[DIMENSION];
        /*if (j == 0) {
            count = 0;
        } else {
            count = j * DIMENSION;
        }*/
        for (int i = 0; i < DIMENSION; i++) {
            this.x[i] = x[count + i];
        }
    }

    public void addX(double[] x, int j) {
        int count = checkIndex(j);
        bigX.add(ArrayUtils.toObject(this.getX(x, j)));
    }

    public double[] getX(double[] x, int j) {
        int count = checkIndex(j);
        double[] result = new double[DIMENSION];
        for (int i = 0; i < DIMENSION; i++) {
            result[i] = x[count + i];
        }
        return result;
    }

    public ArrayList<Double[]> getBigX() {
        return bigX;
    }
}
