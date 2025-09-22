package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
    /**
     * Return next business day
     * @return Date in String
     */
    public static String getNextBusinessDay() {
        // Current date
        LocalDate today = LocalDate.now();
        LocalDate next;

        switch (today.getDayOfWeek()) {
            case FRIDAY -> next = today.plusDays(3);
            case SATURDAY -> next = today.plusDays(2);
            default -> next = today.plusDays(1);
        }

        // Format to YYYY-MM-dd
        return next.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
