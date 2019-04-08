package ru.firsov;

import com.github.javafx.charts.zooming.ZoomManager;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince54Integrator;
import org.gillius.jfxutils.chart.ChartZoomManager;
import ru.firsov.entities.Aircraft;
import ru.firsov.entities.DynamicEntityInterface;
import ru.firsov.entities.Missile;
import ru.firsov.integrator.Euler;
import ru.firsov.modelArch.MAircraft;
import ru.firsov.modelArch.MDynamicEntity;
import ru.firsov.modelArch.MMissile;
import ru.firsov.modelArch.MScene;
import ru.firsov.scene.Scene;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

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
    @FXML BorderPane borderPane;
    @FXML Pane pane;
    @FXML Rectangle selectRect;

    //private List<DynamicEntityInterface> entities = new ArrayList<DynamicEntityInterface>();
    private List<MDynamicEntity> entities = new ArrayList<MDynamicEntity>();
    private FirstOrderIntegrator dp54 = new DormandPrince54Integrator(
            1.0e-8, 100.0, 1.0-10, 1.0e-10
    );
    //private FirstOrderDifferentialEquations scene;
    private MScene scene;
    ChartZoomManager zoomManager;

    public void startModelling() {
        scene = new MScene(entities.toArray(new MDynamicEntity[entities.size()]));
        double[] y = scene.setSceneInitialState();
        writeIntoFile();
        Euler euler = new Euler();
        euler.run(scene, 0, y, 50, y);
        for (double v : y) {
            System.out.println(v);
        }
        zoomManager = new ChartZoomManager( pane, selectRect, scPlot );
        /*scene = new Scene(entities.toArray(new DynamicEntityInterface[entities.size()]));
        double[] y = ((Scene) scene).getInitialState() ;
        writeIntoFile();
        dp54.integrate(scene, 0.0, y, 50, y);
        for (double v : y) {
            System.out.println(v);
        }
        zoomManager = new ChartZoomManager( pane, selectRect, scPlot );*/
    }

    public void startDefaultModel() {
        //Aircraft aircraft = new Aircraft(new double[] {100, 100, 0, 100, 0, 0, 150}, 300, 0.1, 100);
        //aircraft.setName("aircraft");
        //Missile missile = new Missile(new double[] {200, 100, 0, 50, 0, 0, 100}, 100, 0.1, 500);
        //missile.setName("missile");
        //entities.add(aircraft);
        //entities.add(missile);
        //scene = new Scene(entities.toArray(new DynamicEntityInterface[entities.size()]));
        writeIntoFile();
        //double[] y = ((Scene) scene).getInitialState() ;
        //dp54.integrate(scene, 0.0, y, 50, y);
        //for (double v : y) {
        //    System.out.println(v);
        //}
        zoomManager = new ChartZoomManager( pane, selectRect, scPlot );
    }

    public void buildPlot() {

        scPlot.getXAxis().setTickLabelsVisible(true);
        int xAxisIndex = getChoiceBoxValue(xChoiceBox);
        int yAxisIndex = getChoiceBoxValue(yChoiceBox);
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);

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
        //ZoomManager zoomManager = new ZoomManager<NumberAxis, NumberAxis>(pane, scPlot);
        zoomManager.start();


        for (int i = 0; i < entities.size(); i++) {
            for (int j = 0; j < entities.get(0).getBigX().size(); j++) {
                series1[i].getData().add(new XYChart.Data(dataArrayXAxis[i][j], dataArrayYAxis[i][j]));
            }
            scPlot.getData().add(series1[i]);
            //new ZoomManager(pane, scPlot, series1[i]);
        }

        /*ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Integer random = ThreadLocalRandom.current().nextInt(10);

            Platform.runLater(() -> {
                for (int i = 0; i < entities.size(); i++) {
                    for (int j = 0; j < entities.get(0).getBigX().size(); j++) {
                        series1[i].getData().add(new XYChart.Data(dataArrayXAxis[i][j], dataArrayYAxis[i][j]));
                    }
                    scPlot.getData().add(series1[i]);
                    //new ZoomManager(pane, scPlot, series1[i]);
                }
            });
        }, 0, 1, TimeUnit.SECONDS);*/

    }

    public void setZoom() {
        zoomManager.start();
    }

    public void zoomOut() {
        /*Euler euler = new Euler();
        Aircraft aircraft =  new Aircraft(new double[] {100, 100, 0, 100, 0, 0}, 200, 0.1, 100);
        double[] y = {100, 100, 0, 100, 0, 0, 50} ;
        writeIntoFile();
        euler.run(aircraft, 0.0, y, 50, y);
        System.out.println();
        for (double v : y) {
            System.out.println(v);
        }
        zoomManager = new ChartZoomManager( pane, selectRect, scPlot );*/
    }

    public void addEntity() {
        //DynamicEntityInterface entity;
        MDynamicEntity entity;
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
            entity = new MAircraft(initialValue, Double.parseDouble(mLaTxtField.getText()), Double.parseDouble(deltaMTxtField.getText()), Double.parseDouble(kTTxtField.getText()));
            entity.setName(nameField.getText());
            entities.add(entity);
        } else if (!aircraftCheckBox.isSelected() && missileCheckBox.isSelected()) {
            entity = new MMissile(initialValue, Double.parseDouble(mLaTxtField.getText()), Double.parseDouble(deltaMTxtField.getText()), Double.parseDouble(kTTxtField.getText()));
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
