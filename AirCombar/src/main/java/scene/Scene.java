package scene;

import entities.Aircraft;
import entities.DynamicEntityInterface;
import entities.Missile;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

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
    public int findTargetId() {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Aircraft) {
                return i;
            }
        }
        return Integer.parseInt(null);
    }

    public void computeDerivatives(double t, double[] x, double[] xDot) throws MaxCountExceededException, DimensionMismatchException {
        ArrayList<Double[]> arrayList = new ArrayList<Double[]>();
        int targetId = findTargetId();
        double[] targetsX = new double[DIMENSION];
        System.arraycopy(x, targetId, targetsX, 0, 7);

        for (int j = 0; j < entities.size(); j++) {
            entities.get(j).setX(xDot, j);
            if (entities.get(j) instanceof Missile) {
                ((Missile) entities.get(j)).setTargetsX(targetsX);
            }
            arrayList.add(entities.get(j).getRight(x, j)); // combine X
        }

        for (int j = 0; j < arrayList.size(); j++) {
            for (int i = 0; i < DIMENSION; i++) {
                xDot[i + (j*DIMENSION)] = arrayList.get(j)[i];  // store in xDot
            }
            /*System.arraycopy(x, targetId, targetsX, 0, 7);
            entities.get(j).setX(xDot, j);
            if (entities.get(j) instanceof Missile) {
                ((Missile) entities.get(j)).setTargetsX(targetsX);
            }*/
        }
        int s = 4;
    }


    /*public void computeDerivatives(double t, double[] x, double[] xDot) throws MaxCountExceededException, DimensionMismatchException {
        for (int j = 0; j < entities.size(); j++) {
            for (int i = 0; i < DIMENSION; i++) {
                xDot[i + (j * DIMENSION)] = entities.get(j).getRight(x, i, j);
            }
            entities.get(j).setX(xDot, j);
        }
    }*/

 /*   public void computeDerivatives(double t, double[] x, double[] xDot) throws MaxCountExceededException, DimensionMismatchException {
        ArrayList<Double[]> arrayList = new ArrayList<Double[]>();
        //double[][] arrays = new double[entities.size()][DIMENSION];
        for (int j = 0; j < entities.size(); j++) {
            arrayList.add(entities.get(j).getRight(x, j));
            //arrays[j] = entities.get(j).getRight(x, j);
            //xDot = ArrayUtils.addAll(arrays[0], arrays[1]);
            //entities.get(j).setX(xDot, j);
        }

        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < DIMENSION; j++) {
                xDot[j + (i*DIMENSION)] = arrayList.get(i)[j];
            }
        }
        //entities.get(j).setX(xDot, j);
*//*
        for (int j = 0; j < entities.size(); j++) {
            System.arraycopy(arrays[j], 0, xDot, 0, arrays[j].length);
        }*//*
        int s = 4;
    }*/
}
