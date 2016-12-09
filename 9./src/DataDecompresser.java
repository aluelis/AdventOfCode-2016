import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Svetlin Tanyi on 09/12/16.
 */
public class DataDecompresser {

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                System.out.println("The files decompressed length is: " + digestLine(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long digestLine(String line) {
        long decompressedSize = 0;
        while (0 != line.length()) {

            String re1 = ".*?";    // Non-greedy match on filler
            String re2 = "(\\d+)";    // Integer Number 1
            String re3 = ".*?";    // Non-greedy match on filler
            String re4 = "(\\d+)";    // Integer Number 2

            Pattern p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(line);
            if (m.find()) {
                int int1 = Integer.parseInt(m.group(1)); //length of the next sequence
                int int2 = Integer.parseInt(m.group(2)); //repeat int2 times

                int charactersToBrace = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '(') {
                        break;
                    } else {
                        charactersToBrace++;
                    }
                }
                decompressedSize += charactersToBrace;
                line = line.substring(charactersToBrace);

                String currentMarker = "(" + int1 + "x" + int2 + ")";
                line = line.substring(currentMarker.length());

                String nextSequence = line.substring(0, int1);
                line = line.substring(int1);

                if (nextSequence.contains("(")) {
                    decompressedSize += digestLine(nextSequence) * int2;
                } else {
                    decompressedSize += nextSequence.length() * int2;
                }

            } else {
                decompressedSize += line.length();
                line = "";
            }
        }
        return decompressedSize;
    }
}