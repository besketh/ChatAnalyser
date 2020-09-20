public class LinearRegressionResult extends Stats {

    LinearRegressionResult(double slope, double intercept, double rSquared) {
        setSlope(slope);
        setIntercept(intercept);
        setrSquared(rSquared);
    }

    private double slope;
    private double intercept;
    private double rSquared;

    public double getrSquared() {
        return rSquared;
    }
    public void setrSquared(double rSquared) {
        this.rSquared = rSquared;
    }
    public double getSlope() {
        return slope;
    }
    public void setSlope(double slope) {
        this.slope = slope;
    }
    public double getIntercept() {
        return intercept;
    }
    public void setIntercept(double intercept) {
        this.intercept = intercept;
    }

}
