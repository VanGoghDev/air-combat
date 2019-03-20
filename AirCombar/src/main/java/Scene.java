import entities.DynamicEntity;
import entities.DynamicEntityInterface;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scene implements FirstOrderDifferentialEquations {
    private List<DynamicEntityInterface> entities = new ArrayList<DynamicEntityInterface>();
    private double[] initialState;
    private static int DIMENSION = 7;  // dimension of each entity

    public Scene() {
    }

    public Scene(DynamicEntityInterface[] entity) { entities.addAll(Arrays.asList(entity)); }

    public void addEntity(DynamicEntity entity) {
        entities.add(entity);


    }

    public List<DynamicEntityInterface> getEntities() {
        return entities;
    }

    public int getDimension() {  // full dimension including all entities
        return entities.size() * DIMENSION;
    }

    public double[] getInitialState() {
        initialState = new double[getDimension()];
        for (int j = 0; j < entities.size(); j++) {
            for (int i = 0; i < DIMENSION; i++) {
                initialState[i + (j*DIMENSION)] = entities.get(j).getInitialState(i);
            }
        }
        return initialState;
    }

    public void computeDerivatives(double t, double[] x, double[] xDot) throws MaxCountExceededException, DimensionMismatchException {
        for (int j = 0; j < entities.size(); j++) {
            /*for (int i = 0; i < DIMENSION; i++) {
                xDot[i + (j * DIMENSION)] = entities.get(j).getRight(x, xDot, i, j);
            }*/

            entities.get(j).setX(xDot, j);
        }
    }
}
