import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Svetlin Tanyi on 02/12/16.
 */
public class BunnyHQDistanceCalculator {

    public static Facing facing = Facing.UP;
    public static int[] X_Y = {0, 0};
    public static List<Location> visitedLocations = new ArrayList<>();
    public static int counter = 0;
    public static boolean found = false;

    public static void main(String args[]) {

        String read, line = null;
        String filename = args[0];
        System.out.println(filename);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {

            while ((read = bufferedReader.readLine()) != null) {
                line = read;

                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       // calculateDistance(line);

        findFirstRecurringLocation(line);
    }

    private static void findFirstRecurringLocation(String line) {
        if (line != null) {
            String[] split = line.split(", ");
            for (String command : split) {
                if(found){
                    return;
                }
                String f = command.substring(0, 1);
                int steps = Integer.parseInt(command.substring(1));
                switch (f) {
                    case "L":
                        if (facing == Facing.UP) {
                            facing = Facing.LEFT;
                            for (int i = 0; i < steps; i++) {
                                X_Y[0] -= 1;
                                evaluateLocation();
                               /* if(!visitedLocations.contains(X_Y)){
                                    visitedLocations.add(X_Y);
                                }else{
                                    System.out.println("First Revisited Location\nX: " + X_Y[0] + " Y: " + X_Y[1]);
                                    System.out.println("Distance " + (Math.abs(X_Y[0]) + Math.abs(X_Y[1])) + " steps.");
                                }*/
                            }
                        } else if (facing == Facing.LEFT) {
                            facing = Facing.DOWN;
                            //X_Y[1] -= steps;
                            for (int i = 0; i < steps; i++) {
                                X_Y[1] -= 1;
                                evaluateLocation();
                            }
                        } else if (facing == Facing.DOWN) {
                            facing = Facing.RIGHT;
                            //X_Y[0] += steps;
                            for (int i = 0; i < steps; i++) {
                                X_Y[0] += 1;
                                evaluateLocation();
                            }
                        } else if (facing == Facing.RIGHT) {
                            facing = Facing.UP;
                            //X_Y[1] += steps;
                            for (int i = 0; i < steps; i++) {
                                X_Y[1] += 1;
                                evaluateLocation();
                            }
                        }
                        break;
                    case "R":
                        if (facing == Facing.UP) {
                            facing = Facing.RIGHT;
                            //X_Y[0] += steps;
                            for (int i = 0; i < steps; i++) {
                                X_Y[0] += 1;
                                evaluateLocation();
                            }
                        } else if (facing == Facing.RIGHT) {
                            facing = Facing.DOWN;
                            //X_Y[1] -= steps;
                            for (int i = 0; i < steps; i++) {
                                X_Y[1] -= 1;
                                evaluateLocation();
                            }
                        } else if (facing == Facing.DOWN) {
                            facing = Facing.LEFT;
                            //X_Y[0] -= steps;
                            for (int i = 0; i < steps; i++) {
                                X_Y[0] -= 1;
                                evaluateLocation();
                            }
                        } else if (facing == Facing.LEFT) {
                            facing = Facing.UP;
                            //X_Y[1] += steps;
                            for(int i = 0; i<steps; i++){
                                X_Y[1] += 1;
                                evaluateLocation();
                            }
                        }
                        break;
                }
            }
        }
    }

    private static void evaluateLocation() {
        Location location = new Location(X_Y[0], X_Y[1]);
        if (!visitedLocations.contains(location)) {
            visitedLocations.add(location);
        } else {
            System.out.println("First Revisited Location " + location.toString());
            System.out.println("Distance " + (Math.abs(location.getX()) + Math.abs(location.getY())) + " steps.");
            found = true;
        }
       // System.out.println("X: " + X_Y[0] + " Y: " + X_Y[1]);
    }

    public static void calculateDistance(String line) {
        if (line != null) {
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
        }
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
