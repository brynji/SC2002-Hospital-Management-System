package Data;

import Misc.Appointment;
import Misc.Inventory;
import Misc.RoleType;
import Users.Patient;

/**
 * Repository class for doctor-specific operations.
 * Extends BaseRepository to provide shared functionality while adding methods tailored to doctors.
 */
public class DoctorRepository extends BaseRepository {

    /**
     * Constructs a DoctorRepository with the given data source.
     *
     * @param dataSource the data source to be used for data operations.
     */
    public DoctorRepository(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Retrieves an appointment by its unique ID.
     *
     * @param appointmentId the unique identifier of the appointment to retrieve.
     * @return the Appointment object corresponding to the given ID, or null if not found.
     */
    public Appointment getAppointmentById(String appointmentId) {
        return dataSource.getAppointments().get(appointmentId);
    }

    /**
     * Retrieves the inventory for doctor-specific access.
     *
     * @return the Inventory object representing the current inventory data.
     */
    public Inventory getInventory() {
        return dataSource.getInventory();
    }

    /**
     * Generates a new unique ID for an Appointment Outcome Record (AOR).
     * Ensures the generated ID does not conflict with any existing IDs
     * in the past appointment records of all patients.
     *
     * @return a unique AOR ID in the format `Abcd-Wxyz-1234-5678`.
     */
    public String generateNewAORId() {
        String id = generateID();

        // Check if the generated ID already exists in any past appointment records
        while (getAllUsersWithRole(RoleType.Patient).stream()
                .flatMap(u -> ((Patient) u).getMedicalRecord().getPastAppointmentRecordsIds().stream())
                .toList()
                .contains(id)) {
            id = generateID(); // Regenerate the ID until it is unique
        }

        return id;
    }
}
