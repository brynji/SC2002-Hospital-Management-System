package Misc;

import java.io.Serializable;
import java.time.LocalDate;

public class Appointment implements Serializable, Comparable<Appointment> {

    private final String appointmentID;
    private final String patientId;
    private final String doctorId;
    private LocalDate date;
    private Timeslot time;
    private AppointmentStatus status;
    private AppointmentOutcomeRecord AOR = null;

    public Appointment(String appointmentID, String patientId, String doctorId,
                       LocalDate appointmentDate, Timeslot appointmentTime) {
        this.appointmentID = appointmentID;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = appointmentDate;
        this.time = appointmentTime;
        this.status = AppointmentStatus.PENDING; // Default status set to "pending"
    }

    public boolean isOverlapping(LocalDate date, Timeslot timeslot) {
        if(status== AppointmentStatus.REJECTED || status== AppointmentStatus.CANCELLED)
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

    public AppointmentStatus getStatus() {
        return this.status;
    }

    public AppointmentOutcomeRecord getAOR() {
        return AOR;
    }

    public void setAOR(AppointmentOutcomeRecord aOR) {
        AOR = aOR;
    }

    public void setAppointmentDate(LocalDate date) {
        this.date = date;
    }

    public void setAppointmentTime(Timeslot time) {
        this.time = time;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String toString() {
        return "Appointment: ID: " + appointmentID +
                ", PatientID: " + patientId +
                ", DoctorID: " + doctorId +
                ", Status: " + status +
                ", Date: " + date +
                ", Time: " + time +
                (AOR == null ? "" : "\n"+AOR.toString());
    }


    @Override
    public int compareTo(Appointment o) {
        int res = date.compareTo(o.getDate());
        if(res == 0)
            return time.ordinal() - o.time.ordinal();
        return res;
    }
}
