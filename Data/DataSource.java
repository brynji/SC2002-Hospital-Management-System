package Data;

import Misc.Appointment;
import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.util.Map;

/**
 * Interface defining the structure for a data source in the system.
 * Provides methods for managing roles, appointments, inventory, and users.
 */
public interface DataSource {

    /**
     * Retrieves all the roles from the data source.
     *
     * @return a Map where the key is a unique identifier (String) for the role,
     *         and the value is the corresponding Role object.
     */
    Map<String, Role> getRoles();

    /**
     * Retrieves all the appointments from the data source.
     *
     * @return a Map where the key is a unique identifier (String) for the appointment,
     *         and the value is the corresponding Appointment object.
     */
    Map<String, Appointment> getAppointments();

    /**
     * Retrieves the inventory from the data source.
     *
     * @return the Inventory object representing the current inventory data.
     */
    Inventory getInventory();

    /**
     * Retrieves all users with a specific role from the data source.
     *
     * @param role the RoleType of the users to retrieve.
     * @return a Map where the key is a unique identifier (String) for the user,
     *         and the value is the corresponding User object.
     */
    Map<String, User> getAllUsersWithRole(RoleType role);

    /**
     * Updates the in-memory data by reloading it from the persistent storage.
     * This ensures that the most recent data from the underlying files is reflected in memory.
     */
    void update();

    /**
     * Saves the current in-memory data to the persistent storage.
     * This method ensures that changes made during runtime are saved to the underlying files.
     */
    void save();
}
