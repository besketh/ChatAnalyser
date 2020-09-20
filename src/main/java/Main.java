import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        String path = ("src/main/java/ChatTest.txt");


        System.out.println("Chatters detected: " + Input.detectChatterNames(path).toString());
        List<String> chatters = Input.detectChatterNames(path);
        List<ChatterTokenFreqData> chatterTokenFreqDataList = new ArrayList<>();
        chatterTokenFreqDataList = Processor.compileChatterTokenFreqData(chatters, path);

        int minDisplayFrequency = 5;

        for (int i = 0; i < chatterTokenFreqDataList.size(); i++) {
            System.out.println(" ");
            System.out.println(chatterTokenFreqDataList.get(i).getName() + "'s Zipf Stats (" + Processor.countTokensFromZipfMap(Processor.sortByFrequency(chatterTokenFreqDataList.get(i).getTokenFrequencyHashMap(), 1)) + " words total): ");

            Output.printZipfMapToString(Processor.sortByFrequency(chatterTokenFreqDataList.get(i).getTokenFrequencyHashMap(), minDisplayFrequency));

            System.out.println(" ");
            Stats.linearRegressionizer(Stats.zipfMapToLog10_RF_XY(Processor.sortByFrequency(chatterTokenFreqDataList.get(i).getTokenFrequencyHashMap(), 1)));
            System.out.println(" ");
        }


        //   new GUI();

    }


}




