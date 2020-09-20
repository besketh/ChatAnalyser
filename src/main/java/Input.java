import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Input {
    /**
     * @param path         file path
     * @param name         name of person whos chats should be tokenized
     * @param xCharsToTrim how many characters do you want to trim? (17 chars normally for whatsapp timestamp removal)
     * @param xLinesToSkip how many lines to skip? typically 1 line
     * @return a hash map storing a frequency for each token for a given person in the chat
     */
    public static LinkedHashMap<String, Integer> convertWhatsappChatToTokenFreqHashMap(String path,
                                                                                       String name,
                                                                                       int xCharsToTrim,
                                                                                       int xLinesToSkip) {

        //New hash map for storing a frequency for each token (word)
        LinkedHashMap<String, Integer> tokenFrequencyHashMap = new LinkedHashMap<String, Integer>();

        BufferedReader objReader = null;
        int currentLineNumber = 0;

        try {
            String strCurrentLine;

            objReader = new BufferedReader(new FileReader(path));

            //while theres still lines to read, assign the next line to the strCurrentLine variable
            while ((strCurrentLine = objReader.readLine()) != null && strCurrentLine.length() > 1) {

                //ignore x line(s) at the start of the file
                if (currentLineNumber > xLinesToSkip - 1) {

                    //trim x chars from start of line
                    strCurrentLine = strCurrentLine.substring(xCharsToTrim - 1, strCurrentLine.length());

                    //if line starts with name parameter then assign string to current line of reader
                    if (strCurrentLine.substring(0, name.length()).toLowerCase().equals(name.toLowerCase())) {

                        strCurrentLine = processLineForTokenizer(name, strCurrentLine);

                        //create new tokenizer stringTokenizer of the line
                        StringTokenizer stringTokenizer = new StringTokenizer(strCurrentLine, " -;.:^`!/{}*+?\"\\&%$#=,<>'¿¡()[]");

                        int remainingTokenCount = stringTokenizer.countTokens();

                        //assign next token to variable
                        String strCurrentToken = stringTokenizer.nextToken();

                        //reduce given line's remaining tokens
                        remainingTokenCount--;

                        //while there's still tokens
                        while (remainingTokenCount > 0) {
                            tokenFrequencyHashMap = addTokensToHashmap(tokenFrequencyHashMap, strCurrentToken);

                            //assign next token to variable
                            strCurrentToken = stringTokenizer.nextToken();

                            //reduce given line's remaining tokens
                            remainingTokenCount--;
                        }
                    }
                }
                currentLineNumber++;
            }

            //TODO: make error handling better
        } catch (
                IOException e) {

            e.printStackTrace();

        } finally {

            try {
                if (objReader != null)
                    objReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return tokenFrequencyHashMap;
    }

    private static String processLineForTokenizer(String name, String strCurrentLine) {
        //convert input to lower case
        strCurrentLine = strCurrentLine.toLowerCase();

        //remove certain whatapp system text from line
        if (strCurrentLine.contains("<multimedia omitido>"))
            strCurrentLine = strCurrentLine.substring(0, strCurrentLine.length() - "<multimedia omitido>".length());

        //exclude the name from the line's contents
        strCurrentLine = strCurrentLine.substring(name.length(), strCurrentLine.length());

        return strCurrentLine;
    }

    private static LinkedHashMap<String, Integer> addTokensToHashmap(LinkedHashMap<String, Integer> tokenFrequency, String stringToken) {
        //if token is already contained within the hashmap
        if (tokenFrequency.containsKey(stringToken))

            //add 1 to the token freq value
            tokenFrequency.replace(stringToken, tokenFrequency.get(stringToken) + 1);

            // else add new token with init count 1
        else tokenFrequency.put(stringToken, 1);
        return tokenFrequency;
    }

    public static List<String> detectChatterNames(String path) {
        int currentLineNumber = 0;
        List<String> chatters = new ArrayList<>();

        BufferedReader objReader = null;

        try {
            String strCurrentLine;

            objReader = new BufferedReader(new FileReader(path));

            //while theres still lines to read, assign the next line to the strCurrentLine variable
            while ((strCurrentLine = objReader.readLine()) != null && strCurrentLine.length() > 1) {
                //ignore x line(s) at the start of the file
                if (currentLineNumber > 0) {

                    int nameCharStartIndex = 16;
                    String tempString = strCurrentLine.substring(nameCharStartIndex, strCurrentLine.length());
                    int nameCharFinishIndex = tempString.indexOf(':');
                    tempString = tempString.substring(0, nameCharFinishIndex);
                    if (!chatters.contains(tempString)) {
                        chatters.add(tempString);
                    }
                    ;

                }
                currentLineNumber++;
            }
        }

        //TODO: make error handling better
        catch (
                IOException e) {

            e.printStackTrace();

        } finally {

            try {
                if (objReader != null)
                    objReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return chatters;
    }
}
