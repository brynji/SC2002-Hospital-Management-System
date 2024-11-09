package Service;

import Data.DoctorRepository;
import Misc.Appointment;
import Misc.AppointmentOutcomeRecord;
import Misc.RoleType;
import Misc.Status;
import Users.Doctor;
import Users.Patient;

import java.util.Collection;

public class DoctorService extends UserService<Doctor, DoctorRepository> {
    private final DoctorRepository repository;
    private Doctor currentUser;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public Collection<Patient> getAllPatientsInCare(){
        var appointments = repository.getAllAppointmentsFromIds(currentUser.getAppointments());
        return appointments.stream().map(appointment->repository.<Patient>findUserById(appointment.getPatientId(),RoleType.Patient)).toList();
    }

    public Collection<Appointment> getUpcomingAppointments(boolean showPending){
        return repository.getAllAppointmentsFromIds(currentUser.getAppointments()).stream()
                .filter(appointment -> appointment.getStatus() == Status.CONFIRMED ||
                        (!showPending || appointment.getStatus() == Status.PENDING)).sorted().toList();
    }

    public Collection<Appointment> getAllPendingAppointments(){
        return repository.getAllAppointmentsFromIds(currentUser.getAppointments()).stream()
                .filter(appointment -> appointment.getStatus() == Status.PENDING).sorted().toList();
    }

    public void acceptAppointment(String appointmentId){
        if(!currentUser.getAppointments().contains(appointmentId))
            throw new IllegalArgumentException("No appointment with given id for current user");
        Appointment appointment =  repository.getAppointmentById(appointmentId);
        if(appointment.getStatus() != Status.PENDING)
            throw new IllegalStateException("Appointment is not pending - state cannot be changed");
        appointment.setStatus(Status.CONFIRMED);
        repository.save();
    }

    public void declineAppointment(String appointmentId){
        if(!currentUser.getAppointments().contains(appointmentId))
            throw new IllegalArgumentException("No appointment with given id for current user");
        Appointment appointment =  repository.getAppointmentById(appointmentId);
        if(appointment.getStatus() != Status.PENDING)
            throw new IllegalStateException("Appointment is not pending - state cannot be changed");
        appointment.setStatus(Status.REJECTED);
        repository.save();
    }

    public void completeAppointment(String appointmentId, AppointmentOutcomeRecord aor){
        if(!currentUser.getAppointments().contains(appointmentId))
            throw new IllegalArgumentException("No appointment with given id for current user");
        Appointment appointment = repository.getAppointmentById(appointmentId);
        appointment.setAOR(aor);
        appointment.setStatus(Status.COMPLETED);
        repository.save();
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Doctor);
    }

    @Override
    public Doctor getCurrentUser() {
        return currentUser;
    }

    @Override
    public DoctorRepository getRepository() {
        return repository;
    }
}
