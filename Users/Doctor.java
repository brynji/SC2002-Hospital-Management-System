package Users;

import Misc.Appointment;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User{
    private List<Misc.Appointment> appointments;

    public Doctor(String userID, String name, String email, String contactNumber){
        super(userID,name,email,contactNumber);
        appointments = new ArrayList<Appointment>();
    }

    public void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }


}
