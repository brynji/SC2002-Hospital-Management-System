package Data;

import Misc.*;
import Users.*;

import java.util.Collection;
import java.util.Map;

/**
 * Repository with all methods user logged in as Administrator needs
 */
public class AdministratorRepository extends BaseRepository {
    public AdministratorRepository(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Adds new user
     * @param u user to add
     */
    public void addNew(User u){
        RoleType role = getRoleTypeFromUser(u);
        dataSource.getAllUsersWithRole(role).put(u.getUserID(),u);
        dataSource.getRoles().put(u.getUserID(),new Role(u.getUserID(),role));
    }

    /**
     * Removes doctor or patient
     * @param userId userID to remove
     */
    public void remove(String userId){
        RoleType role = dataSource.getRoles().get(userId).getRole();
        if(role==RoleType.Doctor){
            Doctor doctor = findUserById(userId,RoleType.Doctor);
            Collection<Appointment> appointments = getAllAppointmentsFromIds(doctor.getAppointments());
            for (Appointment appointment : appointments){
                Patient patient = findUserById(appointment.getPatientId(), RoleType.Patient);
                patient.removeAppointment(appointment.getAppointmentID());
                if(appointment.getStatus().equals(AppointmentStatus.COMPLETED)){
                    patient.getMedicalRecord().getPastAppointmentRecordsIds().remove(appointment.getAppointmentID());
                }
                dataSource.getAppointments().remove(appointment.getAppointmentID());
            }
        }
        dataSource.getAllUsersWithRole(role).remove(userId);
        dataSource.getRoles().remove(userId);
    }

    public Collection<Appointment> getAllAppointments(){
        return dataSource.getAppointments().values();
    }

    /**
     * Get all roles
     * @return Map of roles by userID
     */
    public Map<String,Role> getAllRoles(){
        return dataSource.getRoles();
    }

    public Inventory getInventory(){
        return dataSource.getInventory();
    }
}
