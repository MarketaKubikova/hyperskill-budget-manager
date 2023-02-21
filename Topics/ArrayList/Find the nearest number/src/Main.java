import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        int n = scanner.nextInt();

        String[] inputArray = input.split(" ");

        ArrayList<Integer> outputList = getNearest(inputArray, n);

        for (int i = 0; i < outputList.size(); i++) {
            if (i == outputList.size() - 1) {
                System.out.print(outputList.get(i));
            } else {
                System.out.print(outputList.get(i) + " ");
            }
        }
    }

    private static ArrayList<Integer> getNearest(String[] array, int n) {
        int difference;
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<Integer> differenceList = new ArrayList<>();
        ArrayList<Integer> resultList = new ArrayList<>();

        for (String s : array) {
            arrayList.add(Integer.parseInt(s));
        }

        for (int element : arrayList) {
            difference = Math.abs(element - n);
            differenceList.add(difference);
        }

        ArrayList<Integer> copyDifferenceList = new ArrayList<>(differenceList);
        copyDifferenceList.sort(Comparator.naturalOrder());

        int min = copyDifferenceList.get(0);

        for (int i = 0; i < differenceList.size(); i++) {
            if (differenceList.get(i).equals(min)) {
                resultList.add(arrayList.get(i));
            }
        }

        resultList.sort(Comparator.naturalOrder());

        return resultList;
    }
}
