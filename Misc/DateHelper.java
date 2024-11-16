package Misc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DateHelper {

    public static boolean isValidDateOfBirth(String date) {
        LocalDate parsedDate;
        try{
            parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException e) {
            return false;
        }
        return parsedDate.isBefore(LocalDate.now());
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT));
    }

    public static List<LocalDate> getDatesNext7Days() {
        List<LocalDate> next7Days = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            next7Days.add(today.plusDays(i));
        }
        return next7Days;
    }

    public static List<DateTimeslot> getTimeslotsNext7Days(){
        return getDatesNext7Days().stream().flatMap(date->
                Arrays.stream(Timeslot.values()).map(timeslot->
                        new DateTimeslot(date, timeslot))).toList();
    }
}
