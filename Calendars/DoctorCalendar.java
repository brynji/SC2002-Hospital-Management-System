/*
package Calendars;

import java.util.ArrayList;
import java.util.List;

import Misc.Appointment;
import Users.Doctor;

public class DoctorCalendar extends UserCalendar {
    private Doctor doctor;

    public DoctorCalendar(Doctor doctor) {
        this.doctor = doctor;
        this.appointments = new ArrayList<>();
    }

    public Doctor getDoctor(){
        return this.doctor;
    }

    @Override
    public boolean isAvailable(String date, String time) {
        // Logic to check if the doctor is available at a specific date and time
        for (Appointment appt : appointments) {
            if (appt.getAppointmentDate().equals(date) && appt.getAppointmentTime().equals(time)) {
                return false; // Slot taken
            }
        }
        return true;
    }

    // TODO: IMPLEMENT LOGIC FOR THIS STUPID FUCKING FUNCTION

    @Override
    public List<String> getAvailableSlots(String date) {
        // Return a list of available time slots for the doctor on the given date 
        List<String> availableSlots = new ArrayList<>();
        // Logic to determine and add available slots to the list
        return availableSlots;
    }

    @Override
    public boolean addAppointment(Appointment appointment, boolean acceptOrDeny) {

        if (isAvailable(appointment.getAppointmentDate(), appointment.getAppointmentTime()) && acceptOrDeny == true) {
            appointments.add(appointment);
            return true;
        } 
        
        else {
            return false;
        }
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    @Override
    public List<Appointment> viewAppointments(String date) {
        List<Appointment> dailyAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getAppointmentDate().equals(date)) {
                dailyAppointments.add(appt);
            }
        }
        return dailyAppointments;
    }
    
    
}
*/