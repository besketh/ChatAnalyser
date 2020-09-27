import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Input {
    /**
     * @param path file path
     * @param name name of person whos chats should be tokenized
     * @return a hash map storing a frequency for each token for a given person in the chat
     */
    public static LinkedHashMap<String, Integer> convertWhatsappChatToTokenFreqHashMap(String path,
                                                                                       String name) throws FileNotFoundException {

        //New hash map for storing a frequency for each token (word)
        AtomicReference<LinkedHashMap<String, Integer>> tokenFrequencyHashMap = new AtomicReference<>(new LinkedHashMap<String, Integer>());

        BufferedReader objReader = null;
        int currentLineNumber = 0;

        objReader = new BufferedReader(new FileReader(path));

        objReader.lines().forEach(line -> {

            if (!line.isEmpty() && isValidLine(line) && line.contains(name)) {

                //trim x chars from start of line
                if ((calculateFirstColonPos(line) + 6 < line.length())) {
                    line = line.substring(calculateFirstColonPos(line) + 6);
                }

                if (line.startsWith(name)) {
                    line = processLineForTokenizer(name, line);

                    //create new tokenizer stringTokenizer of the line
                    StringTokenizer stringTokenizer = new StringTokenizer(line, " -;.:^`!/{}*+?\"\\&%$#=,<>'¿¡()[]");

                    while (stringTokenizer.hasMoreTokens()) {
                        String strCurrentToken = stringTokenizer.nextToken();
                        tokenFrequencyHashMap.set(addTokensToHashmap(tokenFrequencyHashMap.get(), strCurrentToken));
                    }
                }
            }
        });

        return tokenFrequencyHashMap.get();
    }

    public static String processLineForTokenizer(String name, String strCurrentLine) {
        //convert input to lower case
        strCurrentLine = strCurrentLine.toLowerCase();

        //remove certain whatapp system text from line
        List<String> systemMessages = Arrays.asList("<multimedia omitido>");
        for (String systemMessage : systemMessages) {
            CharSequence charSequence = systemMessage;
            if (strCurrentLine.contains(systemMessage)) {
                strCurrentLine.indexOf(systemMessage);
                strCurrentLine = strCurrentLine.replace(charSequence, "");
            }
        }
        //exclude the name from the line's contents
        if (strCurrentLine.length() > name.length())
            strCurrentLine = strCurrentLine.substring(name.length(), strCurrentLine.length());

        return strCurrentLine;
    }

    public static LinkedHashMap<String, Integer> addTokensToHashmap(LinkedHashMap<String, Integer> tokenFrequency, String stringToken) {
        //if token is already contained within the hashmap
        if (tokenFrequency.containsKey(stringToken))

            //add 1 to the token freq value
            tokenFrequency.replace(stringToken, tokenFrequency.get(stringToken) + 1);

            // else add new token with init count 1
        else tokenFrequency.put(stringToken, 1);
        return tokenFrequency;
    }

    public static String removeEmptyLines(String path) {
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

    public static boolean isValidLine(String line) {

        int colonCount = line.length() - line.replaceAll(":", "").length();
        int hyphenCount = line.length() - line.replaceAll("-", "").length();

        if (colonCount > 1 && hyphenCount > 0) return true;
        return false;
    }


    public static int calculateFirstColonPos(String input) {

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
            while ((strCurrentLine = objReader.readLine()) != null) {

                if (isValidLine(strCurrentLine)) {

                    int lineType = calculateFirstColonPos(strCurrentLine);
                    int nameCharStartIndex = lineType + 6;

                    String tempString = strCurrentLine.substring(nameCharStartIndex, strCurrentLine.length());

                    int nameCharFinishIndex = tempString.indexOf(':');
                    tempString = tempString.substring(0, nameCharFinishIndex);

                    if (!chatters.contains(tempString)) {
                        chatters.add(tempString);
                    }
                }
            }
            currentLineNumber++;

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
