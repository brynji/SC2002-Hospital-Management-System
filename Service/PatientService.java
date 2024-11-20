package Service;

import Misc.*;
import Data.PatientRepository;
import Users.Doctor;
import Users.Patient;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class PatientService extends UserService<Patient,PatientRepository> {
    private final PatientRepository repository;
    private Patient currentUser;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
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

    public void addNewAppointment(String doctorId, LocalDate date, Timeslot timeslot) throws IllegalArgumentException {
        Doctor doctor = repository.findUserById(doctorId, RoleType.Doctor);
        Patient patient = repository.findUserById(currentUser.getUserID(), RoleType.Patient);

        if(doctor==null) throw new IllegalArgumentException("Doctor not found");
        if(patient==null) throw new IllegalArgumentException("Patient not found");

        if(repository.getAllAppointmentsFromIds(patient.getAppointments())
                .stream().anyMatch(app->app.isOverlapping(date,timeslot)) ||
            repository.getAllAppointmentsFromIds(doctor.getAppointments())
                .stream().anyMatch(app->app.isOverlapping(date,timeslot))){

            throw new IllegalArgumentException("Appointments timeslot is already booked");
        }

        Appointment appointment = new Appointment(repository.generateNewAppointmentId(),currentUser.getUserID(),doctorId,date,timeslot);
        repository.addNewAppointment(appointment);
        patient.addAppointment(appointment.getAppointmentID());
        doctor.addAppointment(appointment.getAppointmentID());
        repository.save();
    }

    public void removeAppointment(String appointmentId) throws IllegalArgumentException {
        Appointment appointment = repository.getAppointment(appointmentId);

        if(appointment==null) throw new IllegalArgumentException("Appointment not found");

        ((Doctor) repository.findUserById(appointment.getDoctorId(), RoleType.Doctor)).removeAppointment(appointmentId);
        ((Patient) repository.findUserById(appointment.getPatientId(),RoleType.Patient)).removeAppointment(appointmentId);
        repository.deleteAppointment(appointmentId);
        repository.save();
    }

    public void rescheduleAppointment(String appointmentId, DateTimeslot dateTimeslot){
        String doctorId = repository.getAppointment(appointmentId).getDoctorId();
        removeAppointment(appointmentId);
        addNewAppointment(doctorId, dateTimeslot.getDate(), dateTimeslot.getTimeslot());
    }

    public Collection<Doctor> getAllDoctors(){
        return repository.getAllUsersWithRole(RoleType.Doctor).stream().map(u->(Doctor)u).toList();
    }

    public Collection<DateTimeslot> getFreeTimeslots(String doctorUserId){
        if(!repository.<Doctor>findUserById(doctorUserId,RoleType.Doctor).isAvailableForNewAppointments()){
            return List.of();
        }
        HashSet<DateTimeslot> slots = new HashSet<>(DateHelper.getTimeslotsNext7Days());
        var appointments = repository.getAllAppointmentsFromIds(repository.<Doctor>findUserById(doctorUserId,RoleType.Doctor).getAppointments());
        for(var ap : appointments){
            slots.remove(new DateTimeslot(ap.getDate(),ap.getTime()));
        }
        return slots.stream().sorted().toList();
    }

    public Collection<Appointment> getUpcomingAppointments(){
        return repository.getAllAppointmentsFromIds(repository.<Patient>findUserById(
                currentUser.getUserID(),RoleType.Patient).getAppointments()).stream().filter(
                        app->(app.getStatus().equals(AppointmentStatus.CONFIRMED)|| app.getStatus().equals(AppointmentStatus.PENDING) ||
                                app.getStatus().equals(AppointmentStatus.REJECTED))
                                && app.getDate().isAfter(LocalDate.now().minusDays(1))).toList();
    }

    public String getDoctorName(String doctorUserId){
        return repository.<Doctor>findUserById(doctorUserId,RoleType.Doctor).getName();
    }


    public Collection<AppointmentOutcomeRecord> getAppointmentOutcomeRecords(){
        List<String> aorIds =  currentUser.getMedicalRecord().getPastAppointmentRecordsIds();
        return repository.getAllAppointmentsFromIds(aorIds).stream().sorted().map(Appointment::getAOR).toList();
    }
}
