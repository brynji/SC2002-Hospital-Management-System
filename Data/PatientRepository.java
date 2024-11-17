package Data;

import Misc.Appointment;

/**
 * Repository class for patient-specific operations.
 * Extends BaseRepository to provide shared functionality while adding methods tailored to patients.
 */
public class PatientRepository extends BaseRepository {

    /**
     * Constructs a PatientRepository with the given data source.
     *
     * @param dataSource the data source to be used for data operations.
     */
    public PatientRepository(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Adds a new appointment to the system.
     *
     * @param appointment the Appointment object to be added.
     */
    public void addNewAppointment(Appointment appointment) {
        dataSource.getAppointments().put(appointment.getAppointmentID(), appointment);
    }

    /**
     * Retrieves an appointment by its unique ID.
     *
     * @param appointmentId the unique identifier of the appointment to retrieve.
     * @return the Appointment object corresponding to the given ID, or null if not found.
     */
    public Appointment getAppointment(String appointmentId) {
        return dataSource.getAppointments().get(appointmentId);
    }

    /**
     * Deletes an appointment from the system by its unique ID.
     *
     * @param appointmentId the unique identifier of the appointment to delete.
     */
    public void deleteAppointment(String appointmentId) {
        dataSource.getAppointments().remove(appointmentId);
    }

    /**
     * Generates a new unique ID for an appointment.
     * Ensures the generated ID does not conflict with any existing appointment IDs in the system.
     *
     * @return a unique appointment ID in the format `Abcd-Wxyz-1234-5678`.
     */
    public String generateNewAppointmentId() {
        String id = generateID();

        // Check if the generated ID already exists in the data source
        while (dataSource.getAppointments().containsKey(id)) {
            id = generateID(); // Regenerate the ID until it is unique
        }

        return id;
    }
}