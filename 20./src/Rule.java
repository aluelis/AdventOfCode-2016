/**
 * Created by Svetlin Tanyi on 2016. 12. 20..
 */
public class Rule {

    private long startAddress, endAddress;

    public Rule(long startAddress, long endAddress) {
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

    public long getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(long startAddress) {
        this.startAddress = startAddress;
    }

    public long getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(long endAddress) {
        this.endAddress = endAddress;
    }
}
