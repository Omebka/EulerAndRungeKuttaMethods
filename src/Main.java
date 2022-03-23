public class Main {
    public static final String FUNCTION = "cos(1.5x + y) + 1.5(x - y)";
    public static final int X0 = 0;
    public static final int Y0 = 0;
    public static final double H = 0.1;
    public static final int ARRAY_SIZE = 11;
    public static final int DIGITS_AFTER_COMMA = 4;

    public static void main(String[] args) {
        double[] x = new double[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++) {
            x[i] = round(X0 + H * i, DIGITS_AFTER_COMMA);
        }


        // Метод Эйлера
        double[] yEulers = new double[ARRAY_SIZE];
        yEulers[0] = round(Y0, DIGITS_AFTER_COMMA);

        for (int i = 1; i < ARRAY_SIZE; i++) {
            double y = round(functionValue(x[i - 1], yEulers[i - 1]), DIGITS_AFTER_COMMA);
            yEulers[i] = round(yEulers[i - 1] + H * y, DIGITS_AFTER_COMMA);
        }


        // Метод Эйлера с уточнением
        double[] yEulersModified = new double[ARRAY_SIZE];
        yEulersModified[0] = round(Y0, DIGITS_AFTER_COMMA);

        for (int i = 1; i < ARRAY_SIZE; i++) {
            double y = round(functionValue(x[i - 1], yEulersModified[i - 1]), DIGITS_AFTER_COMMA);
            double x1 = round(x[i - 1] + H / 2, DIGITS_AFTER_COMMA);
            double y1 = round(yEulersModified[i - 1] + H / 2 * y, DIGITS_AFTER_COMMA);
            double y2 = round(functionValue(x1, y1), DIGITS_AFTER_COMMA);
            yEulersModified[i] = round(yEulersModified[i - 1] + H * y2, DIGITS_AFTER_COMMA);
        }


        // Метод Рунге-Кутта
        double[] yRungeKutta = new double[ARRAY_SIZE];
        yRungeKutta[0] = round(Y0, DIGITS_AFTER_COMMA);

        for (int i = 1; i < ARRAY_SIZE; i++) {
            double y = round(functionValue(x[i - 1], yRungeKutta[i - 1]), DIGITS_AFTER_COMMA);
            double k1 = round(H * y, DIGITS_AFTER_COMMA);
            double x1 = round(x[i - 1] + H / 2, DIGITS_AFTER_COMMA);
            double y1 = round(yRungeKutta[i - 1] + k1 / 2, DIGITS_AFTER_COMMA);
            y = round(functionValue(x1, y1), DIGITS_AFTER_COMMA);
            double k2 = round(H * y, DIGITS_AFTER_COMMA);
            double y2 = round(yRungeKutta[i - 1] + k2 / 2, DIGITS_AFTER_COMMA);
            y = round(functionValue(x1, y2), DIGITS_AFTER_COMMA);
            double k3 = round(H * y, DIGITS_AFTER_COMMA);
            double x2 = round(x[i - 1] + H, DIGITS_AFTER_COMMA);
            double y3 = round(yRungeKutta[i - 1] + k3, DIGITS_AFTER_COMMA);
            y = round(functionValue(x2, y3), DIGITS_AFTER_COMMA);
            double k4 = round(H * y, DIGITS_AFTER_COMMA);
            double dy = round((k1 + 2 * k2 + 2 * k3 + k4) / 6, DIGITS_AFTER_COMMA);
            yRungeKutta[i] = round(yRungeKutta[i - 1] + dy, DIGITS_AFTER_COMMA);
        }

        System.out.println("\tМетод Эйлера\tМетод Эйлера с уточнением\tМетод Рунге-Кутта");
        for (int i = 0; i < x.length; i++) {
            System.out.println("x[" + i + "] = " + x[i] + "; y[" + i + "] = " + yEulers[i] + ", " + yEulersModified[i] + ", " + yRungeKutta[i]);
        }
    }

    public static double round(double number, int numOfDigitsAfterComma) {
        return (double) Math.round(number * Math.pow(10, numOfDigitsAfterComma)) / Math.pow(10, numOfDigitsAfterComma);
    }

    public static double functionValue(double x, double y) {
        return Math.cos(1.5 * x + y) + 1.5 * (x - y);
    }
}
