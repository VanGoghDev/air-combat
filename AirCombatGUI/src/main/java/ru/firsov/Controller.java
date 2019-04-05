package ru.firsov;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator;
import ru.firsov.entities.Aircraft;
import ru.firsov.entities.DynamicEntityInterface;
import ru.firsov.entities.Missile;
import ru.firsov.scene.Scene;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML Button addEntityBtn;
    @FXML CheckBox aircraftCheckBox;
    @FXML CheckBox missileCheckBox;
    @FXML TextField paramTxtField;
    @FXML Slider fuelSlider;
    @FXML TextField mLaTxtField;
    @FXML TextField deltaMTxtField;
    @FXML TextField kTTxtField;
    @FXML Button startModelling;
    @FXML Button defaultModelBtn;
    @FXML ChoiceBox xChoiceBox;
    @FXML ChoiceBox yChoiceBox;
    @FXML Button buildPlotBtn;
    @FXML ScatterChart scPlot;
    @FXML NumberAxis xAxis;
    @FXML NumberAxis yAxis;
    @FXML Button cleanModelBtn;
    @FXML TextField nameField;

    private List<DynamicEntityInterface> entities = new ArrayList<DynamicEntityInterface>();
    private FirstOrderIntegrator dp54 = new DormandPrince54Integrator(
            1.0e-8, 100.0, 1.0-10, 1.0e-10
    );
    private FirstOrderDifferentialEquations scene;

    public void startModelling() {
        scene = new Scene(entities.toArray(new DynamicEntityInterface[entities.size()]));
        double[] y = ((Scene) scene).getInitialState() ;
        writeIntoFile();
        dp54.integrate(scene, 0.0, y, 50, y);
        for (double v : y) {
            System.out.println(v);
        }
    }

    public void startDefaultModel() {
        Aircraft aircraft = new Aircraft(new double[] {100, 100, 0, 100, 0, 0, 150}, 300, 0.1, 100);
        aircraft.setName("aircraft");
        Missile missile = new Missile(new double[] {200, 100, 0, 50, 0, 0, 100}, 100, 0.1, 500);
        missile.setName("missile");
        entities.add(aircraft);
        entities.add(missile);
        scene = new Scene(entities.toArray(new DynamicEntityInterface[entities.size()]));
        writeIntoFile();
        double[] y = ((Scene) scene).getInitialState() ;
        dp54.integrate(scene, 0.0, y, 50, y);
        for (double v : y) {
            System.out.println(v);
        }
    }

    public void buildPlot() {

        scPlot.getXAxis().setTickLabelsVisible(true);
        int xAxisIndex = getChoiceBoxValue(xChoiceBox);
        int yAxisIndex = getChoiceBoxValue(yChoiceBox);

        double[][] dataArrayXAxis = new double[entities.size()][entities.get(0).getBigX().size()];
        double[][] dataArrayYAxis = new double[entities.size()][entities.get(0).getBigX().size()];
        for (int i = 0; i < entities.size(); i++) {
            for (int j = 0; j < entities.get(0).getBigX().size(); j++) {
                dataArrayXAxis[i][j] = entities.get(i).getBigX().get(j)[xAxisIndex];
                dataArrayYAxis[i][j] = entities.get(i).getBigX().get(j)[yAxisIndex];
            }
        }

        XYChart.Series[] series1 = new XYChart.Series[entities.size()];
        for (int i = 0; i < entities.size(); i++) {
            series1[i] = new XYChart.Series();
            series1[i].setName(entities.get(i).getName());
        }
        for (int i = 0; i < entities.size(); i++) {
            for (int j = 0; j < entities.get(0).getBigX().size(); j++) {
                series1[i].getData().add(new XYChart.Data(dataArrayXAxis[i][j], dataArrayYAxis[i][j]));
            }
            scPlot.getData().add(series1[i]);
        }
    }

    public void addEntity() {
        DynamicEntityInterface entity;
        String param = paramTxtField.getText();
        String[] params = param.split(",");
        if (params.length != 6) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not enough arguments");
            alert.setHeaderText("Result:");
            alert.setContentText("Not enough arguments in parameters field. Only 6 params allowed");
            alert.showAndWait();
            return;
        }

        Double[] initialValues = new Double[params.length + 1];  // +1 because of fuel
        for (int i = 0; i < params.length; i++) {
            initialValues[i] = Double.parseDouble(params[i]);
        }
        initialValues[6] = fuelSlider.getValue();

        double[] initialValue = ArrayUtils.toPrimitive(initialValues);
        if (aircraftCheckBox.isSelected() && !missileCheckBox.isSelected()){
            entity = new Aircraft(initialValue, Double.parseDouble(mLaTxtField.getText()), Double.parseDouble(deltaMTxtField.getText()), Double.parseDouble(kTTxtField.getText()));
            entity.setName(nameField.getText());
            entities.add(entity);
        } else if (!aircraftCheckBox.isSelected() && missileCheckBox.isSelected()) {
            entity = new Missile(initialValue, Double.parseDouble(mLaTxtField.getText()), Double.parseDouble(deltaMTxtField.getText()), Double.parseDouble(kTTxtField.getText()));
            entity.setName(nameField.getText());
            entities.add(entity);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not enough arguments");
            alert.setHeaderText("Result:");
            alert.setContentText("Select entity type");
            alert.showAndWait();
        }
    }

    private int getChoiceBoxValue(ChoiceBox choiceBox) {
        int axis = 0;
        if ("X".equals(choiceBox.getValue())) {
            axis = 0;
        } else if ("Y".equals(choiceBox.getValue())) {
            axis = 1;
        } else if ("Z".equals(choiceBox.getValue())) {
            axis = 2;
        } else if ("Vx".equals(choiceBox.getValue())) {
            axis = 3;
        } else if ("Vy".equals(choiceBox.getValue())) {
            axis = 4;
        }else if ("Vz".equals(choiceBox.getValue())) {
            axis = 5;
        }
        return axis;
    }

    public void cleanPlot() {
        scPlot.getData().clear();
    }


    public void cleanModel() {
        entities.clear();
        cleanPlot();
    }

    public void initChoiceBoxX() {
        ObservableList<String> axisList = FXCollections.observableArrayList("X","Y","Z","Vx","Vy","Vz");
        xChoiceBox.setItems(axisList);
    }



    public void initChoiceBoxY() {
        ObservableList<String> axisList = FXCollections.observableArrayList("X","Y","Z","Vx","Vy","Vz");
        yChoiceBox.setItems(axisList);
    }

    public void aircraftSelected() {
        missileCheckBox.setSelected(false);
        fuelSlider.setMin(50);
        fuelSlider.setMax(200);

        fuelSlider.setShowTickLabels(true);
        fuelSlider.setShowTickMarks(true);
        fuelSlider.setBlockIncrement(10);
    }

    public void missileSelected() {
        aircraftCheckBox.setSelected(false);
        fuelSlider.setMin(50);
        fuelSlider.setMax(100);

        fuelSlider.setShowTickLabels(true);
        fuelSlider.setShowTickMarks(true);
        fuelSlider.setBlockIncrement(10);
    }

    public void writeIntoFile() {
        String strFilePath = "C:/Users/User/Desktop/result.txt";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(strFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.close();
    }
}
