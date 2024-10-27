package Misc;

import java.io.Serializable;
import java.util.Random;
import java.util.Date;

public class Appointment implements Serializable {

    private String appointmentID;
    private String patientId;
    private String doctorId;
    private Date date;
    private Timeslot time;
    private Status status;
    private AppointmentOutcomeRecord AOR;

    public Appointment(String patientId, String doctorId,
                       Date appointmentDate, Timeslot appointmentTime) {
        this.appointmentID = generateAppointmentID(); // Generates random appt ID for each appt
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = appointmentDate;
        this.time = appointmentTime;
        this.status = Status.PENDING; // Default status set to "pending"
    }

    // Getters
    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public Date getAppointmentDate() {
        return date;
    }

    public Timeslot getAppointmentTime() {
        return time;
    }

    public Status getStatus() {
        return this.status;
    }

    public AppointmentOutcomeRecord getAOR() {
        return AOR;
    }

    // public String getServiceType() {
    //     return serviceType;
    // }

    // public List<Prescription> getMedications() {
    //     return medications;
    // }

    // public String getConsultationNotes() {
    //     return consultationNotes;
    // }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public void setAOR(AppointmentOutcomeRecord aOR) {
        AOR = aOR;
    }

    // Setters
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setAppointmentDate(Date date) {
        this.date = date;
    }

    public void setAppointmentTime(Timeslot time) {
        this.time = time;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // public void setMedications(List<Prescription> medications) {
    //     this.medications = medications;
    // }

    // public void setConsultationNotes(String consultationNotes) {
    //     this.consultationNotes = consultationNotes;
    // }

    // public void setServiceType(String serviceType) {
    //     this.serviceType = serviceType;
    // }

    /*
    public String getDetails() {

        StringBuilder info = new StringBuilder();

        info.append("Appointment ID: ").append(appointmentID).append("\n")
                .append("Patient: ").append(patientId.getName()).append("\n")
                .append("Doctor: ").append(doctorId.getName()).append("\n")
                .append("Date: ").append(date).append("\n")
                .append("Time: ").append(time).append("\n")
                // .append("Service Rendered: ").append(serviceType).append("\n")
                // .append("Medication Dispensed: ").append(medications).append("\n")
                // .append("Consultation Notes: ").append(consultationNotes).append("\n")
                .append("Status: ").append(status);

        return info.toString();
    }
    */

    // public String getAppointmentOutcomeRecord(Date date, Timeslot timeslot, String serviceType, List<Prescription> medications, String consultationNotes) {

    //     StringBuilder info = new StringBuilder();

    //     info.append("Date: ").append(date).append("\n")
    //         .append("Time: ").append(time).append("\n")
    //         .append("Service Rendered: ").append(serviceType).append("\n")
    //         .append("Medication Dispensed: ").append(medications).append("\n")
    //         .append("Consultation Notes: ").append(consultationNotes).append("\n");

    //     return info.toString();
    // }


    private static String generateAppointmentID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        // Generate first segment (4 random letters, mixed case)
        for (int i = 0; i < 4; i++) {
            char letter = (char) (random.nextBoolean() ?
                    ('A' + random.nextInt(26)) : // Uppercase
                    ('a' + random.nextInt(26))); // Lowercase
            id.append(letter);
        }
        id.append("-");

        // Generate second segment (4 random letters, mixed case)
        for (int i = 0; i < 4; i++) {
            char letter = (char) (random.nextBoolean() ?
                    ('A' + random.nextInt(26)) :
                    ('a' + random.nextInt(26)));
            id.append(letter);
        }
        id.append("-");

        // Generate third and fourth segments (4 random digits each)
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                int digit = random.nextInt(10);
                id.append(digit);
            }
            if (j == 0) id.append("-");
        }

        return id.toString();
    }

}
