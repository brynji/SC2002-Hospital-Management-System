package Data;

import Misc.Appointment;

import java.util.Collection;

public class DoctorRepository extends BaseRepository {
    //TODO Appointments

    // --- ADD ---

    // --- GET ---

    public Appointment getAppointmentById(String appointmentId) {
        return appointments.get(appointmentId);
    }

    // --- UPDATE ---

    // --- DELETE ---
}
