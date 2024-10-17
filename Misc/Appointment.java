package Misc;

import java.io.Serializable;
import java.util.Random;

import Users.Doctor;
import Users.Patient;

public class Appointment implements Serializable {
    
    private String appointmentID;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;
    private String status;

    public Appointment(Patient patient, Doctor doctor, 
        String appointmentDate, String appointmentTime) {
        this.appointmentID = generateAppointmentID(); // Generates random appt ID for each appt
        this.patient = patient;
        this.doctor = doctor;
        this.date = appointmentDate;
        this.time = appointmentTime;
        this.status = "pending"; // Default status set to "pending"
    }

    // Getters
    public String getAppointmentID() {
        return appointmentID;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getAppointmentDate() {
        return date;
    }

    public String getAppointmentTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setAppointmentDate(String date) {
        this.date = date;
    }

    public void setAppointmentTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {

        StringBuilder info = new StringBuilder();

        info.append("Appointment ID: ").append(appointmentID).append("\n")
            .append("Patient: ").append(patient.getName()).append("\n")
            .append("Doctor: ").append(doctor.getName()).append("\n")
            .append("Date: ").append(date).append("\n")
            .append("Time: ").append(time).append("\n")
            .append("Status: ").append(status);

        return info.toString();
    }

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
