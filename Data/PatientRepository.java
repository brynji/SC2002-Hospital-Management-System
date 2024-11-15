package Data;

import Misc.Appointment;
import Misc.RoleType;
import Users.Patient;

public class PatientRepository extends BaseRepository {
    public PatientRepository(DataSource dataSource) {
        super(dataSource);
    }
    //TODO Appointments

    public void addNewAppointment(Appointment appointment) {
        dataSource.getAppointments().put(appointment.getAppointmentID(), appointment);
    }

    public Appointment getAppointment(String appointmentId){
        return dataSource.getAppointments().get(appointmentId);
    }

    public void deleteAppointment(String appointmentId){
        dataSource.getAppointments().remove(appointmentId);
    }

    public String generateNewAppointmentId(){
        String id = generateID();
        while(dataSource.getAppointments().containsKey(id)){
            id = generateID();
        }
        return id;
    }
}
