import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Svetlin Tanyi on 07/12/16.
 */
public class IPv7Snooper {

    public static int tlsSupportCount = 0;
    public static int sslSupportCount = 0;

    public static void main(String[] args) {

        String read, line;
        String filename = args[0];
        System.out.println(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                int bracesCount = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '[') {
                        bracesCount++;
                    }
                }

                try {
                    digestLine(line, bracesCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("The number of IPv7 addresses that support TLS: " + tlsSupportCount);
        System.out.println("The number of IPv7 addresses that support SSL: " + sslSupportCount);
    }

    private static void digestLine(String line, int bracesCount) throws Exception {
        String re1 = "((?:[a-z][a-z]+))";    // Word 1
        String re2 = "(\\[.*?\\])";    // Square Braces 1
        String re3 = "((?:[a-z][a-z]+))";    // Word 2
        String re4 = "(\\[.*?\\])";    // Square Braces 2
        String re5 = "((?:[a-z][a-z]+))";    // Word 3
        String re6 = "(\\[.*?\\])";    // Square Braces 3
        String re7 = "((?:[a-z][a-z]+))";    // Word 4

        Pattern p;

        switch (bracesCount) {
            case 1:
                p = Pattern.compile(re1 + re2 + re3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                break;
            case 2:
                p = Pattern.compile(re1 + re2 + re3 + re4 + re5, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                break;
            case 3:
                p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                break;
            default:
                throw new Exception("Too many braces: " + bracesCount);
        }

        Matcher m = p.matcher(line);

        String word1;
        String sbraces1;
        String word2;
        String sbraces2;
        String word3;
        String sbraces3;
        String word4;

        ArrayList<String> words;
        ArrayList<String> sbraces;

        if (m.find()) {
            switch (bracesCount) {
                case 1:
                    word1 = m.group(1);
                    sbraces1 = m.group(2);
                    word2 = m.group(3);
                    words = new ArrayList<>(Arrays.asList(word1, word2));
                    sbraces = new ArrayList<>(Arrays.asList(sbraces1));
                    snoopIPv7_TLS(words, sbraces);
                    snoopIPv7_SSL(words, sbraces);

                    break;
                case 2:
                    word1 = m.group(1);
                    sbraces1 = m.group(2);
                    word2 = m.group(3);
                    sbraces2 = m.group(4);
                    word3 = m.group(5);

                    words = new ArrayList<>(Arrays.asList(word1, word2, word3));
                    sbraces = new ArrayList<>(Arrays.asList(sbraces1, sbraces2));
                    snoopIPv7_TLS(words, sbraces);
                    snoopIPv7_SSL(words, sbraces);
                    break;
                case 3:
                    word1 = m.group(1);
                    sbraces1 = m.group(2);
                    word2 = m.group(3);
                    sbraces2 = m.group(4);
                    word3 = m.group(5);
                    sbraces3 = m.group(6);
                    word4 = m.group(7);

                    words = new ArrayList<>(Arrays.asList(word1, word2, word3, word4));
                    sbraces = new ArrayList<>(Arrays.asList(sbraces1, sbraces2, sbraces3));
                    snoopIPv7_TLS(words, sbraces);
                    snoopIPv7_SSL(words, sbraces);
                    break;
            }
        }
    }

    private static void snoopIPv7_SSL(ArrayList<String> words, ArrayList<String> sbraces) {
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            for (int j = 0; j < word.length(); j++) {
                if (j + 2 < word.length()) {
                    if (word.charAt(j) == word.charAt(j + 2) && word.charAt(j) != word.charAt(j + 1)) {
                        String bab = new StringBuilder().append("").append(word.charAt(j + 1)).append(word.charAt(j)).append(word.charAt(j + 1)).toString();
                        for (int k = 0; k < sbraces.size(); k++) {
                            String sbrace = sbraces.get(k);
                            if (sbrace.contains(bab)) {
                                sslSupportCount++;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void snoopIPv7_TLS(ArrayList<String> words, ArrayList<String> sbraces) {
        for (String sbrace : sbraces) {
            for (int i = 0; i < sbrace.length(); i++) {
                if (i + 3 < sbrace.length()) {
                    if (sbrace.charAt(i) == sbrace.charAt(i + 3) && sbrace.charAt(i + 1) == sbrace.charAt(i + 2) && sbrace.charAt(i) != sbrace.charAt(i + 1)) {
                        return;
                    }
                }
            }
        }

        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                if (i + 3 < word.length()) {
                    if (word.charAt(i) == word.charAt(i + 3) && word.charAt(i + 1) == word.charAt(i + 2) && word.charAt(i) != word.charAt(i + 1)) {
                        tlsSupportCount++;
                        return;
                    }
                }
            }
        }
    }
}
