package Misc;

import java.util.List;

public class AppointmentOutcomeRecord {

    private String recordID;
    private String appointmentDate;
    private String serviceType;
    private List<Prescription> medications;
    private String consultationNotes;
    private String status; // Pending or completed

    public AppointmentOutcomeRecord(String recordID, 
        String appointmentDate, String serviceType, 
        List<Prescription> medications, String consultationNotes) {

        this.recordID = recordID;
        this.appointmentDate = appointmentDate;
        this.serviceType = serviceType;
        this.medications = medications;
        this.consultationNotes = consultationNotes;
        this.status = "pending"; // Default status is set to "pending"

    }

    // Setters 
    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }


    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }


    public void setMedications(List<Prescription> medications) {
        this.medications = medications;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getters 
    public String getRecordID() {
        return recordID;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getServiceType() {
        return serviceType;
    }


    public String getConsultationNotes() {
        return consultationNotes;
    }

    public List<Prescription> getMedications() {
        return medications;
    }

    public String getStatus() {
        return status;
    }

    public String getDetails() {

        StringBuilder info = new StringBuilder();
        
        info.append("Appointment Outcome Record:\n")
        .append("Record ID: ").append(recordID).append("\n")
        .append("Date: ").append(appointmentDate).append("\n")
        .append("Service Type: ").append(serviceType).append("\n")
        .append("Medications:\n");

        for (Prescription prescription: medications) {
            info.append("  - ").append(prescription.getDetails()).append("\n"); 
        }

        info.append("Consultation Notes: ").append(consultationNotes).append("\n")
            .append("Status: ").append(status);

        return info.toString();

    }

}
