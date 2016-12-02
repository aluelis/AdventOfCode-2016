import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Svetlin Tanyi on 02/12/16.
 */
public class BunnyHQDistanceCalculator {

    public static Facing facing = Facing.UP;
    public static int[] X_Y = {0,0};

    public static void main(String args[]){

        String read, line = null;
        String filename = args[0];
        System.out.println(filename);

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))){

            while (( read = bufferedReader.readLine()) !=null){
                line = read;

                System.out.println(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        calculateDistance(line);
    }

    public static void calculateDistance(String line){
        if (line != null){
            String[] split = line.split(", ");
            for (String command : split) {
                String f = command.substring(0, 1);
                int steps = Integer.parseInt(command.substring(1));
                switch (f) {
                    case "L":
                        if (facing == Facing.UP) {
                            facing = Facing.LEFT;
                            X_Y[0] -= steps;
                        } else if (facing == Facing.LEFT) {
                            facing = Facing.DOWN;
                            X_Y[1] -= steps;
                        } else if (facing == Facing.DOWN) {
                            facing = Facing.RIGHT;
                            X_Y[0] += steps;
                        } else if (facing == Facing.RIGHT) {
                            facing = Facing.UP;
                            X_Y[1] += steps;
                        }
                        break;
                    case "R":
                        if (facing == Facing.UP) {
                            facing = Facing.RIGHT;
                            X_Y[0] += steps;
                        } else if (facing == Facing.RIGHT) {
                            facing = Facing.DOWN;
                            X_Y[1] -= steps;
                        } else if (facing == Facing.DOWN) {
                            facing = Facing.LEFT;
                            X_Y[0] -= steps;
                        } else if (facing == Facing.LEFT) {
                            facing = Facing.UP;
                            X_Y[1] += steps;
                        }
                        break;
                }
            }
        }else
        System.out.println("X: " + X_Y[0] + " Y: " + X_Y[1]);
        System.out.println("Distance " + (Math.abs(X_Y[0]) + Math.abs(X_Y[1])) + " steps.");
    }

    public enum Facing {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
