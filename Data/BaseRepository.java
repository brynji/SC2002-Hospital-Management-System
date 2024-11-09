package Data;

import Misc.Appointment;
import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.*;

import java.util.*;

public abstract class BaseRepository{
    protected final DataSource dataSource;

    public BaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(){
        dataSource.save();
    }

    public void update(){
        dataSource.update();
    }

    public <T> T findUserById(String userId, RoleType role){
        return (T) dataSource.getAllUsersWithRole(role).get(userId);
    }

    public <T> Collection<T> getAllUsersWithRole(RoleType role){
        return dataSource.getAllUsersWithRole(role).values().stream().map(x->(T)x).toList();
    }

    public Collection<Appointment> getAllAppointmentsFromIds(Collection<String> appointmentIds) {
        var apps = dataSource.getAppointments();
        return appointmentIds.stream().map(apps::get).toList();
    }

    protected RoleType getRoleTypeFromUser(User user) throws IllegalArgumentException{
        return switch (user) {
            case Patient _ -> RoleType.Patient;
            case Doctor _ -> RoleType.Doctor;
            case Pharmacist _ -> RoleType.Pharmacist;
            case Administrator _ -> RoleType.Administrator;
            default -> throw new IllegalArgumentException("User doesnt have any role");
        };
    }
}
