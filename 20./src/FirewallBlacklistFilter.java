import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Svetlin Tanyi on 2016. 12. 20..
 */
public class FirewallBlacklistFilter {

    public static long minNotBlockedIP = 0;
    public static ArrayList<Rule> rules;
    public static int retryCount = 0;
    public static ArrayList<Long> nonBlockedIPs;

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        rules = new ArrayList<>();
        nonBlockedIPs = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                String[] parts = line.split("-");
                rules.add(new Rule(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (rules.size() != 0) {
            findLowestNotBlockedIP();
            if (retryCount > 6) {
                nonBlockedIPs.add(minNotBlockedIP);
                findNextNearestAddress();
                retryCount = 0;
            }
        }


        System.out.println("Lowest Not blocked IP is: " + nonBlockedIPs.get(0));
        System.out.println("Allowed IPs count: " + nonBlockedIPs.size());
    }

    private static void findNextNearestAddress() {
        long nextNearest = Long.MAX_VALUE;
        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i).getStartAddress() < nextNearest) {
                nextNearest = rules.get(i).getStartAddress();
            }
        }
        minNotBlockedIP = nextNearest;
    }

    private static void findLowestNotBlockedIP() {
        Iterator<Rule> iterator = rules.iterator();
        while (iterator.hasNext()) {
            Rule rule = iterator.next();
            if (minNotBlockedIP == 0) {
                if (rule.getStartAddress() == 0) {
                    minNotBlockedIP = rule.getEndAddress() + 1;
                    iterator.remove();
                    retryCount = 0;
                    return;
                }
            } else {
                if (rule.getStartAddress() <= minNotBlockedIP && rule.getEndAddress() < minNotBlockedIP) {
                    iterator.remove();
                }
                if (rule.getStartAddress() <= minNotBlockedIP && rule.getEndAddress() > minNotBlockedIP) {
                    minNotBlockedIP = rule.getEndAddress() + 1;
                    iterator.remove();
                    retryCount = 0;
                    System.out.println(minNotBlockedIP);
                    return;
                }
            }
        }
        retryCount++;
    }
}
