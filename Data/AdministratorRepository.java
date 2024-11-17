package Data;

import Misc.*;
import Users.*;

import java.util.Collection;
import java.util.Map;

/**
 * Repository class for administrator-specific operations.
 * Provides methods to add, remove, and manage users, roles, and appointments.
 * Extends BaseRepository for shared repository functionality.
 */
public class AdministratorRepository extends BaseRepository {

    /**
     * Constructs an AdministratorRepository with the given data source.
     *
     * @param dataSource the data source to be used for data operations.
     */
    public AdministratorRepository(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Adds a new user to the system and assigns a role.
     *
     * @param u the user to be added.
     */
    public void addNew(User u) {
        RoleType role = getRoleTypeFromUser(u);
        dataSource.getAllUsersWithRole(role).put(u.getUserID(), u);
        dataSource.getRoles().put(u.getUserID(), new Role(u.getUserID(), role));
    }

    /**
     * Removes a user from the system by their ID.
     * If the user is a doctor, their associated appointments are canceled, 
     * and completed appointments are removed from patients' past records.
     *
     * @param userId the ID of the user to be removed.
     */
    public void remove(String userId) {
        RoleType role = dataSource.getRoles().get(userId).getRole();

        // Special handling for doctors
        if (role == RoleType.Doctor) {
            Doctor doctor = findUserById(userId, RoleType.Doctor);
            Collection<Appointment> appointments = getAllAppointmentsFromIds(doctor.getAppointments());

            for (Appointment appointment : appointments) {
                Patient patient = findUserById(appointment.getPatientId(), RoleType.Patient);

                // Cancel appointment for the patient
                patient.cancelAppointment(appointment.getAppointmentID());

                // Remove completed appointments from patient's past records
                if (appointment.getStatus().equals(AppointmentStatus.COMPLETED)) {
                    patient.getMedicalRecord().getPastAppointmentRecordsIds().remove(appointment.getAppointmentID());
                }

                // Remove appointment from data source
                dataSource.getAppointments().remove(appointment.getAppointmentID());
            }
        }

        // Remove user and their role from data source
        dataSource.getAllUsersWithRole(role).remove(userId);
        dataSource.getRoles().remove(userId);
    }

    /**
     * Retrieves all appointments in the system.
     *
     * @return a collection of all appointments.
     */
    public Collection<Appointment> getAllAppointments() {
        return dataSource.getAppointments().values();
    }

    /**
     * Retrieves all roles in the system.
     *
     * @return a map of roles where the key is the user ID, and the value is the Role object.
     */
    public Map<String, Role> getAllRoles() {
        return dataSource.getRoles();
    }

    /**
     * Retrieves the inventory for administrative purposes.
     *
     * @return the Inventory object.
     */
    public Inventory getInventory() {
        return dataSource.getInventory();
    }
}