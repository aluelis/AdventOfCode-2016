/**
 * Created by Svetlin Tanyi on 05/12/16.
 */
public class DoorPasswordBreaker {

    //public static String password = "";
    public static String[] password;
    public static boolean[] passwordIndex;
    public static int foundHashed = 0;
    public static int leapCount = 0;

    public static void main(String[] args) {
        String input = args[0];
        System.out.println(input);
        password = new String[8];
        passwordIndex = new boolean[8];

        for (int i = 0; i < passwordIndex.length; i++) {
            passwordIndex[i] = false;
        }

        while (foundHashed != 8) {
            String md5 = input + leapCount;
            MD5(md5);
            leapCount++;
        }

        for (String aPassword : password) {
            System.out.print(aPassword);
        }
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            String hash = sb.toString();
            if (hash.substring(0, 5).equals("00000")) {
                System.out.println(hash + " leapCount: " + leapCount);
                String index = hash.substring(5, 6);
                if (index.equals("0") || index.equals("1") || index.equals("2") || index.equals("3") || index.equals("4") || index.equals("5") || index.equals("6") || index.equals("7")) {
                    int indexInt = Integer.parseInt(index);
                    if (!passwordIndex[indexInt]) {
                        passwordIndex[indexInt] = true;
                        password[Integer.parseInt(index)] = hash.substring(6, 7);
                        foundHashed++;
                    }
                }
                return hash;
            }
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
