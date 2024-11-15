package Misc;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class AppointmentOutcomeRecord implements Serializable {

    private final String recordID;
    private LocalDate appointmentDate;
    private String serviceType;
    private List<Prescription> prescriptions;
    private String consultationNotes;
    private String status; // Pending or completed

    public AppointmentOutcomeRecord(String recordID,
                                    LocalDate appointmentDate, String serviceType,
                                    List<Prescription> prescriptions, String consultationNotes) {

        this.recordID = recordID;
        this.appointmentDate = appointmentDate;
        this.serviceType = serviceType;
        this.prescriptions = prescriptions;
        this.consultationNotes = consultationNotes;
        this.status = "pending"; // Default status is set to "pending"
    }

    // Setters 
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }


    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }


    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
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

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getServiceType() {
        return serviceType;
    }


    public String getConsultationNotes() {
        return consultationNotes;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public String getStatus() {
        return status;
    }

    public String toString() {

        StringBuilder info = new StringBuilder();
        
        info.append("Appointment Outcome Record:\n")
        .append("Record ID: ").append(recordID).append("\n")
        .append("Date: ").append(appointmentDate).append("\n")
        .append("Service Type: ").append(serviceType).append("\n")
        .append("Medications:\n");

        for (Prescription prescription: prescriptions) {
            info.append(" - ").append(prescription.getDetails()).append("\n");
        }

        info.append("Consultation Notes: ").append(consultationNotes).append("\n")
            .append("Status: ").append(status);

        return info.toString();

    }


}
