package Service;

import Misc.*;
import Data.PatientRepository;
import Users.Doctor;
import Users.Patient;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Service class for patient-specific operations.
 * Extends UserService to provide functionalities such as managing appointments,
 * retrieving doctors and schedules, and accessing medical records.
 */
public class PatientService extends UserService<Patient, PatientRepository> {

    /** The repository used for patient-related data operations. */
    private final PatientRepository repository;

    /** The currently logged-in patient. */
    private Patient currentUser;

    /**
     * Constructs a PatientService with the given repository.
     *
     * @param repository the repository to be used for patient-specific data operations.
     */
    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    /**
     * Sets the currently logged-in patient.
     *
     * @param userId the ID of the patient to set as the current user.
     */
    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Patient);
    }

    /**
     * Retrieves the currently logged-in patient.
     *
     * @return the current patient.
     */
    @Override
    public Patient getCurrentUser() {
        return currentUser;
    }

    /**
     * Retrieves the repository associated with this service.
     *
     * @return the PatientRepository instance.
     */
    @Override
    public PatientRepository getRepository() {
        return repository;
    }

    /**
     * Adds a new appointment for the patient with the specified doctor, date, and timeslot.
     *
     * @param doctorId the ID of the doctor.
     * @param date the date of the appointment.
     * @param timeslot the timeslot of the appointment.
     * @throws IllegalArgumentException if the doctor or patient is not found, or if the timeslot is already booked.
     */
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

    /**
     * Removes an appointment by its ID.
     *
     * @param appointmentId the ID of the appointment to remove.
     * @throws IllegalArgumentException if the appointment is not found.
     */
    public void removeAppointment(String appointmentId) throws IllegalArgumentException {
        Appointment appointment = repository.getAppointment(appointmentId);

        if(appointment==null) throw new IllegalArgumentException("Appointment not found");

        ((Doctor) repository.findUserById(appointment.getDoctorId(), RoleType.Doctor)).removeAppointment(appointmentId);
        ((Patient) repository.findUserById(appointment.getPatientId(),RoleType.Patient)).removeAppointment(appointmentId);
        repository.deleteAppointment(appointmentId);
        repository.save();
    }

    /**
     * Reschedules an existing appointment to a new date and timeslot.
     *
     * @param appointmentId the ID of the appointment to reschedule.
     * @param dateTimeslot the new date and timeslot for the appointment.
     */
    public void rescheduleAppointment(String appointmentId, DateTimeslot dateTimeslot) {
        String doctorId = repository.getAppointment(appointmentId).getDoctorId();
        removeAppointment(appointmentId);
        addNewAppointment(doctorId, dateTimeslot.getDate(), dateTimeslot.getTimeslot());
    }

    /**
     * Retrieves a collection of all doctors in the system.
     *
     * @return a collection of all doctors.
     */
    public Collection<Doctor> getAllDoctors() {
        return repository.getAllUsersWithRole(RoleType.Doctor).stream().map(u -> (Doctor) u).toList();
    }

    /**
     * Retrieves a collection of free timeslots for a specific doctor.
     *
     * @param doctorUserId the ID of the doctor.
     * @return a collection of available timeslots for the doctor.
     */
    public Collection<DateTimeslot> getFreeTimeslots(String doctorUserId) {
        if (!repository.<Doctor>findUserById(doctorUserId, RoleType.Doctor).isAvailableForNewAppointments()) {
            return List.of();
        }
        HashSet<DateTimeslot> slots = new HashSet<>(DateHelper.getTimeslotsNext7Days());
        var appointments = repository.getAllAppointmentsFromIds(repository.<Doctor>findUserById(doctorUserId, RoleType.Doctor).getAppointments());
        for (var ap : appointments) {
            slots.remove(new DateTimeslot(ap.getDate(), ap.getTime()));
        }
        return slots.stream().sorted().toList();
    }

    /**
     * Retrieves a collection of the patient's upcoming appointments.
     *
     * @return a collection of upcoming appointments for the patient.
     */
    public Collection<Appointment> getUpcomingAppointments() {
        return repository.getAllAppointmentsFromIds(repository.<Patient>findUserById(
                currentUser.getUserID(),RoleType.Patient).getAppointments()).stream().filter(
                        app->(app.getStatus().equals(AppointmentStatus.CONFIRMED)|| app.getStatus().equals(AppointmentStatus.PENDING) ||
                                app.getStatus().equals(AppointmentStatus.REJECTED))
                                && app.getDate().isAfter(LocalDate.now().minusDays(1))).toList();
    }

    /**
     * Retrieves the name of a doctor by their user ID.
     *
     * @param doctorUserId the ID of the doctor.
     * @return the name of the doctor.
     */
    public String getDoctorName(String doctorUserId) {
        return repository.<Doctor>findUserById(doctorUserId, RoleType.Doctor).getName();
    }

    /**
     * Retrieves a collection of appointment outcome records for the current patient.
     *
     * @return a collection of the patient's appointment outcome records.
     */
    public Collection<AppointmentOutcomeRecord> getAppointmentOutcomeRecords() {
        List<String> aorIds = currentUser.getMedicalRecord().getPastAppointmentRecordsIds();
        return repository.getAllAppointmentsFromIds(aorIds).stream().sorted().map(Appointment::getAOR).toList();
    }
}