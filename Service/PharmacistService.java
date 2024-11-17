package Service;

import Data.PharmacistRepository;
import Misc.*;
import Users.Patient;
import Users.Pharmacist;

import javax.naming.InsufficientResourcesException;
import java.util.Collection;

/**
 * Service class for pharmacist-specific operations.
 * Extends UserService to provide pharmacist-specific functionalities such as managing prescriptions,
 * dispensing medication, and handling inventory.
 */
public class PharmacistService extends UserService<Pharmacist, PharmacistRepository> {

    /** The repository used for pharmacist-related data operations. */
    private final PharmacistRepository repository;

    /** The currently logged-in pharmacist. */
    private Pharmacist currentUser;

    /**
     * Constructs a PharmacistService with the given repository.
     *
     * @param repository the repository to be used for pharmacist-specific data operations.
     */
    public PharmacistService(PharmacistRepository repository) {
        this.repository = repository;
    }

    /**
     * Sets the currently logged-in pharmacist.
     *
     * @param userId the ID of the pharmacist to set as the current user.
     */
    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Pharmacist);
    }

    /**
     * Retrieves the currently logged-in pharmacist.
     *
     * @return the current pharmacist.
     */
    @Override
    public Pharmacist getCurrentUser() {
        return currentUser;
    }

    /**
     * Retrieves the repository associated with this service.
     *
     * @return the PharmacistRepository instance.
     */
    @Override
    public PharmacistRepository getRepository() {
        return repository;
    }

    /**
     * Retrieves appointment outcome records for a specific patient.
     *
     * @param userId the ID of the patient.
     * @return a collection of appointment outcome records for the specified patient.
     */
    public Collection<AppointmentOutcomeRecord> getAppointmentOutcomeRecords(String userId) {
        return repository.getAllAppointmentsFromIds(repository.<Patient>findUserById(userId, RoleType.Patient)
                .getMedicalRecord().getPastAppointmentRecordsIds())
                .stream().map(Appointment::getAOR).toList();
    }

    /**
     * Dispenses medication for a specific prescription and updates its status.
     *
     * @param userId the ID of the patient associated with the prescription.
     * @param prescriptionId the ID of the prescription to dispense.
     * @throws IllegalArgumentException if the prescription is not found.
     * @throws InsufficientResourcesException if there is insufficient stock for the medication.
     */
    public void dispenseMedication(String userId, String prescriptionId)
            throws IllegalArgumentException, InsufficientResourcesException {
        MedicalRecord medicalRecord = ((Patient) repository.findUserById(userId, RoleType.Patient)).getMedicalRecord();
        AppointmentOutcomeRecord appointmentOutcomeRecord = null;
        Prescription prescription = null;

        for (var appointment : repository.getAllAppointmentsFromIds(medicalRecord.getPastAppointmentRecordsIds())) {
            for (var p : appointment.getAOR().getPrescriptions()) {
                if (p.getPrescriptionID().equals(prescriptionId)) {
                    appointmentOutcomeRecord = appointment.getAOR();
                    prescription = p;
                }
            }
        }

        if (prescription == null) {
            throw new IllegalArgumentException("Prescription not found");
        }

        try {
            Inventory inventory = repository.getInventory();
            inventory.dispenseMedication(prescription.getMedicationName(), prescription.getQuantity());
            prescription.setStatus("dispensed");
            if (appointmentOutcomeRecord.getPrescriptions().stream().allMatch(p -> p.getStatus().equals("dispensed"))) {
                appointmentOutcomeRecord.setStatus("dispensed");
            }
        } catch (InsufficientResourcesException e) {
            throw new InsufficientResourcesException("Not enough medication in stock");
        }

        repository.save();
    }

    /**
     * Retrieves all patients in the system.
     *
     * @return a collection of all patients.
     */
    public Collection<Patient> getAllPatients() {
        return repository.getAllUsersWithRole(RoleType.Patient);
    }

    /**
     * Retrieves all medications in the inventory.
     *
     * @return a collection of all medications.
     */
    public Collection<Medication> getAllMedication() {
        return repository.getInventory().getAllMedications();
    }

    /**
     * Retrieves all medications that are low in stock.
     *
     * @return a collection of medications with low stock alerts.
     */
    public Collection<Medication> getAllMedicationWithLowAlert() {
        return repository.getInventory().getAllMedications().stream().filter(Medication::isStockLow).toList();
    }

    /**
     * Retrieves all pending prescriptions for a specific patient.
     *
     * @param patientId the ID of the patient.
     * @return a collection of pending prescriptions for the specified patient.
     */
    public Collection<Prescription> getAllPatientsPendingPrescriptions(String patientId) {
        Patient patient = repository.findUserById(patientId, RoleType.Patient);
        Collection<String> pastRecordsIds = patient.getMedicalRecord().getPastAppointmentRecordsIds();
        return repository.getAllAppointmentsFromIds(pastRecordsIds).stream()
                .flatMap(app -> app.getAOR().getPrescriptions().stream())
                .filter(p -> p.getStatus().equals("pending")).toList();
    }

    /**
     * Checks if a given patient ID is valid.
     *
     * @param userId the ID of the patient to validate.
     * @return true if the patient ID is valid, false otherwise.
     */
    public boolean isValidPatientId(String userId) {
        return repository.getAllUsersWithRole(RoleType.Patient).stream()
                .anyMatch(p -> ((Patient) p).getUserID().equals(userId));
    }

    /**
     * Submits a replenishment request for a specific medication.
     *
     * @param medicationName the name of the medication to replenish.
     * @param amount the quantity of the medication to request.
     */
    public void submitReplenishmentRequest(String medicationName, int amount) {
        repository.getInventory().addReplenishmentRequest(new
                ReplenishmentRequest(medicationName, currentUser.getUserID(), amount));
        repository.save();
    }
}