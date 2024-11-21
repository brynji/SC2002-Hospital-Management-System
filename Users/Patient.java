package Users;

import java.util.ArrayList; // Note that we use ArrayList as it helps to create dynamic arrays that are much faster than typical List arrays

import Misc.MedicalRecord;

/**
 * Patient class in our hospital system
 */
public class Patient extends User {
    private final MedicalRecord medicalRecord;
    private final ArrayList<String> appointmentsIds;

    /**
     * Initializes Patient class
     * @throws IllegalArgumentException for invalid date of birth
     * @param userID Hospital-unique ID, used for login
     * @param name Full Name
     * @param email Email address
     * @param contactNumber Contact number, can include country prefix
     * @param medicalRecord Medical record of this patient
     */
    public Patient(String userID, String name, String email, String contactNumber, 
                   MedicalRecord medicalRecord) {
        super(userID, name, medicalRecord.getGender(), medicalRecord.DOB(), email, contactNumber);
        this.medicalRecord = medicalRecord;
        this.appointmentsIds = new ArrayList<>(); // Initialize the appointments list
    }

    // Getters, note that there are no setters as the patient's medical info is immutable

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        medicalRecord.setName(name);
    }

    /**
     * Get Ids or all past and upcoming appointments of this patient
     * @return ArrayList with Ids of all appointments of this patient
     */
    public ArrayList<String> getAppointments() {
        return appointmentsIds;
    }

    /**
     * Adds new appointment to the collection stored in this doctor
     * @param appointmentId appointmentId to add
     */
    public void addAppointment(String appointmentId) {
        appointmentsIds.add(appointmentId);
    }

    /**
     * Removes appointmentId from collection stored in this patient
     * @param appointmentId appointmentId to remove
     */
    public void removeAppointment(String appointmentId) {
        appointmentsIds.remove(appointmentId);
    }

    @Override
    public String toString() {
        return "Patient - " + super.toString();
    }
}


