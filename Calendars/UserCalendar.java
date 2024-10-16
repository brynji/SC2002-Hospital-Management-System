package Calendars;
import java.util.List;

import Misc.Appointment;

public abstract class UserCalendar {

    protected List<Appointment> appointments;
    
    public abstract boolean isAvailable(String date, String time);
    
    public abstract List<String> getAvailableSlots(String date);
    
    public abstract boolean addAppointment(Appointment appointment, boolean acceptOrDeny);
    
    public abstract void removeAppointment(Appointment appointment);
    
    public abstract List<Appointment> viewAppointments(String date);

}

