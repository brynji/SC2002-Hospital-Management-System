package Users;

import java.util.ArrayList; // Note that we use ArrayList as it helps to create dynamic arrays that are much faster than typical List arrays
import java.util.List;

import Misc.Appointment;
import Misc.MedicalRecord;

public class Patient extends User {

    private String patientID;
    private MedicalRecord medicalRecord;
    private List<Appointment> appointments;

    public Patient(String userID, String name, String email, String contactNumber, 
                   String patientID, MedicalRecord medicalRecord) {
        super(userID, name, email, contactNumber);
        this.patientID = patientID;
        this.medicalRecord = medicalRecord;
        this.appointments = new ArrayList<>(); // Initialize the appointments list
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

    public List<Appointment> getAppointment() {
        return appointments;
    }

    public void scheduleAppointment(){
        
        /*  if date && time are clear in doctor's schedule {
                book appt 
            }
        */
    }

    public void rescheduleAppointment(){
        /*  if new date && new time are clear in doctor's schedule {
            cancel last appt    
            book new appt 
            }
        */
    }

    public void cancelAppointment() {
        /*  if date && time are booked in doctor's schedule {
                delete appt 
            }
        */        
    }

    public void viewScheduledAppointments(){
        /* 
        return all appts
        */        
    }


    @Override
    public String toString() {
        return "Patient{" +
                super.toString()+
                "patientID='" + patientID + '\'' +
                ", medicalRecord=" + medicalRecord +
                ", appointments=" + appointments +
                '}';
    }
}


