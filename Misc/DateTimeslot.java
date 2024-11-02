package Misc;

import java.time.LocalDate;
import java.util.Objects;

public class DateTimeslot implements Comparable<DateTimeslot>{
    private LocalDate date;
    private Timeslot timeslot;

    public DateTimeslot(LocalDate date, Timeslot timeslot) {
        this.date = date;
        this.timeslot = timeslot;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    @Override
    public int compareTo(DateTimeslot o) {
        int res = date.compareTo(o.getDate());
        if(res == 0)
            return timeslot.ordinal() - o.timeslot.ordinal();
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateTimeslot that = (DateTimeslot) o;
        return Objects.equals(date, that.date) && timeslot == that.timeslot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, timeslot);
    }
}
