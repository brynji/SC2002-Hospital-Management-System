package Data;

import Misc.Appointment;

/**
 * Repository with all methods user logged in as Patient needs
 */
public class PatientRepository extends BaseRepository {
    public PatientRepository(DataSource dataSource) {
        super(dataSource);
    }

    public void addNewAppointment(Appointment appointment) {
        dataSource.getAppointments().put(appointment.getAppointmentID(), appointment);
    }

    public Appointment getAppointment(String appointmentId){
        return dataSource.getAppointments().get(appointmentId);
    }

    public void deleteAppointment(String appointmentId){
        dataSource.getAppointments().remove(appointmentId);
    }

    /**
     * Get new unique appointment ID
     * @return unique appointment ID
     */
    public String generateNewAppointmentId(){
        String id = generateID();
        while(dataSource.getAppointments().containsKey(id)){
            id = generateID();
        }
        return id;
    }
}
