package Misc;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class MedicalRecord implements Serializable {
    
    private final String patientID;
    private String name;
    private final String DOB;
    private final String gender;
    private final String bloodType;
    private final ArrayList<String> diagnosisAndTreatments;
    private final List <String> pastAppointmentRecordsIds;

    public MedicalRecord(String patientID, String name, String DOB, String gender, String bloodType, ArrayList<String> diagnosisAndTreatments, List<String> pastAppointmentRecordsIds) {
        this.patientID = patientID;
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
        this.bloodType = bloodType;
        this.diagnosisAndTreatments = diagnosisAndTreatments;
        this.pastAppointmentRecordsIds = pastAppointmentRecordsIds;
    }

    public MedicalRecord(String patientID, String name, String DOB, String gender,
                         String bloodType) {
        this.patientID = patientID;
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
        this.bloodType = bloodType;
        this.diagnosisAndTreatments = new ArrayList<>();
        this.pastAppointmentRecordsIds = new ArrayList<>();
    }

    // Getters for attributes, note that there are only one setter as a medical record is meant to be immutable
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

    public String getBloodType() {
        return bloodType;
    }

    public List <String> getPastAppointmentRecordsIds() {
        return pastAppointmentRecordsIds;
    }

    public void setName(String name){ this.name = name; }

    public void AddDiagnosisAndTreatment(String diagnosisAndTreatment){
        diagnosisAndTreatments.add(diagnosisAndTreatment);
    }

    public void AddPastAppointment(String AOR){
        pastAppointmentRecordsIds.add(AOR);
    }

    public String getDetails() {

        StringBuilder info = new StringBuilder();

        info.append("Patient ID: ").append(patientID).append("\n")
                .append("Patient: ").append(name).append("\n")
                .append("DOB: ").append(DOB).append("\n")
                .append("Gender: ").append(gender).append("\n")
                .append("Blood Type: ").append(bloodType).append("\n");

        return info.toString();
    }

    public String toString(){
        return "Id: "+patientID+", DOB: "+DOB+", Gender: "+gender+", Blood Type: "+bloodType;
    }
}
