import java.util.HashMap;
import java.util.LinkedHashMap;

public class Output {

    //print linkedhashmap
    public static void printHashmap(LinkedHashMap<String,Integer> hashMap) {

        for (String token : hashMap.keySet()) {
            int frequency = hashMap.get(token);
            System.out.println(frequency + " " + token);
        }
    }

    //print zipfmap
    public static void printZipfMap(LinkedHashMap<String,Zipf> zipfMap) {

        System.out.println("R, F, log10_R, log10_F, Word");
        for (String token : zipfMap.keySet()) {
            System.out.println(zipfMap.get(token).getR() + ", " + zipfMap.get(token).getF() + ", " + zipfMap.get(token).getLog10_R() +  ", " + zipfMap.get(token).getLog10_F() + ", " + token);
        }
    }

    //print zipfmap
    public static String printZipfMapToString(LinkedHashMap<String,Zipf> zipfMap) {
        String result = "R, F, log10_R, log10_F, Word \n";
        System.out.println("R, F, log10_R, log10_F, Word");
        for (String token : zipfMap.keySet()) {
            System.out.println(zipfMap.get(token).getR() + ", " + zipfMap.get(token).getF() + ", " + zipfMap.get(token).getLog10_R() +  ", " + zipfMap.get(token).getLog10_F() + ", " + token);
            result+= zipfMap.get(token).getR() + ", " + zipfMap.get(token).getF() + ", " + zipfMap.get(token).getLog10_R() + ", " + zipfMap.get(token).getLog10_F() + ", " + token + "\n";
        }
        return result;
    }


    //overload for normal hashmap
    public static void printHashmap(HashMap<String,Integer> hashMap) {

        for (String token : hashMap.keySet()) {
            int frequency = hashMap.get(token);
            System.out.println(frequency + " " + token);
        }
    }



}
