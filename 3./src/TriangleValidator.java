import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Svetlin Tanyi on 03/12/16.
 */
public class TriangleValidator {

    public static int validTriangleCount = 0;
    public static int lineHopCounter = 0;
    public static Triangle triangle1, triangle2, triangle3;

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                System.out.println(line);
                String[] sides = line.split("  +");

                if (lineHopCounter == 0) {
                    triangle1 = new Triangle(Integer.parseInt(sides[1]));
                    triangle2 = new Triangle(Integer.parseInt(sides[2]));
                    triangle3 = new Triangle(Integer.parseInt(sides[3]));
                    lineHopCounter++;
                } else if (lineHopCounter == 1) {
                    triangle1.setB(Integer.parseInt(sides[1]));
                    triangle2.setB(Integer.parseInt(sides[2]));
                    triangle3.setB(Integer.parseInt(sides[3]));
                    lineHopCounter++;
                } else if (lineHopCounter == 2) {
                    triangle1.setC(Integer.parseInt(sides[1]));
                    triangle2.setC(Integer.parseInt(sides[2]));
                    triangle3.setC(Integer.parseInt(sides[3]));
                    validateTriangles();
                    lineHopCounter = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Valid triangle count: " + validTriangleCount);
    }

    private static void validateTriangles() {
        if (triangle1.isValid()) {
            validTriangleCount++;
        }
        if (triangle2.isValid()) {
            validTriangleCount++;
        }
        if (triangle3.isValid()) {
            validTriangleCount++;
        }
    }

    private static void validateTriangle(int a, int b, int c) {
        if (a + b > c && a + c > b && b + c > a) {
            validTriangleCount++;
        }
    }
}
