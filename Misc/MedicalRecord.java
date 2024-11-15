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
        return "Patient ID: " + patientID + "\n" +
                "Patient: " + name + "\n" +
                "DOB: " + DOB + "\n" +
                "Gender: " + gender + "\n" +
                "Blood Type: " + bloodType + "\n";
    }

    public String toString(){
        return "Id: "+patientID+", DOB: "+DOB+", Gender: "+gender+", Blood Type: "+bloodType;
    }
}
