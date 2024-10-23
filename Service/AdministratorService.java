package Service;

import Data.AdministratorRepository;
import Misc.Appointment;
import Misc.Medication;
import Misc.RoleType;
import Users.Administrator;
import Users.User;

import java.util.ArrayList;
import java.util.Collection;

public class AdministratorService extends UserService<Administrator, AdministratorRepository> {
    AdministratorRepository repository;
    Administrator currentUser;

    public AdministratorService(String userId) {
        repository = new AdministratorRepository();
        setCurrentUser(userId);
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

    public void addNewUser(User u){
        if(!isIdAvailable(u.getUserID()))
            throw new IllegalArgumentException("User ID is not available.");
        repository.addNew(u);
    }

    public void removeUser(User u){
        if(isIdAvailable(u.getUserID()))
            throw new IllegalArgumentException("User ID does not exist.");
        repository.remove(u);
    }

    //TODO sorting option
    public ArrayList<Appointment> viewAllAppointments() {
        var appointments = repository.getAllAppointments();
        //filter
        //sort??
        return appointments;
    }

    public ArrayList<User> viewAllUsers(RoleType roleFilter) {
        var users = repository.getAllUsers(roleFilter);
        //sort
        return users;
    }

    public Collection<Medication> viewAllMedications() {
        return repository.getInventory().getAllMedications();
    }

    public void addMedication(Medication m) {
        if(repository.getInventory().getMedication(m.getMedicationName())==null)
            throw new IllegalArgumentException("Medication already exists.");
        repository.getInventory().addNewMedication(m);
    }

    public boolean isIdAvailable(String userId){
        for(var r : repository.getAllRoles()){
            if(r.getUserId().equals(userId))
                return false;
        }
        return true;
    }

    public void removeMedication(String medicationName){
        repository.getInventory().removeMedication(medicationName);
    }

    public void approveReplenishmentRequest(String requestId){
        repository.getInventory().approveReplenishmentRequest(requestId);
    }


}
