package Misc;

import java.util.List;

public class MedicalRecord {
    
    private String patientID;
    private String name;
    private String DOB;
    private String gender;
    private String email;
    private String contactNumber;
    private String bloodType;
    private List <AppointmentOutcomeRecord> pastRecords;

    public MedicalRecord(String patientID, String name, String DOB, String gender,
    String email, String contactNumber, String bloodType, List<AppointmentOutcomeRecord> pastRecords) {

        this.patientID = patientID;
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
        this.email = email;
        this.contactNumber = contactNumber;
        this.bloodType = bloodType;
        this.pastRecords = pastRecords;
    }

     // Getters for attributes, note that there are only two setters as a medical record is meant to be immutable
     public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public String DOB() {
        return DOB;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getBloodType() {
        return bloodType;
    }

    // Setters for email and contact number only 
    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDetails() {
        
        StringBuilder info = new StringBuilder();

        info.append("Medical Record of ").append(name)
            .append(", patient ID ").append(patientID).append("\n")
            .append("Name: ").append(name).append("\n")
            .append("Date of Birth: ").append(DOB).append("\n")
            .append("Gender: ").append(gender).append("\n")
            .append("Email: ").append(email).append("\n")
            .append("Contact Number: ").append(contactNumber).append("\n")
            .append("Blood Type: ").append(bloodType).append("\n")
            .append("Past Diagnoses and Treatments:\n");

        for (AppointmentOutcomeRecord record: pastRecords) {
            info.append(record).append(record.getDetails()).append("\n");
        } 

        return info.toString();
            
    }






}
