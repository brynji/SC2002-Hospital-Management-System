package Data;

import Misc.*;
import Users.*;

import java.util.ArrayList;
import java.util.Collection;

public class AdministratorRepository extends BaseRepository {
    // --- ADD ---

    public void addNew(User u){
        RoleType role = getRoleTypeFromUser(u);
        users.get(role.ordinal()).put(u.getUserID(),u);
        roles.put(u.getUserID(),new Role(u.getUserID(),role));
        save();
    }

    public void remove(User u){
        RoleType role = getRoleTypeFromUser(u);
        users.get(role.ordinal()).remove(u.getUserID());
        roles.remove(u.getUserID());
        save();
    }

    // --- GET ---

    public ArrayList<Appointment> getAllAppointments(){
        ArrayList<Appointment> appointments = new ArrayList<>();
        for(User d : getAllUsers(RoleType.Doctor)){
            appointments.addAll(((Doctor)d).getAppointments());
        }
        return appointments;
    }

    public ArrayList<User> getAllUsers(RoleType roleFilter){
        ArrayList<User> res = new ArrayList<>();
        if(roleFilter != RoleType.None){
            res.addAll(users.get(roleFilter.ordinal()).values());
        } else {
            for(var userMap : users){
                res.addAll(userMap.values());
            }
        }
        return res;
    }

    public Collection<Role> getAllRoles(){
        return roles.values();
    }

    public Inventory getInventory(){
        return inventory.get("");
    }

    // --- UPDATE ---

    // --- DELETE ---
}
