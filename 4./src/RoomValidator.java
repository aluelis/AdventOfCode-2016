import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Svetlin Tanyi on 04/12/16.
 */
public class RoomValidator {

    public static int sumOfIDs = 0;

    public static void main(String[] args) {
        String filename = args[0];
        System.out.println(filename);
        String read, line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                digestLine(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sum of sectorIds = " + sumOfIDs);
    }

    public static void digestLine(String line) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(line);
        Integer sectorId = 0;
        if (matcher.find()) {
            sectorId = Integer.parseInt(matcher.group());
        }
        String n = matcher.replaceAll("");
        String[] parts = n.split("\\[");
        String decode = parts[0].replace("-", "");
        parts[1] = parts[1].replace("]", "");

        Map<Character, Integer> numChars = new HashMap<>(Math.min(decode.length(), 26));

        for (int i = 0; i < decode.length(); ++i) {
            char charAt = decode.charAt(i);

            if (!numChars.containsKey(charAt)) {
                numChars.put(charAt, 1);
            } else {
                numChars.put(charAt, numChars.get(charAt) + 1);
            }
        }

        int maxOccurence = 0;
        Set s = numChars.entrySet();
        for (Object value : s) {
            Map.Entry m = (Map.Entry) value;
            if ((int) m.getValue() > maxOccurence) {
                maxOccurence = (int) m.getValue();
            }
        }

        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() != 5) {
            for (Object value : s) {
                Map.Entry m = (Map.Entry) value;
                if ((int) m.getValue() == maxOccurence) {
                    if (builder.toString().length() < 5) {
                        builder.append(m.getKey());
                    }
                }
            }
            maxOccurence--;
        }

        if (parts[1].equals(builder.toString())) {
            sumOfIDs += sectorId;
            decipherRoomName(parts[0], sectorId);
        }
    }

    private static void decipherRoomName(String codedRoomName, Integer sectorId) {
        int shift = (sectorId - 26 * (sectorId / 26));

        StringBuilder builder = new StringBuilder(codedRoomName);

        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '-') {
                builder.setCharAt(i, ' ');
            } else {
                builder.setCharAt(i, (char) ((codedRoomName.charAt(i) + shift - (int) 'a') % 26 + (int) 'a'));
            }
        }
        if (builder.toString().contains("north")) {
            System.out.println(builder.toString() + " " + sectorId);
        }
    }
}
