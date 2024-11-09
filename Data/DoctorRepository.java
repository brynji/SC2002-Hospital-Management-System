package Data;

import Misc.Appointment;

public class DoctorRepository extends BaseRepository {
    public DoctorRepository(DataSource dataSource) {
        super(dataSource);
    }
    //TODO Appointments

    public Appointment getAppointmentById(String appointmentId) {
        return dataSource.getAppointments().get(appointmentId);
    }
}
