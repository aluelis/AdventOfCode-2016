import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Svetlin Tanyi on 03/12/16.
 */
public class BathroomDoorUnlocker {

    /*
            1
        2   3   4
    5   6   7   8   9
        10  11  12
            13
     */

    public static int keypadNumber = 5;
    public static List<Integer> passCode = new ArrayList<>();

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                System.out.println(line);
                decodeLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("Passcode is: ");
        for (Integer aPassCode : passCode) {
            if (aPassCode > 9) {
                switch (aPassCode) {
                    case 10:
                        System.out.print("A");
                        break;
                    case 11:
                        System.out.print("B");
                        break;
                    case 12:
                        System.out.print("C");
                        break;
                    case 13:
                        System.out.print("D");
                        break;
                }
            } else {
                System.out.print(aPassCode);
            }
        }
    }

    private static void decodeLine(String line) {
        if (line != null) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                switch (c) {
                    case 'U':
                        if (keypadNumber == 3 || keypadNumber == 13) {
                            keypadNumber -= 2;
                            break;
                        }
                        if (keypadNumber == 6 || keypadNumber == 7 || keypadNumber == 8 || keypadNumber == 10 || keypadNumber == 11 || keypadNumber == 12) {
                            keypadNumber -= 4;
                            break;
                        }
                    case 'L':
                        if (keypadNumber == 3 || keypadNumber == 4 || keypadNumber == 6 || keypadNumber == 7 || keypadNumber == 8 || keypadNumber == 9 || keypadNumber == 11 || keypadNumber == 12) {
                            keypadNumber -= 1;
                        }
                        break;
                    case 'R':
                        if (keypadNumber == 2 || keypadNumber == 3 || keypadNumber == 5 || keypadNumber == 6 || keypadNumber == 7 || keypadNumber == 8 || keypadNumber == 10 || keypadNumber == 11) {
                            keypadNumber += 1;
                        }
                        break;
                    case 'D':
                        if (keypadNumber == 1 || keypadNumber == 11) {
                            keypadNumber += 2;
                            break;
                        }
                        if (keypadNumber == 2 || keypadNumber == 3 || keypadNumber == 4 || keypadNumber == 6 || keypadNumber == 7 || keypadNumber == 8) {
                            keypadNumber += 4;
                        }
                        break;
                }
            }
            passCode.add(keypadNumber);
        }
    }
}
