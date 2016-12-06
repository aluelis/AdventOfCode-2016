import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Svetlin Tanyi on 06/12/16.
 */
public class SignalDecoder {

    public static ArrayList<Character>[] verticalArray;
    public static StringBuilder decodedMessage;

    public static void main(String args[]) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);
        boolean isFirstLine = true;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                if (isFirstLine) {
                    isFirstLine = false;
                    verticalArray = new ArrayList[line.length()];

                    for (int i = 0; i < line.length(); i++) {
                        verticalArray[i] = new ArrayList<>();
                    }
                }

                for (int i = 0; i < line.length(); i++) {
                    verticalArray[i].add(line.charAt(i));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        decodedMessage = new StringBuilder();
        decodeSignal(verticalArray);
        System.out.println(decodedMessage.toString());
    }

    private static void decodeSignal(ArrayList<Character>[] verticalArray) {
        for (int i = 0; i < verticalArray.length; i++) {

            List<Character> characters = verticalArray[i];
            Map<Character, Integer> numChars = new HashMap<>(Math.min(characters.size(), 26));

            for (int j = 0; j < characters.size(); ++j) {
                char charAt = characters.get(j);

                if (!numChars.containsKey(charAt)) {
                    numChars.put(charAt, 1);
                } else {
                    numChars.put(charAt, numChars.get(charAt) + 1);
                }
            }

            int minOccurence = Integer.MAX_VALUE;
            Set s = numChars.entrySet();
            for (Object value : s) {
                Map.Entry m = (Map.Entry) value;
                if ((int) m.getValue() < minOccurence) {
                    minOccurence = (int) m.getValue();
                }
            }

            for (Object value : s) {
                Map.Entry m = (Map.Entry) value;
                if ((int) m.getValue() == minOccurence) {
                    decodedMessage.append(m.getKey());
                }
            }
        }
    }
}
