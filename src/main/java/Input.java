
import java.io.*;
import java.util.*;

public class Input {
    /**
     * @param path         file path
     * @param name         name of person whos chats should be tokenized
     * @param xLinesToSkip how many lines to skip? typically 1 line
     * @return a hash map storing a frequency for each token for a given person in the chat
     */
    public static LinkedHashMap<String, Integer> convertWhatsappChatToTokenFreqHashMap(String path,
                                                                                       String name,
                                                                                       int xLinesToSkip) {

        String newPath = removeEmptyLines(path);

        //New hash map for storing a frequency for each token (word)
        LinkedHashMap<String, Integer> tokenFrequencyHashMap = new LinkedHashMap<String, Integer>();

        BufferedReader objReader = null;
        int currentLineNumber = 0;

        try {
            String strCurrentLine = "";

            objReader = new BufferedReader(new FileReader(newPath));

            //while theres still lines to read, assign the next line to the strCurrentLine variable
            while ((strCurrentLine = objReader.readLine()) != null && strCurrentLine.length() > 1) {

                //ignore x line(s) at the start of the file
                if (currentLineNumber > xLinesToSkip - 1) {

                    //trim x chars from start of line

                    if ((checkLineType(strCurrentLine) + 6 < strCurrentLine.length())) {
                        strCurrentLine = strCurrentLine.substring(checkLineType(strCurrentLine) + 6);
                    }

                    if (!(checkLineType(strCurrentLine) == 0)) {


                        //if line starts with name parameter then assign string to current line of reader
                        if (strCurrentLine.substring(0, name.length()).toLowerCase().equals(name.toLowerCase())) {

                            strCurrentLine = processLineForTokenizer(name, strCurrentLine);

                            //create new tokenizer stringTokenizer of the line
                            StringTokenizer stringTokenizer = new StringTokenizer(strCurrentLine, " -;.:^`!/{}*+?\"\\&%$#=,<>'¿¡()[]");

                            int remainingTokenCount = stringTokenizer.countTokens();

                            String strCurrentToken = "";
                            if (!(remainingTokenCount == 0)) {
                                //assign next token to variable
                                strCurrentToken = stringTokenizer.nextToken();

                                //reduce given line's remaining tokens
                                remainingTokenCount--;
                            }

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

    private static String removeEmptyLines(String path) {
        Scanner file;
        PrintWriter writer;

        String newPath = path.substring(0, path.length() - 4) + "Mod.txt";

        try {

            file = new Scanner(new File(path));
            writer = new PrintWriter(newPath);

            while (file.hasNext()) {
                String line = file.nextLine();
                if (!line.isEmpty()) {
                    writer.write(line);
                    writer.write("\n");
                }
            }
            writer.write("fin");
            file.close();
            writer.close();

            return newPath;

        } catch (FileNotFoundException ex) {

        }
        return "0";
    }

    public static int checkLineType(String input) {

        int end = 13;

        if (input.length() < 13) end = input.length();

        int locationOfFirstColon = 0;//if 0, skip line
        for (int i = 0; i < end; i++) {
            if (input.charAt(i) == ':') locationOfFirstColon = i;
        }

        return locationOfFirstColon;
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

                    int lineType = checkLineType(strCurrentLine);
                    int nameCharStartIndex = lineType + 6;

                    String tempString = strCurrentLine.substring(nameCharStartIndex, strCurrentLine.length());
                    CharSequence c = ":";
                    if (!tempString.contains(c)) lineType = 0;

                    if (lineType != 0) {
                        int nameCharFinishIndex = tempString.indexOf(':');
                        tempString = tempString.substring(0, nameCharFinishIndex);

                        if (!chatters.contains(tempString)) {
                            chatters.add(tempString);
                        }
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
