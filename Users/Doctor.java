package Users;

import java.util.ArrayList;

/**
 * Doctor class in our hospital system
 */
public class Doctor extends User{
    private boolean availableForNewAppointments;
    private final ArrayList<String> appointments;

    /**
     * Initializes Doctors class
     * @throws IllegalArgumentException for invalid date of birth
     * @param userID Hospital-unique ID, used for login
     * @param name Full Name
     * @param gender Gender of the user
     * @param dateOfBirth Date of birth in dd/mm/yyyy format
     * @param email Email address
     * @param contactNumber Contact number, can include country prefix
     * @param availableForNewAppointments If true, patients can schedule appointments with this doctor
     */
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

    /**
     * Adds new appointment to the collection stored in this doctor
     * @param appointmentId appointmentId to add
     */
    public void addAppointment(String appointmentId){
        appointments.add(appointmentId);
    }

    /**
     * Removes appointmentId from collection stored in this doctor
     * @param appointmentId appointmentId to remove
     */
    public void removeAppointment(String appointmentId){ appointments.remove(appointmentId); }

    /**
     * Get Ids or all past and upcoming appointments of this patient
     * @return ArrayList with Ids of all appointments of this patient
     */
    public ArrayList<String> getAppointments(){
        return appointments;
    }

    @Override
    public String toString() {
        return "Doctor - " + super.toString() + ", availableForNewAppointments: " + availableForNewAppointments;
    }
}
