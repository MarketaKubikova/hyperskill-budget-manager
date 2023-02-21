package budget.util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

    public static String formatAmount(double amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount);
    }
}
