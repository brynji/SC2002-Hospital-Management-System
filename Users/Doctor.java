package Users;

import Misc.Appointment;

import java.util.ArrayList;

public class Doctor extends User{
    private ArrayList<Appointment> appointments;

    public Doctor(String userID, String name, String email, String contactNumber){
        super(userID,name,email,contactNumber);
        appointments = new ArrayList<Appointment>();
    }

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }

    public ArrayList<Appointment> getAppointments(){
        return appointments;
    }
}
