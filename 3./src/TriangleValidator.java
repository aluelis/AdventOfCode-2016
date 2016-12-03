import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Svetlin Tanyi on 03/12/16.
 */
public class TriangleValidator {

    public static int validTriangleCount = 0;

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                System.out.println(line);
                String[] sides = line.split("  +");
                validateTriangle(Integer.parseInt(sides[1]), Integer.parseInt(sides[2]), Integer.parseInt(sides[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Valid triangle count: " + validTriangleCount);
    }

    private static void validateTriangle(int a, int b, int c) {
        if (a + b > c && a + c > b && b + c > a) {
            validTriangleCount++;
        }
    }
}
