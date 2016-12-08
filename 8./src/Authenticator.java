import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Svetlin Tanyi on 08/12/16.
 */
public class Authenticator {

    public static int rowCount = 6;
    public static int columnCount = 50;
    public static boolean[][] ledMatrix;

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        ledMatrix = new boolean[rowCount][columnCount];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                digestCommand(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        printMatrix(ledMatrix);
        calculateResult(ledMatrix);
    }

    private static void calculateResult(boolean[][] ledMatrix) {

        int result = 0;

        for (int i = 0; i < ledMatrix.length; i++) {
            for (int j = 0; j < ledMatrix[i].length; j++) {
                if (ledMatrix[i][j]) {
                    result++;
                }
            }
        }

        System.out.println("Lit pixel count: " + result);
    }

    private static void printMatrix(boolean[][] ledMatrix) {
        int[][] readableMatrix = new int[rowCount][columnCount];
        for (int i = 0; i < ledMatrix.length; i++) {
            for (int j = 0; j < ledMatrix[i].length; j++) {
                if (ledMatrix[i][j]) {
                    readableMatrix[i][j] = 1;
                }
                if (j == readableMatrix[i].length - 1) {
                    if (readableMatrix[i][j] == 1) {
                        System.out.println(readableMatrix[i][j]);
                    } else {
                        System.out.println(" ");
                    }
                } else {
                    if (readableMatrix[i][j] == 1) {
                        System.out.print(readableMatrix[i][j] + " ");
                    } else {
                        System.out.print("  ");
                    }
                }
            }
        }
    }

    public static void digestCommand(String line) {
        int commandType = -1;
        if (line.contains("rect")) {
            commandType = 0; //create Pixel
        } else if (line.contains("column")) {
            commandType = 1; //rotate column
        } else if (line.contains("row")) {
            commandType = 2; // rotate row
        }

        parseLine(line, commandType);
    }

    private static void parseLine(String line, int commandType) {
        String re1 = ".*?";
        String re2 = "(\\d+)";
        String re3 = ".*?";
        String re4 = "(\\d+)";

        int int1, int2;

        Pattern p = Pattern.compile(re1 + re2 + re3 + re4, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(line);
        if (m.find()) {
            int1 = Integer.parseInt(m.group(1));
            int2 = Integer.parseInt(m.group(2));

            if (commandType == 0) {
                drawRect(int1, int2);
            } else if (commandType == 1) {
                moveColumn(int1, int2);
            } else if (commandType == 2) {
                moveRow(int1, int2);
            }
        }
    }

    private static void moveRow(int int1, int int2) {
        boolean[] row = ledMatrix[int1];
        boolean[] dummyRow = new boolean[columnCount];
        for (int i = 0; i < columnCount; i++) {
            if (i + int2 > columnCount - 1) {
                int tempIndex = i + int2 - columnCount;
                dummyRow[tempIndex] = row[i];
            } else {
                dummyRow[i + int2] = row[i];
            }
        }
        ledMatrix[int1] = dummyRow;
    }

    private static void moveColumn(int int1, int int2) {
        boolean[] column = new boolean[rowCount];
        boolean[] dummyColumn = new boolean[rowCount];

        for (int i = 0; i < rowCount; i++) {
            column[i] = ledMatrix[i][int1];
        }

        for (int i = 0; i < rowCount; i++) {
            if (i + int2 > rowCount - 1) {
                int tempIndex = i + int2 - rowCount;
                dummyColumn[tempIndex] = column[i];
            } else {
                dummyColumn[i + int2] = column[i];
            }
        }

        for (int i = 0; i < rowCount; i++) {
            ledMatrix[i][int1] = dummyColumn[i];
        }
    }

    private static void drawRect(int int1, int int2) {
        for (int i = 0; i < int2; i++) {
            for (int j = 0; j < int1; j++) {
                ledMatrix[i][j] = true;
            }
        }
    }
}
