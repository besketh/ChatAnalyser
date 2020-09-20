import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Stats {

    public static LinearRegressionInput zipfMapToLog10_RF_XY(LinkedHashMap<String, Zipf> zipfMap) {

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        try {
            for (String token : zipfMap.keySet()) {
                x.add((double) (zipfMap.get(token).getLog10_R()));
                y.add((double) (zipfMap.get(token).getLog10_F()));
            }
            return new LinearRegressionInput(x, y);

        } catch (ArithmeticException e) {
            System.err.println(e);
        }
        return null;
    }


    public static LinearRegressionResult linearRegressionizer(LinearRegressionInput linearRegressionInput) {
        //http://2000clicks.com/MathHelp/BasicLinearRegression.aspx
        try {

            double xBar;// written as an X with a line over it, is the mean (average) of the x-values.
            double yBar;// a Y with a line over it, is the mean of the y-values.
            double ss_reg = 0; //The regression sum of squares
            double sumOfX = 0;
            double sumOfY = 0;
            double ss_xx = 0;// is the sum of the squares of the x-deviations.  SUM (xi-(X-bar))²
            double ss_yy = 0;// is the sum of the squares of the y-deviations.  SUM (xi-(X-bar))²
            double ss_xy = 0;//SUM (xi-(X-bar))(yi-(Y-bar))
            int n = linearRegressionInput.getX().size();//samplesize

            for (int i = 0; i < n; i++) {
                double x = linearRegressionInput.getX().get(i);
                double y = linearRegressionInput.getY().get(i);
                sumOfX += x;
                sumOfY += y;
            }

            xBar = sumOfX / n;
            yBar = sumOfY / n;

            for (int i = 0; i < n; i++) {
                double xVariance = linearRegressionInput.getX().get(i) - xBar;
                double yVariance = linearRegressionInput.getY().get(i) - yBar;

                ss_xx += Math.pow(xVariance, 2);
                ss_xy += xVariance * yVariance;
            }

            double slope = ss_xy / ss_xx;
            double intercept = yBar - slope * xBar;

            System.out.println("Linear Regression Calculated: ");
            System.out.println("y = " + slope + "x + " + intercept);


            for (int i = 0; i < n; i++) {
                double yVariance = linearRegressionInput.getY().get(i) - yBar;
                ss_yy += Math.pow(yVariance, 2);
                double yHat=slope*linearRegressionInput.getX().get(i)+intercept;
                double yHatVar=yHat-yBar;
                ss_reg+=Math.pow(yHatVar,2);
            }

            double rSquared=ss_reg/ss_yy;

            System.out.println("r squared = " + rSquared);


            return new LinearRegressionResult(slope, intercept, rSquared);

        } catch (ArithmeticException e) {
            System.err.println(e);
        }

        return null;

    }

    public static LinearRegressionResult crudeLinearRegressionizer(LinkedHashMap<String, Zipf> zipfMap) {

        try {
            int i = 0;
            double x1 = 0, x2 = 0, y1 = 0, y2 = 0;
            for (String token : zipfMap.keySet()) {
                if (i == 0) {
                    x1 = zipfMap.get(token).getLog10_R();
                    y1 = zipfMap.get(token).getLog10_F();
                } else if (i == zipfMap.keySet().size() - 1) {
                    x2 = zipfMap.get(token).getLog10_R();
                    y2 = zipfMap.get(token).getLog10_F();
                }
                i++;
            }
            double rise = y2 - y1;
            double run = x2 - x1;

            double slope = rise / run;

            double intercept = y1 - slope * x1;

            System.out.println("Crude Linear Regression Calculated: ");
            System.out.println("y = " + slope + "x + " + intercept);

            return new LinearRegressionResult(slope, intercept, 0);

        } catch (ArithmeticException e) {
            System.err.println(e);
        }
        return null;
    }


}








