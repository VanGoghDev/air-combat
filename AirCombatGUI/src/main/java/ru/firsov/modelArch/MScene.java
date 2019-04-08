package ru.firsov.modelArch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MScene implements Model{
    private List<MDynamicEntity> entities = new ArrayList<MDynamicEntity>();
    private double[][] initialState;
    private static int DIMENSION = 7;

    public MScene(MDynamicEntity[] entity) {
        entities.addAll(Arrays.asList(entity));
        setInitialState();
    }

    @Override
    public double[] getRight(double[] x) {
        double[][] xDot = new double[entities.size()][DIMENSION];
        double[] targetsX = new double[DIMENSION];
        int targetId = this.findTargetID();

        // протестить с какой позиции начинать копирование
        System.arraycopy(x, targetId, targetsX, 0, DIMENSION);
        double[][] entitiesX = convertVectorToArray(x);

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof MMissile) {
                ((MMissile) entities.get(i)).setTargetsX(targetsX);
            }

            xDot[i] = entities.get(i).getRight(entitiesX[i]);
        }

        return convertArrayToVector(xDot);
    }

    public double[] setSceneInitialState() {
        return convertArrayToVector(initialState);
    }

    private double[][] convertVectorToArray(double[] vector) {
        double[][] array = new double[entities.size()][DIMENSION];
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (count == vector.length) break;
                array[i][j] = vector[count];
                count++;
            }
        }
        return array;
    }

    private double[] convertArrayToVector(double[][] array) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                list.add(array[i][j]);
            }
        }

        double[] vector = new double[list.size()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = list.get(i);
        }
        return vector;
    }

    private void setInitialState() {
        initialState = new double[entities.size()][DIMENSION];
        for (int i = 0; i < entities.size(); i++) {
            initialState[i] = entities.get(i).getInitialState();
        }
    }

    public double[][] getSceneInitialState() {
        return initialState;
    }


    @Override
    public double[] getInitialState() {
        return new double[0];
    }


    @Override
    public void setX(double[] x) {

    }

    @Override
    public double[] getX(double[] x) {
        return new double[0];
    }

    @Override
    public void addX(double[] x) {

    }

    @Override
    public ArrayList<double[]> getBigX() {
        return null;
    }

    public int getDimension() {
        return entities.size() * DIMENSION;
    }

    private int findTargetID() {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof MAircraft) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int checkIndex(int j) {
        return 0;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }
}
