package scene;

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
        return entities.size() * 6;
    }

    public double[] getInitialState() {
        initialState = new double[entities.size() * 6];
        for (int j = 0; j < entities.size(); j++) {
            for (int i = 0; i < 6; i++) {
                initialState[i + (j*6)] = entities.get(j).getInitialState(i);
            }
        }
        return initialState;
    }

    public void computeDerivatives(double t, double[] x, double[] xDot) throws MaxCountExceededException, DimensionMismatchException {
        for (int j = 0; j < entities.size(); j++) {
            for (int i = 0; i < 6; i++) {
                xDot[i + (j * 6)] = entities.get(j).getRight(x, xDot, i, j);
            }
            entities.get(j).setX(xDot, j);
        }
    }
}
