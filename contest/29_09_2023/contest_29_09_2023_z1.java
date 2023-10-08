import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class contest_29_09_2023_z1 {

    public static void main(String[] args) {
        contest_29_09_2023_z1 obj = new contest_29_09_2023_z1();

        System.out.println("Enter 2 strings for Anagram check");
        Scanner scanner = new Scanner(System.in);

        System.out.print("s1: ");
        String s1 = scanner.nextLine();

        System.out.print("s2: ");
        String s2 = scanner.nextLine();
//        obj.printMap(fillMap("abcabda"));
        System.out.println(obj.anagramCheck(s1, s2));
    }

    public void printMap(final Map<?, ?> map) {
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static HashMap<Character, Integer> fillMap(String s) {

        HashMap<Character, Integer> chars = new HashMap<Character, Integer>();
        int l = s.length();

        for (int i = 0; i < l; i++) {
            char c = s.charAt(i);
            if (chars.containsKey(c)) {
                chars.put(c, chars.get(c) + 1);
            }
            else {
                chars.put(c, 1);
            }
        }
        return chars;
    }

    public boolean anagramCheck(String s1, String s2){

        HashMap<Character, Integer> chars1 = fillMap(s1);
        HashMap<Character, Integer> chars2 = fillMap(s2);

        if (chars1.equals(chars2)) return true;
        else return false;
    }

}

