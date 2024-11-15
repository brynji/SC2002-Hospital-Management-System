package Data;

import Misc.Appointment;
import Misc.Inventory;
import Misc.RoleType;
import Users.Patient;


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

    public String generateNewAORId(){
        String id = generateID();
        while(getAllUsersWithRole(RoleType.Patient).stream().flatMap(
                u->((Patient)u).getMedicalRecord().getPastAppointmentRecordsIds().stream()).toList().contains(id)){
            id = generateID();
        }
        return id;
    }
}
