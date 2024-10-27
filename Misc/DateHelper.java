package Misc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateHelper {

    public static List<Date> getNext7Days() {
        List<Date> next7Days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);  // Start from tomorrow

        for (int i = 0; i < 7; i++) {
            next7Days.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_YEAR, 1);  // Move to the next day
        }

        return next7Days;
    }
}
