package Misc;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Appointment made by user to schedule a visit doctor
 */
public class Appointment implements Serializable, Comparable<Appointment> {
    private final String appointmentID;
    private final String patientId;
    private final String doctorId;
    private LocalDate date;
    private Timeslot time;
    private AppointmentStatus status;
    private AppointmentOutcomeRecord AOR = null;

    /**
     * Initializes Appointment class
     * @param appointmentID unique ID
     * @param patientId ID of patient that scheduled this appointment
     * @param doctorId ID of doctor the patient wants to visit
     * @param appointmentDate Date of the appointment
     * @param appointmentTime Time of the appointment
     */
    public Appointment(String appointmentID, String patientId, String doctorId,
                       LocalDate appointmentDate, Timeslot appointmentTime) {
        this.appointmentID = appointmentID;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = appointmentDate;
        this.time = appointmentTime;
        this.status = AppointmentStatus.PENDING; // Default status set to "pending"
    }

    /**
     * Check whether date and time of this appointment is the same with given date and time
     * @param date Date to check
     * @param timeslot Time to check
     * @return True if appointment has same date and time as argument, false otherwise
     */
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

    /**
     * Get status of this appointment
     * @return status of this appointment
     */
    public AppointmentStatus getStatus() {
        return this.status;
    }

    /**
     * Returns appointment outcome record for completed appointments otherwise null
     * @return AOR for completed appointment, null otherwise
     */
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
