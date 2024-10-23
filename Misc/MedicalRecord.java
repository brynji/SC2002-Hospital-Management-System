package Misc;

import java.io.Serializable;
import java.util.List;

public class MedicalRecord implements Serializable {
    
    private final String patientID;
    private String name;
    private final String DOB;
    private final String gender;
    private final String bloodType;
    private List <AppointmentOutcomeRecord> pastRecords;

    public MedicalRecord(String patientID, String name, String DOB, String gender,
    String bloodType, List<AppointmentOutcomeRecord> pastRecords) {
        this.patientID = patientID;
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
        this.bloodType = bloodType;
        this.pastRecords = pastRecords;
    }

    public MedicalRecord(String patientID, String name, String DOB, String gender, String bloodType) {
        this.patientID = patientID;
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
        this.bloodType = bloodType;
    }

    // Getters for attributes, note that there are only one setter as a medical record is meant to be immutable
     public String getPatientID() {
        return patientID;
    }

    public String DOB() {
        return DOB;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public List <AppointmentOutcomeRecord> getPastRecords() {
        return pastRecords;
    }

    public void setName(String name){ this.name = name; }

    public void AddPastAppointment(AppointmentOutcomeRecord appointment){
        pastRecords.add(appointment);
    }

    public String getDetails() {
        
        StringBuilder info = new StringBuilder();

        info.append("Medical Record of ").append(name).append("\n")
            .append(", patient ID ").append(patientID).append("\n")
            .append("Date of Birth: ").append(DOB).append("\n")
            .append("Gender: ").append(gender).append("\n")
            .append("Blood Type: ").append(bloodType).append("\n")
            .append("Past Diagnoses and Treatments:\n");

        for (AppointmentOutcomeRecord record: pastRecords) {
            info.append(record).append(record.getDetails()).append("\n");
        } 

        return info.toString();
            
    }






}
