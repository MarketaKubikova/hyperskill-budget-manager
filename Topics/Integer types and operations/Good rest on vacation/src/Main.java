import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int days = scanner.nextInt();
        int foodCost = scanner.nextInt();
        int flightCost = scanner.nextInt();
        int hotelCost = scanner.nextInt();

        int totalCosts = foodCost * days + hotelCost * (days - 1) + 2 * flightCost;

        System.out.println(totalCosts);
    }
}
