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
    1   2   3
    4   5   6
    7   8   9
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
            System.out.print(aPassCode);
        }
    }

    private static void decodeLine(String line) {
        if (line != null) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                switch (c) {
                    case 'U':
                        if (keypadNumber > 3) {
                            keypadNumber -= 3;
                        }
                        break;
                    case 'L':
                        if (keypadNumber == 2 || keypadNumber == 3 || keypadNumber == 5 || keypadNumber == 6 || keypadNumber == 8 || keypadNumber == 9) {
                        keypadNumber -= 1;
                    }
                    break;
                    case 'R':
                        if(keypadNumber == 1 || keypadNumber == 2 || keypadNumber == 4 || keypadNumber == 5 || keypadNumber == 7 ||keypadNumber == 8){
                        keypadNumber += 1;
                    }
                        break;
                    case 'D':
                        if (keypadNumber < 7) {
                            keypadNumber += 3;
                        }
                        break;

                }
            }
            passCode.add(keypadNumber);
        }
    }
}
