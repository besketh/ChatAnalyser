import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class ProcessorTest {

    @Test
    void sortByFrequency() {
        //GIVEN

        LinkedHashMap<String, Integer> tokenFrequencyHashMap = new LinkedHashMap<>();


        List<String> words = Arrays.asList("test", "testicle", "icicle", "wallet", "me", "sideways", "jim", "key", "lasagna");
        List<Integer> freqs = Arrays.asList(59, 4, 20, 6, 8, 2, 40, 30, 1);


        for (int i = 0; i < words.size(); i++) {
            tokenFrequencyHashMap.put(words.get(i), freqs.get(i));
        }

        //WHEN
        LinkedHashMap<String, Zipf> resultingZipfMap = Processor.sortByFrequency(tokenFrequencyHashMap, 1);

        //THEN

        Assertions.assertEquals(1, resultingZipfMap.get(words.get(0)).getR());
        Assertions.assertEquals(2, resultingZipfMap.get(words.get(6)).getR());
        Assertions.assertEquals(9, resultingZipfMap.get(words.get(8)).getR());
        Assertions.assertEquals(9, resultingZipfMap.keySet().size());
    }
}



