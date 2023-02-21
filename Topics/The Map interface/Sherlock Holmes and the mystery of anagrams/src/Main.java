import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String firstInput = scanner.nextLine().toLowerCase();
        String secondInput = scanner.nextLine().toLowerCase();

        String answer = isAnagram(firstInput, secondInput) ? "yes" : "no";

        System.out.println(answer);
    }

    private static boolean isAnagram(String firstInput, String secondInput) {
        Map<Character, Integer> firstMap = new HashMap<>();
        Map<Character, Integer> secondMap = new HashMap<>();

        for (Character c : firstInput.toCharArray()) {
            if (firstMap.containsKey(c)) {
                firstMap.put(c, firstMap.get(c) + 1);
            } else {
                firstMap.put(c, 1);
            }
        }

        for (Character c : secondInput.toCharArray()) {
            if (secondMap.containsKey(c)) {
                secondMap.put(c, secondMap.get(c) + 1);
            } else {
                secondMap.put(c, 1);
            }
        }

        return firstMap.equals(secondMap);
    }
}
