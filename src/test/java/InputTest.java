import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

public class InputTest {

    @Test
    void convertWhatsappChatToTokenFreqHashMapTest() throws FileNotFoundException {
        //GIVEN
        String path = ("src/main/java/ChatTest.txt");
        String name = "Jim Bó";

        //WHEN
        LinkedHashMap<String, Integer> result = Input.convertWhatsappChatToTokenFreqHashMap(path, name);

        //THEN
        Assertions.assertEquals(5, result.keySet().size());
    }


    @Test
    void processLineForTokenizerTest() {

        //WHEN
        String result = Input.processLineForTokenizer("Paul", "Paul:   ornamental bronze doorknobs made by <multimedia omitido> the Metallic Compression Company");

        //THEN
        Assertions.assertEquals(false, result.contains("<multimedia omitido>"));
        Assertions.assertEquals(false, result.contains("Paul"));
    }
}






