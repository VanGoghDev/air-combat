package ru.firsov.scene;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import ru.firsov.entities.Aircraft;
import ru.firsov.entities.DynamicEntityInterface;
import ru.firsov.entities.Missile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scene implements FirstOrderDifferentialEquations {
    private List<DynamicEntityInterface> entities = new ArrayList<DynamicEntityInterface>();
    private double[] initialState;
    private static int DIMENSION = 7;

    public Scene() {
    }

    public Scene(DynamicEntityInterface[] entity) {
        entities.addAll(Arrays.asList(entity));
    }

    public void addEntity(DynamicEntityInterface entity) {
        entities.add(entity);
    }

    public List<DynamicEntityInterface> getEntities() {
        return entities;
    }

    public int getDimension() {
        return entities.size() * DIMENSION;
    }

    public double[] getInitialState() {
        initialState = new double[entities.size() * DIMENSION];
        for (int j = 0; j < entities.size(); j++) {
            for (int i = 0; i < DIMENSION; i++) {
                initialState[i + (j*DIMENSION)] = entities.get(j).getInitialState(i);
            }
        }
        return initialState;
    }

    // пока работает только для одной цели
    private int findTargetId() {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Aircraft) {
                return i;
            }
        }
        return 0;
    }

    public void computeDerivatives(double t, double[] x, double[] xDot) throws MaxCountExceededException, DimensionMismatchException {
        ArrayList<Double[]> arrayList = new ArrayList<Double[]>();
        int targetId = findTargetId();
        double[] targetsX = new double[DIMENSION];
        System.arraycopy(x, targetId, targetsX, 0, DIMENSION);

        for (int j = 0; j < entities.size(); j++) {
            if (entities.get(j) instanceof Missile) {
                ((Missile) entities.get(j)).setTargetsX(targetsX);  // insert into missile targets x vector
            }

            arrayList.add(entities.get(j).getRight(x, j)); // combine X

            for (int i = 0; i < DIMENSION; i++) {
                xDot[i + (j*DIMENSION)] = arrayList.get(j)[i];  // store in xDot
            }
        }

        // можно подумать над тем, чтобы вывести этот кусок кода из текущего метода
        String strFilePath = "C:/Users/User/Desktop/result.txt";
        String newLine = System.getProperty("line.separator");
        String comma = ",";
        Double[] doubleArray = ArrayUtils.toObject(x);
        try {
            FileOutputStream fos = new FileOutputStream(strFilePath, true);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(fos));

            for (int i = 0; i < x.length; i++) {

                dos.writeBytes(doubleArray[i].toString());
                dos.writeBytes(comma);
            }
            dos.writeBytes(newLine);
            dos.flush();
            dos.writeBytes(newLine);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
