import java.util.LinkedHashMap;

public class ChatterTokenFreqData {
    private String name;
    private LinkedHashMap tokenFrequencyHashMap;

    public ChatterTokenFreqData(String name, LinkedHashMap tokenFrequencyHashMap) {
        this.name = name;
        this.tokenFrequencyHashMap = tokenFrequencyHashMap;


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedHashMap getTokenFrequencyHashMap() {
        return tokenFrequencyHashMap;
    }

    public void setTokenFrequencyHashMap(LinkedHashMap tokenFrequencyHashMap) {
        this.tokenFrequencyHashMap = tokenFrequencyHashMap;
    }

}
