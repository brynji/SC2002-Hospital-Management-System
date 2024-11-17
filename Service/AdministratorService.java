package Service;

import Data.AdministratorRepository;
import Misc.Appointment;
import Misc.Medication;
import Misc.ReplenishmentRequest;
import Misc.RoleType;
import Users.Administrator;
import Users.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Service class for administrator-specific operations.
 * Extends UserService to provide functionalities such as managing users, appointments, medications, and replenishment requests.
 */
public class AdministratorService extends UserService<Administrator, AdministratorRepository> {

    /** The repository used for administrator-related data operations. */
    private final AdministratorRepository repository;

    /** The currently logged-in administrator. */
    private Administrator currentUser;

    /**
     * Constructs an AdministratorService with the given repository.
     *
     * @param repository the repository to be used for administrator-specific data operations.
     */
    public AdministratorService(AdministratorRepository repository) {
        this.repository = repository;
    }

    /**
     * Sets the currently logged-in administrator.
     *
     * @param userId the ID of the administrator to set as the current user.
     */
    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Administrator);
    }

    /**
     * Retrieves the currently logged-in administrator.
     *
     * @return the current administrator.
     */
    @Override
    public Administrator getCurrentUser() {
        return currentUser;
    }

    /**
     * Retrieves the repository associated with this service.
     *
     * @return the AdministratorRepository instance.
     */
    @Override
    public AdministratorRepository getRepository() {
        return repository;
    }

    /**
     * Checks whether a user ID is available for registration.
     *
     * @param userId the user ID to check.
     * @return true if the ID is available, false otherwise.
     */
    public boolean isIdAvailable(String userId) {
        return !repository.getAllRoles().containsKey(userId);
    }

    /**
     * Adds a new user to the system.
     *
     * @param u the user to add.
     * @throws IllegalArgumentException if the user ID is already taken.
     */
    public void addNewUser(User u) throws IllegalArgumentException {
        if (!isIdAvailable(u.getUserID())) {
            throw new IllegalArgumentException("User ID is not available.");
        }
        repository.addNew(u);
        repository.save();
    }

    /**
     * Removes a user from the system by their ID.
     *
     * @param userId the ID of the user to remove.
     * @throws IllegalArgumentException if the user ID does not exist.
     */
    public void removeUser(String userId) throws IllegalArgumentException {
        if (isIdAvailable(userId)) {
            throw new IllegalArgumentException("User ID does not exist.");
        }
        repository.remove(userId);
        repository.save();
    }

    /**
     * Retrieves information about all appointments in the system.
     *
     * @return a collection of appointment details as strings.
     */
    public Collection<String> viewAllAppointmentsInformation() {
        Collection<Appointment> appointments = repository.getAllAppointments();
        return appointments.stream().sorted().map(Appointment::toString).toList();
    }

    /**
     * Retrieves a filtered list of staff members based on specified criteria.
     *
     * @param role the role of the staff (e.g., Doctor, Pharmacist, or All).
     * @param gender the gender of the staff to filter by.
     * @param minAge the minimum age of the staff to include.
     * @param maxAge the maximum age of the staff to include.
     * @param name a substring of the staff name to filter by.
     * @return a collection of staff members matching the criteria.
     */
    public Collection<User> getFilteredStaff(String role, String gender, int minAge, int maxAge, String name) {
        Collection<User> staffList;

        if (role.equalsIgnoreCase("doctor")) {
            staffList = repository.getAllUsersWithRole(RoleType.Doctor);
        } else if (role.equalsIgnoreCase("pharmacist")) {
            staffList = repository.getAllUsersWithRole(RoleType.Pharmacist);
        } else if (role.equalsIgnoreCase("all")) {
            staffList = viewAllStaff();
        } else {
            staffList = new ArrayList<>();
        }

        return staffList.stream()
                .filter(staff -> gender.isEmpty() || staff.getGender().equalsIgnoreCase(gender))
                .filter(staff -> name.isEmpty() || staff.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(staff -> (minAge == -1 || staff.getAge() >= minAge) && (maxAge == -1 || staff.getAge() <= maxAge))
                .toList();
    }

    /**
     * Retrieves a collection of all staff members in the system.
     *
     * @return a collection of all staff members.
     */
    public Collection<User> viewAllStaff() {
        Collection<User> staff = new ArrayList<>(getAllUsersWithRole(RoleType.Doctor));
        staff.addAll(getAllUsersWithRole(RoleType.Pharmacist));
        return staff;
    }

    /**
     * Retrieves all users with a specified role.
     *
     * @param role the role to filter users by.
     * @return a collection of users with the specified role.
     */
    public Collection<User> getAllUsersWithRole(RoleType role) {
        return repository.getAllUsersWithRole(role);
    }

    /**
     * Retrieves all medications in the inventory.
     *
     * @return a collection of medications.
     */
    public Collection<Medication> getAllMedications() {
        return repository.getInventory().getAllMedications();
    }

    /**
     * Retrieves a medication by its name.
     *
     * @param medicationID the name of the medication to retrieve.
     * @return the medication object, or null if it does not exist.
     */
    public Medication getMedication(String medicationID) {
        return repository.getInventory().getMedication(medicationID);
    }

    /**
     * Adds a new medication to the inventory.
     *
     * @param m the medication to add.
     * @throws IllegalArgumentException if the medication already exists.
     */
    public void addMedication(Medication m) throws IllegalArgumentException {
        if (repository.getInventory().getMedication(m.getMedicationName()) != null) {
            throw new IllegalArgumentException("Medication already exists.");
        }
        repository.getInventory().addNewMedication(m);
        repository.save();
    }

    /**
     * Updates the stock level of a specified medication.
     *
     * @param medicationName the name of the medication to update.
     * @param newStock the new stock level.
     * @throws IllegalArgumentException if the medication does not exist.
     */
    public void updateMedicationStock(String medicationName, int newStock) throws IllegalArgumentException {
        Medication medication = repository.getInventory().getMedication(medicationName);
        if (medication == null) {
            throw new IllegalArgumentException("Medication does not exist.");
        }
        medication.setStockLevel(newStock);
        repository.save();
    }

    /**
     * Updates the low stock alert level of a specified medication.
     *
     * @param medicationName the name of the medication to update.
     * @param newAlertLevel the new low stock alert level.
     * @throws IllegalArgumentException if the medication does not exist.
     */
    public void updateMedicationAlertLevel(String medicationName, int newAlertLevel) throws IllegalArgumentException {
        Medication medication = repository.getInventory().getMedication(medicationName);
        if (medication == null) {
            throw new IllegalArgumentException("Medication does not exist.");
        }
        medication.setLowStockAlert(newAlertLevel);
        repository.save();
    }

    /**
     * Approves a replenishment request for medication.
     *
     * @param request the replenishment request to approve.
     */
    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        repository.getInventory().approveReplenishmentRequest(request);
        repository.save();
    }

    /**
     * Denies a replenishment request for medication.
     *
     * @param request the replenishment request to deny.
     */
    public void denyReplenishmentRequest(ReplenishmentRequest request) {
        repository.getInventory().denyReplenishmentRequest(request);
        repository.save();
    }

    /**
     * Retrieves all replenishment requests in the system.
     *
     * @return a collection of replenishment requests.
     */
    public Collection<ReplenishmentRequest> getReplenishmentRequests() {
        return repository.getInventory().getReplenishmentRequests();
    }

    /**
     * Retrieves all pending replenishment requests.
     *
     * @return a collection of pending replenishment requests.
     */
    public Collection<ReplenishmentRequest> getPendingReplenishmentRequests(){
        return  repository.getInventory().getReplenishmentRequests().stream().filter(
                req -> req.getRequestState().equals("pending")).toList();
    }
}
