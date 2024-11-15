package Users;

import java.util.ArrayList; // Note that we use ArrayList as it helps to create dynamic arrays that are much faster than typical List arrays

import Misc.MedicalRecord;

public class Patient extends User {

    private MedicalRecord medicalRecord;
    private ArrayList<String> appointmentsIds;

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

    public ArrayList<String> getAppointments() {
        return appointmentsIds;
    }

    public void addAppointment(String appointment) {
        appointmentsIds.add(appointment);
    }

    public void cancelAppointment(String appointmentId) {
        appointmentsIds.remove(appointmentId);
    }

    @Override
    public String toString() {
        return "Patient - " + super.toString();
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public ArrayList<String> getAppointmentsIds() {
        return appointmentsIds;
    }

    public void setAppointmentsIds(ArrayList<String> appointmentsIds) {
        this.appointmentsIds = appointmentsIds;
    }
}


