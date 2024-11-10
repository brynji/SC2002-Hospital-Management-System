package Service;

import Data.AdministratorRepository;
import Misc.Medication;
import Misc.ReplenishmentRequest;
import Misc.RoleType;
import Users.Administrator;
import Users.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        //TODO Can add new patient?
        if(!isIdAvailable(u.getUserID()))
            throw new IllegalArgumentException("User ID is not available.");
        repository.addNew(u);
        repository.save();
    }

    //TODO update staff

    //Cannot use his, data consistency. Also, does it make sense given the menu options?

    public void removeUser(String userId) throws IllegalArgumentException{
        //TODO Can remove patient?
        if(isIdAvailable(userId))
            throw new IllegalArgumentException("User ID does not exist.");
        repository.remove(userId);
        repository.save();
    }

    public ArrayList<String> viewAllAppointments() {
        ArrayList<String> appointments = repository.getAllAppointments();
        //TODO sorting option for appointments
        //filter
        //sort??
        return appointments;
    }

    // Display filtered staff list by role, gender, age, and name
    public Collection<User> getFilteredStaff(String role, String gender, int minAge, int maxAge, String name) {
        Collection<User> staffList;

        if (role.equalsIgnoreCase("doctor")) {
            staffList = repository.getAllUsersWithRole(RoleType.Doctor);
        } else if (role.equalsIgnoreCase("pharmacist")) {
            staffList = repository.getAllUsersWithRole(RoleType.Pharmacist);
        } else if(role.isEmpty()){
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
        Collection<User> staff = new ArrayList<User>(viewAllDoctors());
        staff.addAll(viewAllPharmacist());
        return staff;
    }

    public Collection<User> viewAllDoctors() {
        return repository.getAllUsersWithRole(RoleType.Doctor);
    }

    public Collection<User> viewAllPharmacist() {
        return repository.getAllUsersWithRole(RoleType.Pharmacist);
    }

    public Collection<Medication> viewAllMedications() {
        return repository.getInventory().getAllMedications();
    }

    public Medication viewMedication(String medicationID) {
        return repository.getInventory().getMedication(medicationID);
    }

    public void addMedication(Medication m) throws IllegalArgumentException {
        if(repository.getInventory().getMedication(m.getMedicationName())==null)
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

    public void removeMedication(String medicationName){
        repository.getInventory().removeMedication(medicationName);
        repository.save();
    }

    public void approveReplenishmentRequest(String requestId){
        repository.getInventory().approveReplenishmentRequest(requestId);
        repository.save();
    }
    
    public Collection<ReplenishmentRequest> getReplenishmentRequests() {
        return repository.getInventory().getReplenishmentRequests();
    }
}
