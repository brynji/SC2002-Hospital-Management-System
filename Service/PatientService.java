package Service;

import Misc.Appointment;
import Data.PatientRepository;
import Misc.RoleType;
import Users.Doctor;
import Users.Patient;

public class PatientService extends UserService<Patient,PatientRepository> {
    PatientRepository repository;
    Patient currentUser;

    public PatientService(String userId) {
        repository = new PatientRepository();
        setCurrentUser(userId);
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Patient);
    }

    @Override
    public Patient getCurrentUser() {
        return currentUser;
    }

    @Override
    public PatientRepository getRepository() {
        return repository;
    }

    public void addNewAppointment(Appointment appointment) {
        Doctor doctor = repository.findUserById(appointment.getDoctorId(), RoleType.Doctor);
        Patient patient = repository.findUserById(appointment.getPatientId(), RoleType.Patient);

        if(doctor==null) throw new IllegalArgumentException("Doctor not found");
        if(patient==null) throw new IllegalArgumentException("Patient not found");

        //TODO Check that the slot is not already full

        repository.addNewAppointment(appointment);
        patient.addAppointment(appointment.getAppointmentID());
        doctor.addAppointment(appointment.getDoctorId());
        repository.save();
    }

    public void removeAppointment(String appointmentId){
        Appointment appointment = repository.getAppointment(appointmentId);

        if(appointment==null) throw new IllegalArgumentException("Appointment not found");

        ((Doctor) repository.findUserById(appointment.getDoctorId(), RoleType.Doctor)).removeAppointment(appointmentId);
        ((Patient) repository.findUserById(appointment.getPatientId(),RoleType.Patient)).cancelAppointment(appointmentId);
        repository.deleteAppointment(appointmentId);
        repository.save();
    }
}
