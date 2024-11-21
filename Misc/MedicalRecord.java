package Misc;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Medical record of patient
 */
public class MedicalRecord implements Serializable {
    
    private final String patientID;
    private String name;
    private final String DOB;
    private final String gender;
    private final String bloodType;
    private final ArrayList<String> diagnosisAndTreatments;
    private final List <String> pastAppointmentRecordsIds;

    /**
     * Initializes Medical Record class
     * @param patientID unique ID of the patient that has this medical record
     * @param name full name of the Patient
     * @param DOB date of birth of the Patient, in dd/mm/yyyy format
     * @param gender gender of Patient
     * @param bloodType blood type of Patient
     * @param diagnosisAndTreatments all diagnosis and treatments prescribed by doctors
     * @param pastAppointmentRecordsIds IDs or records of past appointments
     * @throws IllegalArgumentException when date is not in dd/mm/yyyy format, or is in future
     */
    public MedicalRecord(String patientID, String name, String DOB, String gender, String bloodType, ArrayList<String> diagnosisAndTreatments, List<String> pastAppointmentRecordsIds) {
        if(DateHelper.isValidDateOfBirth(DOB)) throw new IllegalArgumentException("DOB is invalid");
        this.patientID = patientID;
        this.name = name;
        this.DOB = DOB;
        this.gender = gender;
        this.bloodType = bloodType;
        this.diagnosisAndTreatments = diagnosisAndTreatments;
        this.pastAppointmentRecordsIds = pastAppointmentRecordsIds;
    }

    /**
     * Initializes Medical Record class
     * @param patientID unique ID of the patient that has this medical record
     * @param name full name of the Patient
     * @param DOB date of birth of the Patient, in dd/mm/yyyy format
     * @param gender gender of Patient
     * @param bloodType blood type of Patient
     * @throws IllegalArgumentException when date is not in dd/mm/yyyy format, or is in future
     */
    public MedicalRecord(String patientID, String name, String DOB, String gender,
                         String bloodType) {
        if(!DateHelper.isValidDateOfBirth(DOB)) throw new IllegalArgumentException("DOB is invalid");
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

    public String toString(){
        StringBuilder res = new StringBuilder("Id: " + patientID + ", DOB: " + DOB + ", Gender: " + gender + ", Blood Type: " + bloodType + "\nDiagnosis and Treatments:");
        for(String diagnosisAndTreatment: diagnosisAndTreatments){
            res.append("\n- ").append(diagnosisAndTreatment);
        }
        return res.toString();
    }
}
