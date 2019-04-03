import Entities.DynamicEntity;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private RealVector fullGetRightVector;
    private List<DynamicEntity> entities = new ArrayList<DynamicEntity>();

    public Scene() {
        fullGetRightVector = new ArrayRealVector(
                new double[0], false);
    }

    public void addEntity(DynamicEntity entity) {
        entities.add(entity);

        fullGetRightVector.append(entity.getRight());
    }

    public RealVector getFullGetRightVector() {
        return fullGetRightVector;
    }

    public void setFullGetRightVector(RealVector fullGetRightVector) {
        this.fullGetRightVector = fullGetRightVector;
    }

    public List<DynamicEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<DynamicEntity> entities) {
        this.entities = entities;
    }

    public void addToGetRightVector() {

    }
}
