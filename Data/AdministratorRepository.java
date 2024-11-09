package Data;

import Misc.*;
import Users.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/* The reason the methods for adding, deleting and updating the staff are in the repository
    is that a repo is meant to serve as the site for data manipulation.

    This helps to ensure the Single Responsibility Principle and also allows for cleaner
    extension of the code base in the future (e.g. if we add a Manager class, they could
    have the same power as administrators if not more and may be able to use this repo
    as well).
 */

public class AdministratorRepository extends BaseRepository {
    public AdministratorRepository(DataSource dataSource) {
        super(dataSource);
    }

    //TODO Appointments

    // --- ADD ---

    public void addNew(User u){
        RoleType role = getRoleTypeFromUser(u);
        dataSource.getAllUsersWithRole(role).put(u.getUserID(),u);
        dataSource.getRoles().put(u.getUserID(),new Role(u.getUserID(),role));
    }

    public void remove(String userId){
        RoleType role = dataSource.getRoles().get(userId).getRole();
        dataSource.getAllUsersWithRole(role).remove(userId);
        dataSource.getRoles().remove(userId);
    }

    // --- GET ---

    public ArrayList<String> getAllAppointments(){
        ArrayList<String> appointments = new ArrayList<>();
        for(Doctor d : super.<Doctor>getAllUsersWithRole(RoleType.Doctor)){
            appointments.addAll(d.getAppointments());
        }
        return appointments;
    }

    public Map<String,Role> getAllRoles(){
        return dataSource.getRoles();
    }

    public Inventory getInventory(){
        return dataSource.getInventory();
    }

    // --- UPDATE ---

    //TODO UPDATE

    /*
    //Issue with data consistency?
    //Just pass new changes?
    // Searches through the doctors ArrayList for the doctor we need, and then update said doctor with updatedDoctor
    public void updateDoctor (String userId, Doctor updatedDoctor) {

        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getUserID() == userId) {
                doctors.set(i, updatedDoctor);
                Save();
            }
        }
    }

    // Searches through the pharmacists ArrayList for the pharmacist we need, and then update said pharmacist with updatedPharmacist
    public void updatePharmacist (String userId, Pharmacist updatedPharmacist) {

        for (int i = 0; i < pharmacists.size(); i++) {
            if (pharmacists.get(i).getUserID() == userId) {
                pharmacists.set(i, updatedPharmacist);
                Save();
            }
        }
    }
     */

    // --- DELETE ---
}
