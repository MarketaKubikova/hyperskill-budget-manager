package budget.item;

public enum ItemCategory {

    FOOD("Food", "1"),
    CLOTHES("Clothes", "2"),
    ENTERTAINMENT("Entertainment", "3"),
    OTHER("Other", "4");

    final String name;
    final String value;

    ItemCategory(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static ItemCategory get(String value) {
        for (ItemCategory i : values())
            if (i.value.equals(value))
                return i;
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
