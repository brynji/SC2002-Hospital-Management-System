package Service;

import Misc.*;
import Data.PatientRepository;
import Users.Doctor;
import Users.Patient;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

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

    public void addNewAppointment(String doctorId, LocalDate date, Timeslot timeslot) {
        Doctor doctor = repository.findUserById(doctorId, RoleType.Doctor);
        Patient patient = repository.findUserById(currentUser.getUserID(), RoleType.Patient);

        if(doctor==null) throw new IllegalArgumentException("Doctor not found");
        if(patient==null) throw new IllegalArgumentException("Patient not found");

        //TODO Check that the slot is not already full
        if(repository.getAllAppointmentsFromIds(patient.getAppointments())
                .stream().anyMatch(app->app.isOverlapping(date,timeslot)) ||
            repository.getAllAppointmentsFromIds(doctor.getAppointments())
                .stream().anyMatch(app->app.isOverlapping(date,timeslot))){

            throw new IllegalArgumentException("Appointments timeslot is already booked");
        }

        Appointment appointment = new Appointment(currentUser.getUserID(),doctorId,date,timeslot);
        repository.addNewAppointment(appointment);
        patient.addAppointment(appointment.getAppointmentID());
        doctor.addAppointment(appointment.getAppointmentID());
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

    public Collection<Doctor> getAllDoctors(){
        return repository.getAllUsersWithRole(RoleType.Doctor).stream().map(u->(Doctor)u).toList();
    }

    public Collection<DateTimeslot> getFreeTimeslots(String doctorUserId){
        HashSet<DateTimeslot> slots = new HashSet<>(DateHelper.getTimeslotsNext7Days());
        var appointments = repository.getAllAppointmentsFromIds(repository.<Doctor>findUserById(doctorUserId,RoleType.Doctor).getAppointments());
        for(var ap : appointments){
            slots.remove(new DateTimeslot(ap.getDate(),ap.getTime()));
        }
        return slots.stream().sorted().toList();
    }

    public Collection<Appointment> getUpcomingAppointments(){
        var ap = repository.getAllAppointmentsFromIds(repository.<Patient>findUserById(
                currentUser.getUserID(),RoleType.Patient).getAppointments()).stream().toList();
        return ap;
    }

    public String getDoctorName(String doctorUserId){
        return repository.<Doctor>findUserById(doctorUserId,RoleType.Doctor).getName();
    }
}
