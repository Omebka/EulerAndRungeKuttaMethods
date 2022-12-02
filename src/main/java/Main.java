import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static final double X0 = 0;
    public static final double Y0 = 0;
    public static final double H = 0.1;
    public static final int ARRAY_SIZE = 200;
    public static final int DIGITS_AFTER_COMMA = 4;

    public static final String[] algos = {"Эйлер", "Эйлер с уточнением", "Рунге-Кутта"};

    public static void main(String[] args) {
        double[] x = new double[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            x[i] = round(X0 + H * i, DIGITS_AFTER_COMMA);
        }

        System.out.println("Введите имя метода:");
        for (int i = 0; i < algos.length; i++) {
            if (i == algos.length - 1) {
                System.out.print(algos[i] + "\n");
            } else {
                System.out.print(algos[i] + ", ");
            }
        }

        Scanner scanner = new Scanner(System.in);
        String algo = scanner.nextLine();

        double[] y = new double[ARRAY_SIZE];

        if (algo.equals(algos[0])) {
            // Метод Эйлера
            y[0] = round(Y0, DIGITS_AFTER_COMMA);

            for (int i = 1; i < ARRAY_SIZE; i++) {
                double yNext = round(functionValue(x[i - 1], y[i - 1]), DIGITS_AFTER_COMMA);
                y[i] = round(y[i - 1] + H * yNext, DIGITS_AFTER_COMMA);
            }
        }
        else if (algo.equals(algos[1])) {
            // Метод Эйлера с уточнением
            y[0] = round(Y0, DIGITS_AFTER_COMMA);

            for (int i = 1; i < ARRAY_SIZE; i++) {
                double yNext = round(functionValue(x[i - 1], y[i - 1]), DIGITS_AFTER_COMMA);
                double x1 = round(x[i - 1] + H / 2, DIGITS_AFTER_COMMA);
                double y1 = round(y[i - 1] + H / 2 * yNext, DIGITS_AFTER_COMMA);
                double y2 = round(functionValue(x1, y1), DIGITS_AFTER_COMMA);
                y[i] = round(y[i - 1] + H * y2, DIGITS_AFTER_COMMA);
            }
        }
        else if (algo.equals(algos[2])) {
            // Метод Рунге-Кутта
            y[0] = round(Y0, DIGITS_AFTER_COMMA);

            for (int i = 1; i < ARRAY_SIZE; i++) {
                double yNext = round(functionValue(x[i - 1], y[i - 1]), DIGITS_AFTER_COMMA);
                double k1 = round(H * yNext, DIGITS_AFTER_COMMA);
                double x1 = round(x[i - 1] + H / 2, DIGITS_AFTER_COMMA);
                double y1 = round(y[i - 1] + k1 / 2, DIGITS_AFTER_COMMA);
                yNext = round(functionValue(x1, y1), DIGITS_AFTER_COMMA);
                double k2 = round(H * yNext, DIGITS_AFTER_COMMA);
                double y2 = round(y[i - 1] + k2 / 2, DIGITS_AFTER_COMMA);
                yNext = round(functionValue(x1, y2), DIGITS_AFTER_COMMA);
                double k3 = round(H * yNext, DIGITS_AFTER_COMMA);
                double x2 = round(x[i - 1] + H, DIGITS_AFTER_COMMA);
                double y3 = round(y[i - 1] + k3, DIGITS_AFTER_COMMA);
                yNext = round(functionValue(x2, y3), DIGITS_AFTER_COMMA);
                double k4 = round(H * yNext, DIGITS_AFTER_COMMA);
                double dy = round((k1 + 2 * k2 + 2 * k3 + k4) / 6, DIGITS_AFTER_COMMA);
                y[i] = round(y[i - 1] + dy, DIGITS_AFTER_COMMA);
            }
        }

        for (int i = 0; i < x.length; i++) {
            System.out.println("x[" + i + "] = " + x[i] + "; y[" + i + "] = " + y[i]);
        }

        graphics(x, y, "v(t)");
    }

    public static double round(double number, int numOfDigitsAfterComma) {
        double powOf10 = Math.pow(10, numOfDigitsAfterComma);
        return (double) Math.round(number * powOf10) / powOf10;
    }

    public static double functionValue(double x, double y) {
        double m = 70;
        double g = 9.8;

        double c = 1.22;
        double S = 1.7 * 0.4;
        double roEnv = 1.29;
        double k = 0.5 * c * S * roEnv;

        return (m * g - k * Math.pow(y, 2)) / m;
    }

    public static void graphics(double[] x, double[] y, String chartName) {
        XYSeries seriesY = new XYSeries("y(x)");

        for (int i = 0; i < y.length; i++) {
            seriesY.add(x[i], y[i]);
        }

        XYSeriesCollection xy = new XYSeriesCollection(seriesY);
        createChart(xy, chartName);
    }

    private static void createChart(XYSeriesCollection xy, String chartName) {
        JFreeChart chart = ChartFactory
                .createXYLineChart("", "x", "", xy, PlotOrientation.VERTICAL,
                        true, true, true);
        JFrame frame = new JFrame(chartName);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(400, 300);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
