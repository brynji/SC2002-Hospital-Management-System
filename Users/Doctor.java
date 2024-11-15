package Users;

import java.util.ArrayList;

public class Doctor extends User{
    private boolean availableForNewAppointments;
    private final ArrayList<String> appointments;

    public Doctor(String userID, String name, String gender, String dateOfBirth, String email, String contactNumber, boolean availableForNewAppointments) {
        super(userID, name, gender, dateOfBirth, email, contactNumber);
        this.availableForNewAppointments = availableForNewAppointments;
        appointments = new ArrayList<>();
    }

    public boolean isAvailableForNewAppointments() {
        return availableForNewAppointments;
    }

    public void setAvailableForNewAppointments(boolean availableForNewAppointments) {
        this.availableForNewAppointments = availableForNewAppointments;
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
