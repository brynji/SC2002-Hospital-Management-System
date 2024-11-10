package Misc;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

public class Appointment implements Serializable, Comparable<Appointment> {

    private String appointmentID;
    private String patientId;
    private String doctorId;
    private LocalDate date;
    private Timeslot time;
    private Status status;
    private AppointmentOutcomeRecord AOR;

    public Appointment(String patientId, String doctorId,
                       LocalDate appointmentDate, Timeslot appointmentTime) {
        this.appointmentID = generateAppointmentID(); // Generates random appt ID for each appt
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = appointmentDate;
        this.time = appointmentTime;
        this.status = Status.PENDING; // Default status set to "pending"
    }

    public boolean isOverlapping(LocalDate date, Timeslot timeslot) {
        if(status==Status.REJECTED || status==Status.CANCELLED)
            return false;
        return this.date.equals(date) && this.time.equals(timeslot);
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

    public LocalDate getDate() {
        return date;
    }

    public Timeslot getTime() {
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

    public void setAppointmentDate(LocalDate date) {
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

    // TODO: Get docor and patient name from doctor and patient ID so that it is more readable (easier to remember a doctor's name than a random ID number)
    public String getDetails() {

        StringBuilder info = new StringBuilder();

        info.append("Appointment ID: ").append(appointmentID).append("\n")
                .append("Patient ID : ").append(patientId).append("\n")
                .append("Doctor ID: ").append(doctorId).append("\n")
                .append("Date: ").append(date).append("\n")
                .append("Time: ").append(time).append("\n")
                // .append("Service Rendered: ").append(serviceType).append("\n")
                // .append("Medication Dispensed: ").append(medications).append("\n")
                // .append("Consultation Notes: ").append(consultationNotes).append("\n")
                .append("Status: ").append(status);

        return info.toString();
    }
    

    // public String getAppointmentOutcomeRecord(Date date, Timeslot timeslot, String serviceType, List<Prescription> medications, String consultationNotes) {

    //     StringBuilder info = new StringBuilder();

    //     info.append("Date: ").append(date).append("\n")
    //         .append("Time: ").append(time).append("\n")
    //         .append("Service Rendered: ").append(serviceType).append("\n")
    //         .append("Medication Dispensed: ").append(medications).append("\n")
    //         .append("Consultation Notes: ").append(consultationNotes).append("\n");

    //     return info.toString();
    // }

    //TODO one function to generate all ids (user,patient,app,...)
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

    @Override
    public int compareTo(Appointment o) {
        int res = date.compareTo(o.getDate());
        if(res == 0)
            return time.ordinal() - o.time.ordinal();
        return res;
    }
}
