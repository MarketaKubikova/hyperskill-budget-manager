import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        String[] array = input.split("-");

        System.out.println("%s/%s/%s".formatted(array[1], array[2], array[0]));
    }
}
