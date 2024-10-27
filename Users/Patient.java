package Users;

import java.util.ArrayList; // Note that we use ArrayList as it helps to create dynamic arrays that are much faster than typical List arrays

import Misc.MedicalRecord;

public class Patient extends User {

    private final String patientID;
    private MedicalRecord medicalRecord;
    private ArrayList<String> appointmentsIds;

    public Patient(String userID, String name, String email, String contactNumber, 
                   String patientID, MedicalRecord medicalRecord) {
        super(userID, name, medicalRecord.getGender(), medicalRecord.DOB(), email, contactNumber);
        this.patientID = patientID;
        this.medicalRecord = medicalRecord;
        this.appointmentsIds = new ArrayList<>(); // Initialize the appointments list
    }

    // Getters, note that there are no setters as the patient's medical info is immutable
    public String getPatientID() {
        return patientID;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void viewMedicalRecord() {
        System.out.println(medicalRecord.getDetails()); // Get detailed view from MedicalRecord
    }

    public ArrayList<String> getAppointments() {
        return appointmentsIds;
    }

    public void addAppointment(String appointment) {
        appointmentsIds.add(appointment);
    }

    public void rescheduleAppointment(){
        /*  if new date && new time are clear in doctor's schedule {
            cancel last appt    
            book new appt 
            }
        */
    }

    public void cancelAppointment(String appointmentId) {
        appointmentsIds.remove(appointmentId);
    }

    public void viewScheduledAppointments(){
        /* 
        return all appts
        */        
    }


    @Override
    public String toString() {
        return "Patient - " + super.toString();
    }
}


