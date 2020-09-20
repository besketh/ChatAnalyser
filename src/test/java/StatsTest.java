import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class StatsTest {

    @Test
    void linearRegressionTest() {

        //GIVEN
        List<String> words = Arrays.asList("fuck","the","queen");
        List<Integer> ranks = Arrays.asList(1,100,1000);//log10_R -> x-axis
        List<Integer> freqs = Arrays.asList(1,10000,1000000);//log10_F -> y-axis

        LinkedHashMap<String, Zipf> zipfMap = new LinkedHashMap<>();

        for (int i = 0; i < words.size(); i++) {
            zipfMap.put(words.get(i), new Zipf(ranks.get(i), freqs.get(i)));
        }

        //WHEN
        LinearRegressionInput linearRegressionInput = Stats.zipfMapToLog10_RF_XY(zipfMap);
        LinearRegressionResult linearRegressionResult = Stats.linearRegressionizer(linearRegressionInput);

        //THEN

        Assertions.assertEquals(2,linearRegressionResult.getSlope());
        Assertions.assertEquals(0,linearRegressionResult.getIntercept());
        Assertions.assertEquals(1,linearRegressionResult.getrSquared());
    }
}
