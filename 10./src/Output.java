import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Svetlin Tanyi on 10/12/16.
 */
public class Output {

    int number;
    List<Integer> bin;

    public Output(int number, int chip) {
        this.number = number;
        if (this.bin == null) {
            this.bin = new ArrayList<>();
        }
        bin.add(chip);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Integer> getBin() {
        return bin;
    }

    public void setBin(List<Integer> bin) {
        this.bin = bin;
    }

    public void addChipToBin(int chip) {
        bin.add(chip);
        Collections.sort(bin);
    }

    @Override
    public String toString() {
        return "Output " + number + " Chips: " + bin.toString();
    }
}
