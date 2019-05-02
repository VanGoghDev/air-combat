package plots;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.io.*;
import java.util.Scanner;

public class BuildXYChart {

    private File file;
    private String fileDir;
    private int xCol;
    private int yCol;

    public BuildXYChart(String fileDir, int xCol, int yCol) {
        this.file = new File(fileDir);
        this.fileDir = fileDir;
        this.xCol = xCol - 1;
        this.yCol = yCol - 1;
    }

    public void buildPlot() {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(this.file)));
            int rows = countLinesNew(fileDir);
            int columns = 7 * 2; // переделать
            double[][] myArray = new double[rows][columns];
            while(sc.hasNextLine()) {
                for (int i = 0; i < myArray.length; i++) {
                    String[] line = sc.nextLine().trim().split(",");
                    for (int j = 0; j < line.length; j++) {
                        myArray[i][j] = Double.parseDouble(line[j]);
                    }
                }
            }
            double[][] data1 = new double[rows][2];
            double[][] data2 = new double[rows][2]; // переделать
            for (int i = 0; i < myArray.length; i++) {
                for (int j = 0; j < myArray[0].length; j++) {
                    data1[i][0] = myArray[j][this.xCol];
                    data1[i][1] = myArray[j][this.yCol];
                    data2[i][0] = myArray[j][this.xCol + 7];
                    data2[i][1] = myArray[j][this.yCol + 7];
                }
            }
            //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", data1[0], data1[1]);
            XYChart chart = new XYChartBuilder().width(600).height(500).title("Aircombat").xAxisTitle("X").yAxisTitle("Y").build();
            chart.addSeries("Aircraft", data1[0], data1[1]);
            chart.addSeries("Missile", data2[0], data2[1]);
            //chart.set
            new SwingWrapper(chart).displayChart();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countLinesNew(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i = 0; i < 1024; ) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                System.out.println(readChars);
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) {
        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

// Create Chart
        //XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

// Show it
        //new SwingWrapper(chart).displayChart();
    }
}
