import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Svetlin Tanyi on 07/12/16.
 */
public class IPv7Snooper {

    public static int tlsSupportCount = 0;

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

        if (m.find()) {
            switch (bracesCount) {
                case 1:
                    word1 = m.group(1);
                    sbraces1 = m.group(2);
                    word2 = m.group(3);
                    snoopIPv7(word1, sbraces1, word2);
                    break;
                case 2:
                    word1 = m.group(1);
                    sbraces1 = m.group(2);
                    word2 = m.group(3);
                    sbraces2 = m.group(4);
                    word3 = m.group(5);
                    snoopIPv7(word1, sbraces1, word2, sbraces2, word3);
                    break;
                case 3:
                    word1 = m.group(1);
                    sbraces1 = m.group(2);
                    word2 = m.group(3);
                    sbraces2 = m.group(4);
                    word3 = m.group(5);
                    sbraces3 = m.group(6);
                    word4 = m.group(7);
                    snoopIPv7(word1, sbraces1, word2, sbraces2, word3, sbraces3, word4);
                    break;
            }

        }
    }

    private static void snoopIPv7(String word1, String sbraces1, String word2) {
        /*
        * abba
        * 0. == 3.
        * 1. == 2.
        * habababa
        *
        *
        *
        *
        *
        */


        for (int i = 0; i < sbraces1.length(); i++) {
            if (i + 3 < sbraces1.length()) {
                if (sbraces1.charAt(i) == sbraces1.charAt(i + 3) && sbraces1.charAt(i + 1) == sbraces1.charAt(i + 2) && sbraces1.charAt(i) != sbraces1.charAt(i + 1)) {
                    return;
                }
            }
        }

        for (int i = 0; i < word1.length(); i++) {
            if (i + 3 < word1.length()) {
                if (word1.charAt(i) == word1.charAt(i + 3) && word1.charAt(i + 1) == word1.charAt(i + 2) && word1.charAt(i) != word1.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }

        for (int i = 0; i < word2.length(); i++) {
            if (i + 3 < word2.length()) {
                if (word2.charAt(i) == word2.charAt(i + 3) && word2.charAt(i + 1) == word2.charAt(i + 2) && word2.charAt(i) != word2.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }
    }

    private static void snoopIPv7(String word1, String sbraces1, String word2, String sbraces2, String word3) {

        for (int i = 0; i < sbraces1.length(); i++) {
            if (i + 3 < sbraces1.length()) {
                if (sbraces1.charAt(i) == sbraces1.charAt(i + 3) && sbraces1.charAt(i + 1) == sbraces1.charAt(i + 2) && sbraces1.charAt(i) != sbraces1.charAt(i + 1)) {
                    return;
                }
            }
        }

        for (int i = 0; i < sbraces2.length(); i++) {
            if (i + 3 < sbraces2.length()) {
                if (sbraces2.charAt(i) == sbraces2.charAt(i + 3) && sbraces2.charAt(i + 1) == sbraces2.charAt(i + 2) && sbraces2.charAt(i) != sbraces2.charAt(i + 1)) {
                    return;
                }
            }
        }

        for (int i = 0; i < word1.length(); i++) {
            if (i + 3 < word1.length()) {
                if (word1.charAt(i) == word1.charAt(i + 3) && word1.charAt(i + 1) == word1.charAt(i + 2) && word1.charAt(i) != word1.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }

        for (int i = 0; i < word2.length(); i++) {
            if (i + 3 < word2.length()) {
                if (word2.charAt(i) == word2.charAt(i + 3) && word2.charAt(i + 1) == word2.charAt(i + 2) && word2.charAt(i) != word2.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }

        for (int i = 0; i < word3.length(); i++) {
            if (i + 3 < word3.length()) {
                if (word3.charAt(i) == word3.charAt(i + 3) && word3.charAt(i + 1) == word3.charAt(i + 2) && word3.charAt(i) != word3.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }
    }

    private static void snoopIPv7(String word1, String sbraces1, String word2, String sbraces2, String word3, String sbraces3, String word4) {

        for (int i = 0; i < sbraces1.length(); i++) {
            if (i + 3 < sbraces1.length()) {
                if (sbraces1.charAt(i) == sbraces1.charAt(i + 3) && sbraces1.charAt(i + 1) == sbraces1.charAt(i + 2) && sbraces1.charAt(i) != sbraces1.charAt(i + 1)) {
                    return;
                }
            }
        }

        for (int i = 0; i < sbraces2.length(); i++) {
            if (i + 3 < sbraces2.length()) {
                if (sbraces2.charAt(i) == sbraces2.charAt(i + 3) && sbraces2.charAt(i + 1) == sbraces2.charAt(i + 2) && sbraces2.charAt(i) != sbraces2.charAt(i + 1)) {
                    return;
                }
            }
        }

        for (int i = 0; i < sbraces3.length(); i++) {
            if (i + 3 < sbraces3.length()) {
                if (sbraces3.charAt(i) == sbraces3.charAt(i + 3) && sbraces3.charAt(i + 1) == sbraces3.charAt(i + 2) && sbraces3.charAt(i) != sbraces3.charAt(i + 1)) {
                    return;
                }
            }
        }

        for (int i = 0; i < word1.length(); i++) {
            if (i + 3 < word1.length()) {
                if (word1.charAt(i) == word1.charAt(i + 3) && word1.charAt(i + 1) == word1.charAt(i + 2) && word1.charAt(i) != word1.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }

        for (int i = 0; i < word2.length(); i++) {
            if (i + 3 < word2.length()) {
                if (word2.charAt(i) == word2.charAt(i + 3) && word2.charAt(i + 1) == word2.charAt(i + 2) && word2.charAt(i) != word2.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }

        for (int i = 0; i < word3.length(); i++) {
            if (i + 3 < word3.length()) {
                if (word3.charAt(i) == word3.charAt(i + 3) && word3.charAt(i + 1) == word3.charAt(i + 2) && word3.charAt(i) != word3.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }

        for (int i = 0; i < word4.length(); i++) {
            if (i + 3 < word4.length()) {
                if (word4.charAt(i) == word4.charAt(i + 3) && word4.charAt(i + 1) == word4.charAt(i + 2) && word4.charAt(i) != word4.charAt(i + 1)) {
                    tlsSupportCount++;
                    return;
                }
            }
        }
    }

}
