/**
 * Created by Svetlin Tanyi on 2016. 12. 18..
 */
public class TrapTileMapper {

    public static final char SAFE_TILE = '.';
    public static final char TRAP_TILE = '^';
    public static final String SAFE_TILE_STRING = ".";
    public static final String TRAP_TILE_STRING = "^";
    public static final int MAX_ROWS = 1000;
    public static int safeTileCount = 0;
    public static int FINAL_ROWS = 400000;
    public static int rowCount = 0;
    public static String startLine = "^^.^..^.....^..^..^^...^^.^....^^^.^.^^....^.^^^...^^^^.^^^^.^..^^^^.^^.^.^.^.^.^^...^^..^^^..^.^^^^";

    /**
     * Rule 1: TTS
     * Rule 2: STT
     * RUle 3: TSS
     * Rule 4  SST
     */

    public static void main(String[] args) {

        while (FINAL_ROWS != 0) {
            rowCount = 0;
            tileMapper(startLine);
            FINAL_ROWS -= 1000;
        }

        System.out.println("Safe tile count: " + safeTileCount);
    }

    private static void tileMapper(String line) {

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == SAFE_TILE) {
                safeTileCount++;
            }
        }

        StringBuilder nextLine = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            if (i == 0) {
                //match for rule 2
                if (line.charAt(i) == TRAP_TILE && line.charAt(i + 1) == TRAP_TILE) {
                    nextLine.append(TRAP_TILE_STRING);
                } else
                    // match for rule 4
                    if (line.charAt(i) == SAFE_TILE && line.charAt(i + 1) == TRAP_TILE) {
                        nextLine.append(TRAP_TILE_STRING);
                    } else {
                        nextLine.append(SAFE_TILE_STRING);
                    }
            } else if (i == line.length() - 1) {
                //match for rule 1
                if (line.charAt(i - 1) == TRAP_TILE && line.charAt(i) == TRAP_TILE) {
                    nextLine.append(TRAP_TILE_STRING);
                } else
                    //match for rule 3
                    if (line.charAt(i - 1) == TRAP_TILE && line.charAt(i) == SAFE_TILE) {
                        nextLine.append(TRAP_TILE_STRING);
                    } else {
                        nextLine.append(SAFE_TILE_STRING);
                    }
            } else {
                //match for rule 1
                if (line.charAt(i - 1) == TRAP_TILE && line.charAt(i) == TRAP_TILE && line.charAt(i + 1) == SAFE_TILE) {
                    nextLine.append(TRAP_TILE_STRING);
                } else
                    //match for rule 2
                    if (line.charAt(i - 1) == SAFE_TILE && line.charAt(i) == TRAP_TILE && line.charAt(i + 1) == TRAP_TILE) {
                        nextLine.append(TRAP_TILE_STRING);
                    } else
                        //match for rule 3
                        if (line.charAt(i - 1) == TRAP_TILE && line.charAt(i) == SAFE_TILE && line.charAt(i + 1) == SAFE_TILE) {
                            nextLine.append(TRAP_TILE_STRING);
                        } else
                            //match for rule 4
                            if (line.charAt(i - 1) == SAFE_TILE && line.charAt(i) == SAFE_TILE && line.charAt(i + 1) == TRAP_TILE) {
                                nextLine.append(TRAP_TILE_STRING);
                            } else {
                                nextLine.append(SAFE_TILE_STRING);
                            }
            }
        }
        rowCount++;
        if (rowCount != MAX_ROWS) {
            tileMapper(nextLine.toString());
        } else {
            startLine = nextLine.toString();
        }
    }
}
