import me.tongfei.progressbar.ProgressBar;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        String path = ("src/main/java/FamChat.txt");

        System.out.println("Chatters detected: " + Input.detectChatterNames(path).toString());
        List<String> chatters = Input.detectChatterNames(path);
        List<ChatterTokenFreqData> chatterTokenFreqDataList = new ArrayList<>();
        try {
            chatterTokenFreqDataList = Processor.compileChatterTokenFreqData(chatters, path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int minDisplayFrequency = 5;

        ProgressBar pb = new ProgressBar("Progress", 100).start();

        for (int i = 0; i < chatterTokenFreqDataList.size(); i++) {
            pb.stepBy(pb.getMax() / chatterTokenFreqDataList.size());

            LinkedHashMap<String, Zipf> zipfMap = Processor.sortByFrequency(chatterTokenFreqDataList.get(i).getTokenFrequencyHashMap(), minDisplayFrequency);
            int tokenCount = Processor.countTokensFromZipfMap(zipfMap);

            System.out.println(" ");
            System.out.println(chatterTokenFreqDataList.get(i).getName() + "'s Zipf Stats (" + tokenCount + " words total): ");

            Output.printZipfMapToString(zipfMap);

            System.out.println(" ");
            Stats.linearRegressionizer(Stats.zipfMapToLog10_RF_XY(zipfMap));
            System.out.println(" ");
        }

        pb.stepTo(pb.getMax());
        pb.stop();
        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("Time taken = " + timeElapsed + " ms");

        //   new GUI();

    }

}




