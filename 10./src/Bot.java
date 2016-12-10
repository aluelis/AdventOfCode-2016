import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Svetlin Tanyi on 10/12/16.
 */
public class Bot {

    int number;
    List<Integer> chips = new ArrayList<>();

    public Bot(int number) {
        this.number = number;
    }

    public Bot(int number, int chip) {
        this.number = number;
        chips.add(chip);
    }

    public void addChip(int chip) throws Exception {
        chips.add(chip);
        Collections.sort(chips);
        if (chips.size() > 2) {
            throw new Exception("Too many chips");
        }

        if (chips.size() == 2 && chips.get(0) == 17 && chips.get(1) == 61) {
            System.out.println("Sir, I am Bot " + number + " and I compared the required chips");
        }
    }

    public int getNumber() {
        return number;
    }

    public boolean hasTwoChips() {
        return chips.size() == 2;
    }

    public int removeLowChip() throws Exception {
        if (!chips.isEmpty()) {
            int lowChip = chips.get(0);
            chips.remove(0);
            return lowChip;
        }
        throw new Exception("Can not remove low chip");
    }

    public int removeHighChip() throws Exception {
        if (chips.size() == 2) {
            int highChip = chips.get(1);
            chips.remove(1);
            return highChip;
        }
        throw new Exception("Can not remove high chip");
    }

    @Override
    public String toString() {
        return "Bot " + number + " Chips: " + chips.toString();
    }
}
