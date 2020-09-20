import java.lang.Math;

public class Zipf {


    private int R;//rank
    private int F;//freq
    private double log10_R;//log base 10 of rank
    private double log10_F;//log base 10 of freq


    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }

    public double getLog10_R() {
        return log10_R;
    }

    public void setLog10_R(int R) {
        this.log10_R = Math.log10((R));
    }

    public double getLog10_F() {
        return log10_F;
    }

    public void setLog10_F(int F) {
        this.log10_F = Math.log10((F));
    }


    Zipf(int R, int F){

        setR(R);
        setF(F);
        setLog10_R(R);
        setLog10_F(F);

    }





}
