package Data;

import Misc.*;
import Users.Patient;
import java.util.Collection;
import java.util.Objects;

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
     * Generates a new unique ID for a Prescription.
     * Ensures the generated ID does not conflict with any existing IDs of Prescriptions
     * @return a unique Prescription ID in the format `Abcd-Wxyz-1234-5678`.
     */
    public String generateNewPrescriptionId(){
        String id = generateID();
        Collection<AppointmentOutcomeRecord> aors = dataSource.getAppointments().values().stream().
                map(Appointment::getAOR).filter(Objects::nonNull).toList();
        if(aors.stream().flatMap(aor->aor.getPrescriptions().stream().map(Prescription::getPrescriptionID)).toList().contains(id)){
            return generateNewPrescriptionId();
        }
        return id;
    }
}
