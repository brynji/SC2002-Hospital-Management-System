/*
package Calendars;

import java.util.List;

import Misc.Appointment;
import Users.Patient;

import java.util.ArrayList;

public class PatientCalendar extends UserCalendar {
    private Patient patient;
    private DoctorCalendar doctorCalendar;
    private List<Appointment> appointments;

    public PatientCalendar(Patient patient, DoctorCalendar doctorCalendar) {
        this.patient = patient;
        this.doctorCalendar = doctorCalendar;
        this.appointments = new ArrayList<>();
    }

    public Patient getPatient(){
        return this.patient;
    }

    @Override
    public boolean isAvailable(String date, String time) {
        return doctorCalendar.isAvailable(date, time);
    }

    @Override
    public List<String> getAvailableSlots(String date) {
        return doctorCalendar.getAvailableSlots(date);

    }
    @Override
    public boolean addAppointment(Appointment appointment, boolean acceptedOrDenied) {
        
        // Patients can only add to their own calendar if doctor is available
        if (isAvailable(appointment.getAppointmentDate(), appointment.getAppointmentTime())) {
            appointments.add(appointment);

            if (acceptedOrDenied == true) {
                doctorCalendar.addAppointment(appointment, acceptedOrDenied);
                appointment.setStatus("Confirmed");
                return true;
            }
        } 
        
        appointment.setStatus("Denied");
        removeAppointment(appointment);
        return false;

    }

    public boolean changeAppointment(Appointment currentAppointment, Appointment newAppointment, boolean acceptedOrDenied) {

        // If doctor is available on new appt timeslot, add new appt and remove old one
        if (isAvailable(newAppointment.getAppointmentDate(), newAppointment.getAppointmentTime())) {
            addAppointment(newAppointment, acceptedOrDenied);

            if (acceptedOrDenied == true) {
                removeAppointment(currentAppointment);
                doctorCalendar.addAppointment(newAppointment, acceptedOrDenied);
                doctorCalendar.removeAppointment(currentAppointment);
                newAppointment.setStatus("Confirmed");
                return true;
            }
           
        }

        newAppointment.setStatus("Denied");
        removeAppointment(newAppointment);
        return false;
    }


    @Override
    public void removeAppointment(Appointment appointment) {
        // Remove appointment and update the doctor's calendar accordingly
        doctorCalendar.removeAppointment(appointment);
        appointments.remove(appointment);
    }


    @Override
    public List<Appointment> viewAppointments(String date) {
        // Patients can only view their own appointments, not their doctors
        List<Appointment> myAppointments = new ArrayList<>();
        for (Appointment appt : appointments) {
            if (appt.getAppointmentDate().equals(date)) {
                myAppointments.add(appt);
            }
        }
        return myAppointments;
    }

}
*/
