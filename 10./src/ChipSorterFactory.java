import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Svetlin Tanyi on 10/12/16.
 */
public class ChipSorterFactory {

    public static List<Bot> bots;
    public static List<Output> outputs;
    public static List<String> commands;

    public static void main(String[] args) {
        String read, line;
        String filename = args[0];
        System.out.println(filename);

        bots = new ArrayList<>();
        outputs = new ArrayList<>();
        commands = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            while ((read = bufferedReader.readLine()) != null) {
                line = read;
                distributeChips(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            while (!commands.isEmpty()) {
                executeOrder66(commands);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(bots);
        System.out.println(commands);
        System.out.println(outputs);

        int multiply = 1;
        for (int i = 0; i < outputs.size(); i++) {
            if (outputs.get(i).getNumber() < 3) {
                System.out.println(outputs.get(i));
                int binValue = 1;
                List<Integer> bin = outputs.get(i).getBin();
                for (int j = 0; j < bin.size(); j++) {
                    binValue *= bin.get(j);
                }
                multiply *= binValue;
            }
        }

        System.out.println("Multiplied number is: " + multiply);
    }

    private static void executeOrder66(List<String> commands) throws Exception {
        Iterator commandIterator = commands.iterator();

        while (commandIterator.hasNext()) {
            String order = (String) commandIterator.next();

            executeOrder(order, commandIterator);
        }
    }

    private static void executeOrder(String order, Iterator commandIterator) throws Exception {
        String re1 = ".*?";    // Non-greedy match on filler
        String re2 = "(\\d+)";    // Integer Number 1
        String re3 = ".*?";    // Non-greedy match on filler
        String re4 = "(?:[a-z][a-z]+)";    // Uninteresting: word
        String re5 = ".*?";    // Non-greedy match on filler
        String re6 = "(?:[a-z][a-z]+)";    // Uninteresting: word
        String re7 = ".*?";    // Non-greedy match on filler
        String re8 = "(?:[a-z][a-z]+)";    // Uninteresting: word
        String re9 = ".*?";    // Non-greedy match on filler
        String re10 = "((?:[a-z][a-z]+))";    // Word 1
        String re11 = ".*?";    // Non-greedy match on filler
        String re12 = "(\\d+)";    // Integer Number 2
        String re13 = ".*?";    // Non-greedy match on filler
        String re14 = "(?:[a-z][a-z]+)";    // Uninteresting: word
        String re15 = ".*?";    // Non-greedy match on filler
        String re16 = "(?:[a-z][a-z]+)";    // Uninteresting: word
        String re17 = ".*?";    // Non-greedy match on filler
        String re18 = "(?:[a-z][a-z]+)";    // Uninteresting: word
        String re19 = ".*?";    // Non-greedy match on filler
        String re20 = "((?:[a-z][a-z]+))";    // Word 2
        String re21 = ".*?";    // Non-greedy match on filler
        String re22 = "(\\d+)";    // Integer Number 3

        Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11 + re12 + re13 + re14 + re15 + re16 + re17 + re18 + re19 + re20 + re21 + re22, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(order);
        if (m.find()) {
            int number = Integer.parseInt(m.group(1));
            String lowType = m.group(2);
            int lowNumber = Integer.parseInt(m.group(3));
            String highType = m.group(4);
            int highNumber = Integer.parseInt(m.group(5));

            boolean botExists = false;
            Bot bot = null;
            for (int i = 0; i < bots.size(); i++) {
                if (bots.get(i).getNumber() == number) {
                    bot = bots.get(i);
                    botExists = true;
                }
            }
            if (botExists) {
                if (bot.hasTwoChips()) {
                    commandIterator.remove();
                    if (highType.equals("bot")) {
                        botExists = false;
                        for (int i = 0; i < bots.size(); i++) {
                            if (bots.get(i).getNumber() == highNumber) {
                                bots.get(i).addChip(bot.removeHighChip());
                                botExists = true;
                            }
                        }

                        if (!botExists) {
                            bots.add(new Bot(highNumber, bot.removeHighChip()));
                        }
                    } else if (highType.equals("output")) {
                        botExists = false;
                        for (int i = 0; i < outputs.size(); i++) {
                            if (outputs.get(i).getNumber() == highNumber) {
                                outputs.get(i).addChipToBin(bot.removeHighChip());
                                botExists = true;
                            }
                        }
                        if (!botExists) {
                            outputs.add(new Output(highNumber, bot.removeHighChip()));
                        }
                    }

                    if (lowType.equals("bot")) {
                        botExists = false;
                        for (int i = 0; i < bots.size(); i++) {
                            if (bots.get(i).getNumber() == lowNumber) {
                                bots.get(i).addChip(bot.removeLowChip());
                                botExists = true;
                            }
                        }

                        if (!botExists) {
                            bots.add(new Bot(lowNumber, bot.removeLowChip()));
                        }
                    } else if (lowType.equals("output")) {
                        botExists = false;
                        for (int i = 0; i < outputs.size(); i++) {
                            if (outputs.get(i).getNumber() == lowNumber) {
                                outputs.get(i).addChipToBin(bot.removeLowChip());
                                botExists = true;
                            }
                        }

                        if (!botExists) {
                            outputs.add(new Output(lowNumber, bot.removeLowChip()));
                        }
                    }
                }
            }
        }
    }

    private static void distributeChips(String line) throws Exception {

        String re1 = "(value)";    // Word 1
        String re2 = ".*?";    // Non-greedy match on filler
        String re3 = "(\\d+)";    // Integer Number 1
        String re4 = ".*?";    // Non-greedy match on filler
        String re5 = "(\\d+)";    // Integer Number 2

        Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(line);
        if (m.find()) {
            int chip = Integer.parseInt(m.group(2));
            int number = Integer.parseInt(m.group(3));

            boolean botExists = false;
            for (int i = 0; i < bots.size(); i++) {
                if (bots.get(i).getNumber() == number) {
                    bots.get(i).addChip(chip);
                    botExists = true;
                }
            }

            if (!botExists) {
                bots.add(new Bot(number, chip));
            }

        } else {
            commands.add(line);
        }
    }
}
