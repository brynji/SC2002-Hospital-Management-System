package Users;

import java.util.ArrayList;

public class Doctor extends User{
    private ArrayList<String> appointments;

    public Doctor(String userID, String name, String gender, String dateOfBirth, String email, String contactNumber) {
        super(userID, name, gender, dateOfBirth, email, contactNumber);
        appointments = new ArrayList<>();
    }

    public void addAppointment(String appointment){
        appointments.add(appointment);
    }

    public void removeAppointment(String appointment){ appointments.remove(appointment); }

    public ArrayList<String> getAppointments(){
        return appointments;
    }

    @Override
    public String toString() {
        return "Doctor - " + super.toString();
    }
}
