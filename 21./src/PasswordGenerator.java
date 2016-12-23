import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Svetlin Tanyi on 2016. 12. 21..
 */
public class PasswordGenerator {

    public static String password = "abcdefgh";
    public static char[] pw = password.toCharArray();
    public static int lineCount = 0;

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        System.out.println(password);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                if (line.contains("swap position")) {
                    String re1 = ".*?";    // Non-greedy match on filler
                    String re2 = "(\\d+)";    // Integer Number 1
                    String re3 = ".*?";    // Non-greedy match on filler
                    String re4 = "(\\d+)";    // Integer Number 2

                    Pattern p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int from = Integer.parseInt(m.group(1));
                        int to = Integer.parseInt(m.group(2));
                        swapPosition(from, to);
                    }
                } else if (line.contains("swap letter")) {
                    //swap letter X with letter Y
                    String sub = "swap letter ";
                    line = line.substring(sub.length());
                    char from = line.charAt(0);
                    sub = " with letter ";
                    line = line.substring(sub.length() + 1);
                    swapCharacter(from, line.charAt(0));
                } else if (line.contains("rotate left") || line.contains("rotate right")) {
                    int direction = -1;
                    if (line.contains("left")) {
                        direction = 0;
                    } else if (line.contains("right")) {
                        direction = 1;
                    }

                    String re1 = ".*?";    // Non-greedy match on filler
                    String re2 = "(\\d+)";    // Integer Number 1

                    Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int offset = Integer.parseInt(m.group(1));
                        rotate(direction, offset);
                    }
                } else if (line.contains("move position")) {
                    String re1 = ".*?";    // Non-greedy match on filler
                    String re2 = "(\\d+)";    // Integer Number 1
                    String re3 = ".*?";    // Non-greedy match on filler
                    String re4 = "(\\d+)";    // Integer Number 2

                    Pattern p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int from = Integer.parseInt(m.group(1));
                        int to = Integer.parseInt(m.group(2));
                        movePosition(from, to);
                    }
                } else if (line.contains("rotate based")) {
                    String sub = "rotate based on position of letter ";
                    line = line.substring(sub.length());
                    rotateByCharacter(line.charAt(0));
                } else if (line.contains("reverse position")) {
                    String re1 = ".*?";    // Non-greedy match on filler
                    String re2 = "(\\d+)";    // Integer Number 1
                    String re3 = ".*?";    // Non-greedy match on filler
                    String re4 = "(\\d+)";    // Integer Number 2

                    Pattern p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        int from = Integer.parseInt(m.group(1));
                        int to = Integer.parseInt(m.group(2));
                        reversePosition(from, to);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(String.valueOf(pw));
    }

    public static void swapPosition(int from, int to) {
        //swap these two characters at given index
        char tmp = pw[from];
        pw[from] = pw[to];
        pw[to] = tmp;

        System.out.println(lineCount++ + " swapPosition " + from + " with " + to + " " + String.valueOf(pw));
    }

    public static void swapCharacter(char from, char to) {
        //swap these two characters
        int tmpIndexFrom = 0, tmpIndexTo = 0;
        for (int i = 0; i < pw.length; i++) {
            if (pw[i] == from) {
                tmpIndexFrom = i;
            } else if (pw[i] == to) {
                tmpIndexTo = i;
            }
        }

        char tmp = pw[tmpIndexFrom];
        pw[tmpIndexFrom] = pw[tmpIndexTo];
        pw[tmpIndexTo] = tmp;

        System.out.println(lineCount++ + " swapCharacter " + from + " with " + to + " " + String.valueOf(pw));
    }

    public static void rotate(int direction, int offset) {
        //direction: 0 = left, 1 = right;
        //rotate string to given direction by offset steps
        char[] rotatedArray = new char[pw.length];
        if (direction == 0) {
            for (int i = 0; i < pw.length; i++) {
                if (i - offset < 0) {
                    rotatedArray[pw.length + i - offset] = pw[i];
                } else {
                    rotatedArray[i - offset] = pw[i];
                }
            }
        } else {
            for (int i = 0; i < pw.length; i++) {
                if (i + offset >= pw.length) {
                    rotatedArray[i - pw.length + offset] = pw[i];
                } else {
                    rotatedArray[i + offset] = pw[i];
                }
            }
        }
        pw = rotatedArray;
        System.out.println(lineCount++ + " rotate direction = " + direction + " by " + offset + " " + String.valueOf(pw));
    }

    public static void rotateByCharacter(char c) {
        //rotate right by char cs index
        int offset = 0;
        for (int i = 0; i < pw.length; i++) {
            if (pw[i] == c) {
                offset = i + 1;
                if (i > 3) {
                    offset++;
                }
                break;
            }
        }

        if (offset > pw.length) {
            offset -= pw.length;
        }

        char[] rotatedArray = new char[pw.length];
        for (int i = 0; i < pw.length; i++) {
            if (i + offset >= pw.length) {
                rotatedArray[i - pw.length + offset] = pw[i];
            } else {
                rotatedArray[i + offset] = pw[i];
            }
        }

        pw = rotatedArray;
        System.out.println(lineCount++ + " rotate by " + c + " " + String.valueOf(pw));

    }

    public static void reversePosition(int from, int to) {
        //reverse the order of the character from from index to to index

        char[] reversedArray = new char[pw.length];
        for (int i = 0; i < from; i++) {
            reversedArray[i] = pw[i];
        }

        int j = from;
        for (int i = to; i >= from; i--) {
            reversedArray[j] = pw[i];
            j++;
        }

        for (int i = to + 1; i < pw.length; i++) {
            reversedArray[i] = pw[i];
        }

        pw = reversedArray;

        System.out.println(lineCount++ + " reverse " + from + " index to " + to + " " + String.valueOf(pw));
    }

    public static void movePosition(int from, int to) {
        //remove char at postion from, insert to positon to
        ArrayList<Character> c = new ArrayList<>();
        for (char aPw : pw) {
            c.add(aPw);
        }

        char tmp = c.get(from);
        c.remove(from);
        c.add(to, tmp);

        for (int i = 0; i < c.size(); i++) {
            pw[i] = c.get(i);
        }

        System.out.println(lineCount++ + " movePosition " + from + " to " + to + " " + String.valueOf(pw));

    }

}
