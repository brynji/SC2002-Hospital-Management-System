package Misc;

import java.io.Serializable;
import java.util.List;

public class MedicalRecord implements Serializable {
    
    private final String patientID;
    private String name;
    private final String DOB;
    private final String gender;
    private final String bloodType;
    private List <String> pastAppointmentRecordsIds;

    public MedicalRecord(String patientID, String name, String DOB, String gender,
    String bloodType, List<String> pastAppointmentRecordsIds) {
        this.patientID = patientID;
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
        this.bloodType = bloodType;
        this.pastAppointmentRecordsIds = pastAppointmentRecordsIds;
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

    public List <String> getPastAppointmentRecordsIds() {
        return pastAppointmentRecordsIds;
    }

    public void setName(String name){ this.name = name; }

    public void AddPastAppointment(String appointmentId){
        pastAppointmentRecordsIds.add(appointmentId);
    }

}
