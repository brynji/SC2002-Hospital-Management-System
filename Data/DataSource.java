package Data;
import Misc.Appointment;
import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.User;
import java.util.Map;

public interface DataSource {
    Map<String, Role> getRoles();
    Map<String, Appointment> getAppointments();
    Inventory getInventory();
    Map<String, User> getAllUsersWithRole(RoleType role);
    void update();
    void save();
}
