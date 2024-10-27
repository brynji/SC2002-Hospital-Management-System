package Data;

import Misc.Appointment;

import java.util.Collection;

public class PatientRepository extends BaseRepository {
    //TODO Appointments

    // --- ADD ---
    public void addNewAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentID(), appointment);
    }


    // --- GET ---
    public Collection<Appointment> getAllPatientsAppointments(Collection<String> appointmentIds) {
        return appointmentIds.stream().map(appId->appointments.get(appId)).toList();
    }

    public Appointment getAppointment(String appointmentId){
        return appointments.get(appointmentId);
    }

    // --- UPDATE ---

    // --- DELETE ---

    public void deleteAppointment(String appointmentId){
        appointments.remove(appointmentId);
    }
}
