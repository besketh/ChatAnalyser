import java.util.*;

public class Processor {

    public static List<ChatterTokenFreqData> compileChatterTokenFreqData(List<String> chatters, String path) {

        List<ChatterTokenFreqData> chatterTokenFreqDataList = new ArrayList<>();
        for (int i = 0; i < chatters.size(); i++) {
            String name = chatters.get(i);
            ChatterTokenFreqData chatterTokenFreqData = new ChatterTokenFreqData(name, Input.convertWhatsappChatToTokenFreqHashMap(path, name, 17, 1));
            chatterTokenFreqDataList.add(chatterTokenFreqData);
        }
        return chatterTokenFreqDataList;
    }



    public static LinkedHashMap<String, Zipf> sortByFrequency(LinkedHashMap<String, Integer> tokenFrequencyHashMap, int minFreq) {

        //assign each frequency in the hashmap to a list element
        List<Integer> listOfFreqs = new ArrayList<>();
        for (String token : tokenFrequencyHashMap.keySet()) {
            listOfFreqs.add(tokenFrequencyHashMap.get(token));
        }

        //order list of freqs by highest first
        listOfFreqs.sort(Comparator.reverseOrder());

        //New hash map for storing ordered frequency for each token (word)
        LinkedHashMap<String, Zipf> zipfMap = new LinkedHashMap<>();

        //list if tokens already counted
        List<String> tokensAlreadyCounted = new ArrayList<>();

        //rank initialized to 1
        int R = 1;

        //looping over list of ordered frequencies
        for (int i = 0; i < listOfFreqs.size(); i++) {

            //orderedFreq at index i
            int F = listOfFreqs.get(i);

            //looping over key-value pairs to find a value that matches the orderedFreq
            for (String token : tokenFrequencyHashMap.keySet()) {

                int frequency = tokenFrequencyHashMap.get(token);

                //if value for a given key (token) matches the orderedFreq
                //AND the token has not already been counted
                //AND freq is greater than the min freq
                if (F == frequency && !(tokensAlreadyCounted.contains(token)) && F > minFreq - 1) {

                    //add new zipf data point to "zipfMap" with it's associated token
                    zipfMap.put(token, new Zipf(R, F));

                    //add token to list so that it will be ignored when sorting in the next iteration
                    tokensAlreadyCounted.add(token);

                    //increment rank
                    R++;
                }
            }
        }

        return zipfMap;


    }

    public static int countTokens(LinkedHashMap<String, Integer> tokenFrequencyHashMap) {
        int count = 0;

        //
        for (String token : tokenFrequencyHashMap.keySet()) {
            count += tokenFrequencyHashMap.get(token);
        }

        return count;
    }


    public static int countTokensFromZipfMap(LinkedHashMap<String, Zipf> zipfMap) {
        int count = 0;

        //
        for (String token : zipfMap.keySet()) {
            count += zipfMap.get(token).getF();
        }

        return count;
    }


}

