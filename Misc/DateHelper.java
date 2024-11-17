package Misc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used for operations with dates
 */
public class DateHelper {

    /**
     * Checks if argument is in dd/mm/yyyy format and is not in future
     * @param date date to check
     * @return true if date is in dd/mm/yyyy format and not in future, false otherwise
     */
    public static boolean isValidDateOfBirth(String date) {
        LocalDate parsedDate;
        try{
            parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return false;
        }
        return parsedDate.isBefore(LocalDate.now());
    }

    /**
     * Tries to parse given date
     * @throws DateTimeParseException date in not in dd/mm/yyyy format
     * @param date date to parse, should be in dd/mm/yyyy format
     * @return parsed LocalDate
     */
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
    }

    /**
     * Get dates for today and 6 days after that
     * @return List of today's date and 6 days after that
     */
    public static List<LocalDate> getDatesNext7Days() {
        List<LocalDate> next7Days = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            next7Days.add(today.plusDays(i));
        }
        return next7Days;
    }

    /**
     * Get List of all timeslots today and 6 following days
     * @return List of dates and timeslots for today and 6 following days
     */
    public static List<DateTimeslot> getTimeslotsNext7Days(){
        return getDatesNext7Days().stream().flatMap(date->
                Arrays.stream(Timeslot.values()).map(timeslot->
                        new DateTimeslot(date, timeslot))).toList();
    }
}
