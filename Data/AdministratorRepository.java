package Data;

import Misc.*;
import Users.*;

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

    // --- ADD ---

    public void addNew(User u){
        RoleType role = getRoleTypeFromUser(u);
        dataSource.getAllUsersWithRole(role).put(u.getUserID(),u);
        dataSource.getRoles().put(u.getUserID(),new Role(u.getUserID(),role));
    }

    public void remove(String userId){
        //TODO FIX REMOVING - NULL REFERENCE EXCEPTIONS AFTER REMOVING USER BUT KEEPING HIS IDS IN THE APPOINTMENTS ETC.
        RoleType role = dataSource.getRoles().get(userId).getRole();
        dataSource.getAllUsersWithRole(role).remove(userId);
        dataSource.getRoles().remove(userId);
    }

    // --- GET ---

    public Collection<Appointment> getAllAppointments(){
        return dataSource.getAppointments().values();
    }

    public Map<String,Role> getAllRoles(){
        return dataSource.getRoles();
    }

    public Inventory getInventory(){
        return dataSource.getInventory();
    }

    // --- UPDATE ---


    // --- DELETE ---
}
