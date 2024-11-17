package Data;

import Misc.*;
import Users.Patient;

import java.util.Collection;
import java.util.Objects;

/**
 * Repository with all methods user logged in as Doctor needs
 */
public class DoctorRepository extends BaseRepository {
    public DoctorRepository(DataSource dataSource) {
        super(dataSource);
    }

    public Appointment getAppointmentById(String appointmentId) {
        return dataSource.getAppointments().get(appointmentId);
    }

    public Inventory getInventory(){
        return dataSource.getInventory();
    }

    /**
     * Generates new unique ID for prescription
     * @return unique ID for prescription
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
