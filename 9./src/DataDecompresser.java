import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Svetlin Tanyi on 09/12/16.
 */
public class DataDecompresser {

    public static int decompressedLength = 0;

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                digestLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The files decompressed length is: " + decompressedLength);

    }

    private static void digestLine(String line) {
        int positionCounter = 0;
        while (positionCounter != line.length()) {
            System.out.println(line.length());

            String re1 = ".*?";    // Non-greedy match on filler
            String re2 = "(\\d+)";    // Integer Number 1
            String re3 = ".*?";    // Non-greedy match on filler
            String re4 = "(\\d+)";    // Integer Number 2

            Pattern p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = p.matcher(line);
            if (m.find()) {
                int int1 = Integer.parseInt(m.group(1));
                int int2 = Integer.parseInt(m.group(2));
                decompressedLength += int1 * int2;
                String tmp = "(" + int1 + "x" + int2 + ")";
                int tmpCount = tmp.length();
                line = line.substring(tmpCount);
                line = line.substring(int1);
            }

            int charactersToBrace = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '(') {
                    break;
                } else {
                    charactersToBrace++;
                }
            }

            decompressedLength += charactersToBrace;

            line = line.substring(charactersToBrace);
        }

    }
}
