package Service;

import Data.DoctorRepository;
import Misc.*;
import Users.Doctor;
import Users.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Service class for doctor-specific operations.
 * Extends UserService to provide functionalities such as managing appointments,
 * updating diagnoses, and accessing patient records.
 */
public class DoctorService extends UserService<Doctor, DoctorRepository> {

    /** The repository used for doctor-related data operations. */
    private final DoctorRepository repository;

    /** The currently logged-in doctor. */
    private Doctor currentUser;

    /**
     * Constructs a DoctorService with the given repository.
     *
     * @param repository the repository to be used for doctor-specific data operations.
     */
    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all patients under the care of the current doctor.
     *
     * @return a collection of patients in the doctor's care.
     */
    public Collection<Patient> getAllPatientsInCare() {
        var appointments = repository.getAllAppointmentsFromIds(currentUser.getAppointments());
        return appointments.stream().map(appointment->repository.<Patient>findUserById(appointment.getPatientId(),RoleType.Patient))
                .distinct().toList();
    }

    /**
     * Retrieves all pending appointments for the current doctor.
     *
     * @return a collection of pending appointments.
     */
    public Collection<Appointment> getAllPendingAppointments() {
        return repository.getAllAppointmentsFromIds(currentUser.getAppointments()).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.PENDING).sorted().toList();
    }

    /**
     * Retrieves all confirmed appointments for the current doctor.
     *
     * @return a collection of confirmed appointments.
     */
    public Collection<Appointment> getConfirmedAppointments() {
        return repository.getAllAppointmentsFromIds(currentUser.getAppointments()).stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.CONFIRMED).sorted().toList();
    }

    /**
     * Retrieves all medications available in the inventory.
     *
     * @return a collection of medications.
     */
    public Collection<Medication> getAllMedications() {
        return repository.getInventory().getAllMedications();
    }

    /**
     * Accepts a pending appointment by its ID.
     *
     * @param appointmentId the ID of the appointment to accept.
     * @throws IllegalArgumentException if the appointment does not belong to the current doctor.
     * @throws IllegalStateException if the appointment is not in a pending state.
     */
    public void acceptAppointment(String appointmentId) {
        if (!currentUser.getAppointments().contains(appointmentId)) {
            throw new IllegalArgumentException("No appointment with given id for current user");
        }
        Appointment appointment = repository.getAppointmentById(appointmentId);
        if (appointment.getStatus() != AppointmentStatus.PENDING)
            throw new IllegalStateException("Appointment is not pending - state cannot be changed");
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        repository.save();
    }

    /**
     * Declines a pending appointment by its ID.
     *
     * @param appointmentId the ID of the appointment to decline.
     * @throws IllegalArgumentException if the appointment does not belong to the current doctor.
     * @throws IllegalStateException if the appointment is not in a pending state.
     */
    public void declineAppointment(String appointmentId) {
        if (!currentUser.getAppointments().contains(appointmentId))
            throw new IllegalArgumentException("No appointment with given id for current user");
        Appointment appointment =  repository.getAppointmentById(appointmentId);
        if(appointment.getStatus() != AppointmentStatus.PENDING)
            throw new IllegalStateException("Appointment is not pending - state cannot be changed");
        appointment.setStatus(AppointmentStatus.REJECTED);
        repository.save();
    }

    /**
     * Completes an appointment by attaching an appointment outcome record (AOR) to it.
     *
     * @param appointmentId the ID of the appointment to complete.
     * @param aor the appointment outcome record to attach to the appointment.
     * @throws IllegalArgumentException if the appointment does not belong to the current doctor.
     */
    public void completeAppointment(String appointmentId, AppointmentOutcomeRecord aor) {
        if (!currentUser.getAppointments().contains(appointmentId))
            throw new IllegalArgumentException("No appointment with given id for current user");

        if(aor.getPrescriptions().isEmpty()){
            aor.setStatus("dispensed");
        }

        Appointment appointment = repository.getAppointmentById(appointmentId);
        appointment.setAOR(aor);
        appointment.setStatus(AppointmentStatus.COMPLETED);

        Patient patient = repository.findUserById(appointment.getPatientId(), RoleType.Patient);
        patient.getMedicalRecord().AddPastAppointment(aor.getRecordID());

        repository.save();
    }

    /**
     * Adds a new diagnosis to a patient's medical record.
     *
     * @param patientId the ID of the patient.
     * @param diagnosis the diagnosis to add.
     */
    public void addNewDiagnosis(String patientId, String diagnosis) {
        repository.<Patient>findUserById(patientId, RoleType.Patient)
                .getMedicalRecord().AddDiagnosisAndTreatment(diagnosis);
        repository.save();
    }

    /**
     * Retrieves appointment outcome records for a specific patient.
     *
     * @param patient the patient whose appointment outcome records are to be retrieved.
     * @return a collection of appointment outcome records.
     */
    public Collection<AppointmentOutcomeRecord> getAppointmentOutcomesFromPatient(Patient patient) {
        var ids = patient.getMedicalRecord().getPastAppointmentRecordsIds();
        return repository.getAllAppointmentsFromIds(ids).stream().map(Appointment::getAOR).toList();
    }

    /**
     * Retrieves the upcoming schedule of the current doctor.
     *
     * @return a collection of timeslots for upcoming appointments.
     */
    public Collection<DateTimeslot> getUpcomingSchedule() {
        ArrayList<DateTimeslot> slots = new ArrayList<>();
        var appointments = getConfirmedAppointments();
        for(var ap : appointments){
            if(ap.getDate().isBefore(LocalDate.now())){
                continue;
            }
            slots.add(new DateTimeslot(ap.getDate(),ap.getTime()));
        }
        return slots.stream().sorted().toList();
    }

    /**
     * Toggles the availability of the current doctor for new appointments.
     */
    public void changeAvailability() {
        currentUser.setAvailableForNewAppointments(!currentUser.isAvailableForNewAppointments());
    }

    /**
     * Validates whether a given medication name exists in the inventory.
     *
     * @param medicationName the name of the medication to validate.
     * @return true if the medication exists, false otherwise.
     */
    public boolean isValidMedication(String medicationName) {
        return repository.getInventory().getMedication(medicationName) != null;
    }

    /**
     * Generates a new ID for a prescription.
     *
     * @return a new prescription ID.
     */
    public String getNewPrescriptionId(){
        return repository.generateNewPrescriptionId();
    }

    /**
     * Sets the currently logged-in doctor.
     *
     * @param userId the ID of the doctor to set as the current user.
     */
    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Doctor);
    }

    /**
     * Retrieves the currently logged-in doctor.
     *
     * @return the current doctor.
     */
    @Override
    public Doctor getCurrentUser() {
        return currentUser;
    }

    /**
     * Retrieves the repository associated with this service.
     *
     * @return the DoctorRepository instance.
     */
    @Override
    public DoctorRepository getRepository() {
        return repository;
    }
}