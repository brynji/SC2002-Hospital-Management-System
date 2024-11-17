package Misc;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Record made by doctor after appointment is over
 */
public class AppointmentOutcomeRecord implements Serializable {
    private final String recordID;
    private LocalDate appointmentDate;
    private String serviceType;
    private List<Prescription> prescriptions;
    private String consultationNotes;
    private String status; // Pending or completed

    /**
     * Initializes AppointmentOutcomeRecord class
     * @param recordID ID of the appointment, that has this outcome record
     * @param appointmentDate date of the appointment, from which is this record
     * @param serviceType description of the service doctor did
     * @param prescriptions prescriptions prescribed by doctor on this appointment
     * @param consultationNotes notes from this appointment
     */
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

    /**
     * Set status of AOR, valid statuses are "pending" and "dispensed"
     * @param status "pending" or "dispensed"
     */
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

    /**
     * Get status of this AOR
     * @return "dispensed" after all prescriptions are dispensed, "pending" until that
     */
    public String getStatus() {
        return status;
    }

    public String toString() {

        StringBuilder info = new StringBuilder();
        
        info.append("Appointment Outcome Record: ")
        .append("Record ID: ").append(recordID).append(", ")
        .append("Date: ").append(appointmentDate).append(", ")
        .append("Status: ").append(status).append(", ")
        .append("Service Type: ").append(serviceType).append("\n")
        .append("Medications:\n");

        for (Prescription prescription: prescriptions) {
            info.append(" - ").append(prescription.toString()).append("\n");
        }

        info.append("Consultation Notes: ").append(consultationNotes);

        return info.toString();

    }


}
