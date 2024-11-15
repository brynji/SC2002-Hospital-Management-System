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

public class AdministratorService extends UserService<Administrator, AdministratorRepository> {
    private final AdministratorRepository repository;
    private Administrator currentUser;

    public AdministratorService(AdministratorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId,RoleType.Administrator);
    }

    @Override
    public Administrator getCurrentUser() {
        return currentUser;
    }

    @Override
    public AdministratorRepository getRepository() {
        return repository;
    }

    public boolean isIdAvailable(String userId){
        return !repository.getAllRoles().containsKey(userId);
    }

    public void addNewUser(User u) throws IllegalArgumentException{
        if(!isIdAvailable(u.getUserID()))
            throw new IllegalArgumentException("User ID is not available.");
        repository.addNew(u);
        repository.save();
    }

    public void removeUser(String userId) throws IllegalArgumentException{
        if(isIdAvailable(userId))
            throw new IllegalArgumentException("User ID does not exist.");
        repository.remove(userId);
        repository.save();
    }

    public Collection<String> viewAllAppointmentsInformation() {
        Collection<Appointment> appointments = repository.getAllAppointments();
        return appointments.stream().sorted().map(Appointment::toString).toList();
    }

    // Display filtered staff list by role, gender, age, and name
    public Collection<User> getFilteredStaff(String role, String gender, int minAge, int maxAge, String name) {
        Collection<User> staffList;

        if (role.equalsIgnoreCase("doctor")) {
            staffList = repository.getAllUsersWithRole(RoleType.Doctor);
        } else if (role.equalsIgnoreCase("pharmacist")) {
            staffList = repository.getAllUsersWithRole(RoleType.Pharmacist);
        } else if(role.equalsIgnoreCase("all")) {
            staffList = viewAllStaff();
        }else {
            staffList = new ArrayList<>();
        }

        return staffList.stream() // Initialise as List and not ArrayList so that the list generated can be any kind of list (e.g. LinkedList, etc
                .filter(staff -> gender.isEmpty() || staff.getGender().equalsIgnoreCase(gender))
                .filter(staff -> name.isEmpty() || staff.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(staff -> (minAge == -1 || staff.getAge() >= minAge) && (maxAge == -1  || staff.getAge() <= maxAge))
                .toList();
    }


    public Collection<User> viewAllStaff() {
        Collection<User> staff = new ArrayList<>(getAllUsersWithRole(RoleType.Doctor));
        staff.addAll(getAllUsersWithRole(RoleType.Pharmacist));
        return staff;
    }

    public Collection<User> getAllUsersWithRole(RoleType role) {
        return repository.getAllUsersWithRole(role);
    }

    public Collection<Medication> getAllMedications() {
        return repository.getInventory().getAllMedications();
    }

    public Medication getMedication(String medicationID) {
        return repository.getInventory().getMedication(medicationID);
    }

    public void addMedication(Medication m) throws IllegalArgumentException {
        if(repository.getInventory().getMedication(m.getMedicationName())!=null)
            throw new IllegalArgumentException("Medication already exists.");
        repository.getInventory().addNewMedication(m);
        repository.save();
    }

    public void updateMedicationStock(String medicationName, int newStock) throws IllegalArgumentException {
        Medication medication = repository.getInventory().getMedication(medicationName);
        if(medication==null)
            throw new IllegalArgumentException("Medication does not exist.");
        medication.setStockLevel(newStock);
        repository.save();
    }

    public void updateMedicationAlertLevel(String medicationName, int newAlertLevel) throws IllegalArgumentException {
        Medication medication = repository.getInventory().getMedication(medicationName);
        if(medication==null)
            throw new IllegalArgumentException("Medication does not exist.");
        medication.setLowStockAlert(newAlertLevel);
        repository.save();
    }

    public void approveReplenishmentRequest(ReplenishmentRequest request){
        repository.getInventory().approveReplenishmentRequest(request);
        repository.save();
    }

    public void denyReplenishmentRequest(ReplenishmentRequest request){
        repository.getInventory().denyReplenishmentRequest(request);
        repository.save();
    }

    public Collection<ReplenishmentRequest> getReplenishmentRequests() {
        return repository.getInventory().getReplenishmentRequests();
    }

    public Collection<ReplenishmentRequest> getPendingReplenishmentRequests(){
        return  repository.getInventory().getReplenishmentRequests().stream().filter(
                req -> req.getRequestState().equals("pending")).toList();
    }
}
