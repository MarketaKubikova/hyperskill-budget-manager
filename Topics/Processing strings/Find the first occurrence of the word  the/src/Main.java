import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String sentence = scanner.nextLine().toLowerCase();

        if (sentence.contains("the")) {
            System.out.println(sentence.indexOf("the"));
        } else {
            System.out.println(-1);
        }
    }
}
