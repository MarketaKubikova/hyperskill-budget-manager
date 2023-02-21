package budget;

import budget.item.Item;
import budget.item.ItemCategory;
import budget.item.ItemList;
import budget.menu.*;
import budget.util.NumberUtils;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BudgetManager implements PurchaseFileManager {

    private final Scanner scanner;
    private final Menu mainMenu;
    private final Menu addPurchaseMenu;
    private final Menu showPurchasesMenu;
    private final Menu analyzeMenu;
    private final Menu showTypeMenu;
    @Expose
    private ItemList[] purchases;
    @Expose
    private double balance;

    public BudgetManager() {
        scanner = new Scanner(System.in);
        balance = 0.0d;
        mainMenu = new MainMenu(this::handleMainMenu);
        addPurchaseMenu = new AddPurchaseMenu(this::handleAddPurchaseMenu);
        showPurchasesMenu = new ShowPurchasesMenu(this::handleShowPurchaseMenu);
        analyzeMenu = new AnalyzeMenu(this::handleShowAnalyzeMenu);
        showTypeMenu = new ShowTypeMenu(this::showTypeMenu);
        initPurchaseLists();
    }

    private void initPurchaseLists() {
        purchases = new ItemList[ItemCategory.values().length];
        for (int i = 0; i < purchases.length; i++)
            purchases[i] = new ItemList(ItemCategory.values()[i]);
    }

    public void run() {
        mainMenu.getListener().handleInput();
    }

    private void handleMainMenu() {
        mainMenu.show();
        String option = scanner.nextLine().trim();
        System.out.println();
        switch (option) {
            case "1":
                addIncome();
                break;
            case "2":
                addPurchaseMenu.getListener().handleInput();
                break;
            case "3":
                showPurchasesMenu.getListener().handleInput();
                break;
            case "4":
                showBalance();
                break;
            case "5":
                write(this);
                break;
            case "6":
                handleLoadedData();
                break;
            case "7":
                analyzeMenu.getListener().handleInput();
                break;
            case "0":
                System.out.println("Bye!");
                return;
            default:
                System.out.println("Unknown operation.");
                break;
        }
        mainMenu.getListener().handleInput();
    }

    private void handleLoadedData() {
        BudgetManager loaded = read();
        if (loaded != null) {
            balance = loaded.balance;
            purchases = loaded.purchases;
            for (ItemList list : purchases)
                list.recalculateSum();
        }
    }

    private void handleAddPurchaseMenu() {
        addPurchaseMenu.show();
        String option = scanner.nextLine().trim();
        System.out.println();
        ItemCategory category = ItemCategory.get(option);
        if (category != null)
            addPurchase(category);
        else if (option.equals("5"))
            return;
        else System.out.println("Unknown operation.");
        addPurchaseMenu.getListener().handleInput();
    }

    private void handleShowPurchaseMenu() {
        if (!isPurchaseEmpty()) {
            showPurchasesMenu.show();
            String option = scanner.nextLine().trim();
            System.out.println();
            ItemCategory category = ItemCategory.get(option);
            if (category != null) {
                purchases[category.ordinal()].print();
                purchases[category.ordinal()].printSum();
            } else if (option.equals("5"))
                printAllPurchases();
            else if (option.equals("6"))
                return;
            else System.out.println("Unknown operation.");
            showPurchasesMenu.getListener().handleInput();
        } else System.out.println("Purchase list is empty!");
    }

    private void handleShowAnalyzeMenu() {
        analyzeMenu.show();
        String option = scanner.nextLine().trim();
        System.out.println();

        switch (option) {
            case "1":
                sortAllPurchases();
                break;
            case "2":
                sortByType();
                break;
            case "3":
                showTypeMenu();
                break;
            case "4":
                return;
            default:
                System.out.println("Unknown operation.");
                break;
        }
        analyzeMenu.getListener().handleInput();
    }

    private void showTypeMenu() {
        showTypeMenu.show();
        String option = scanner.nextLine().trim();
        System.out.println();
        ItemCategory category = ItemCategory.get(option);
        if (isPurchaseEmpty()) {
            System.out.println("The purchase list is empty!");
        } else if (category != null) {
            sortCertainType(category);
        } else System.out.println("Unknown operation.");
    }

    private boolean isPurchaseEmpty() {
        for (ItemList list : purchases)
            if (!list.getList().isEmpty())
                return false;
        return true;
    }

    private void printAllPurchases() {
        double sum = 0;
        for (ItemList list : purchases) {
            if (!list.getList().isEmpty()) {
                list.print();
                sum += list.getSum();
            }
        }
        showTotalSum(sum);
    }

    private void addPurchase(ItemCategory category) {
        System.out.println("Enter purchase name:");
        String name = scanner.nextLine().replaceAll("\\s+", " ");
        System.out.println("Enter its price:");
        try {
            double value = parseAmount(scanner.nextLine());
            balance -= value;
            purchases[category.ordinal()].add(new Item(name, value));
            System.out.println("Purchase was added!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addIncome() {
        System.out.println("Enter income:");
        try {
            String valueString = scanner.nextLine();
            if (!valueString.contains("-")) {
                double value = parseAmount(valueString);
                balance += value;
                System.out.println("Income was added!");
            } else System.out.println("Income cannot be negative!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private double parseAmount(String value) throws NullPointerException, NumberFormatException {
        return Double.parseDouble(value.trim().replace(',', '.').replaceAll("[-+]", ""));
    }

    private void showBalance() {
        System.out.println("Balance: " + NumberUtils.formatAmount(balance));
    }

    private void showTotalSum(double sum) {
        System.out.println("Total sum: " + NumberUtils.formatAmount(sum));
    }

    private void sortAllPurchases() {
        if (isPurchaseEmpty()) {
            System.out.println("The purchase list is empty!");
        } else {
            List<Item> foodList = purchases[ItemCategory.FOOD.ordinal()].getList();
            List<Item> clothesList = purchases[ItemCategory.CLOTHES.ordinal()].getList();
            List<Item> entrtnmtList = purchases[ItemCategory.ENTERTAINMENT.ordinal()].getList();
            List<Item> otherList = purchases[ItemCategory.OTHER.ordinal()].getList();

            List<Item> list = new ArrayList<>();
            list.addAll(foodList);
            list.addAll(clothesList);
            list.addAll(entrtnmtList);
            list.addAll(otherList);

            System.out.println("All:");
            list.stream()
                    .sorted(Comparator.comparingDouble(Item::getValue).reversed())
                    .forEach(Item::print);
            double sum = list.stream().mapToDouble(Item::getValue).sum();
            showTotalSum(sum);
        }
    }

    private void sortByType() {
        ItemList foodList = purchases[ItemCategory.FOOD.ordinal()];
        ItemList clothesList = purchases[ItemCategory.CLOTHES.ordinal()];
        ItemList entrtnmtList = purchases[ItemCategory.ENTERTAINMENT.ordinal()];
        ItemList otherList = purchases[ItemCategory.OTHER.ordinal()];

        List<ItemList> list = new ArrayList<>();
        list.add(foodList);
        list.add(clothesList);
        list.add(entrtnmtList);
        list.add(otherList);

        list.sort(Comparator.comparing(ItemList::getSum).reversed());

        System.out.println("Types:");
        list.forEach(item -> System.out.println(item.getCategoryName() + " - " + NumberUtils.formatAmount(item.getSum())));
        double sum = list.stream().mapToDouble(ItemList::getSum).sum();
        System.out.println("Total sum: " + NumberUtils.formatAmount(sum));
    }

    private void sortCertainType(ItemCategory category) {
        purchases[category.ordinal()].getList().sort(Comparator.comparing(Item::getValue).reversed());
        purchases[category.ordinal()].print();
        purchases[category.ordinal()].printSum();
    }
}
