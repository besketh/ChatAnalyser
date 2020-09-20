import java.util.List;

public class LinearRegressionInput {

    public LinearRegressionInput(List<Double> x, List<Double> y) {
        try {
            if (x.size() == y.size()) {
                setX(x);
                setY(y);
            } else {
                throw new Exception("x and y not the same length");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Double> x;
    private List<Double> y;


    public List<Double> getX() {
        return x;
    }

    public void setX(List<Double> x) {
        this.x = x;
    }

    public List<Double> getY() {
        return y;
    }

    public void setY(List<Double> y) {
        this.y = y;
    }


}
