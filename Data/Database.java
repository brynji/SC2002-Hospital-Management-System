package Data;

import Misc.Appointment;
import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.io.*;
import java.util.*;

/**
 * Implementation of the DataSource interface.
 * This class manages the persistence and retrieval of data for roles, users, inventory, and appointments.
 * Data is stored in serialized files and loaded into memory during runtime.
 */
public class Database implements DataSource {

    /** Path to the serialized file storing inventory data. */
    private final String inventoryFile = "SavedData/Inventory.dat";

    /** Path to the serialized file storing appointments data. */
    private final String appointmentsFile = "SavedData/Appointments.dat";

    /** Path to the serialized file storing roles data. */
    private final String rolesFile = "SavedData/Roles.dat";

    /** Paths to serialized files storing user data, organized by RoleType. */
    private final String[] usersFilenames =
            Arrays.stream(RoleType.values())
                  .filter(role -> role != RoleType.None)
                  .map(role -> "SavedData/" + role.toString() + ".dat")
                  .toArray(String[]::new);

    /** Map of roles with a String key and Role object as the value. */
    private Map<String, Role> roles;

    /** List of user maps, each representing users by a specific role. */
    private ArrayList<Map<String, User>> users;

    /** Map of appointments with a String key and Appointment object as the value. */
    private Map<String, Appointment> appointments;

    /** Map of inventory data with a String key and Inventory object as the value. */
    private Map<String, Inventory> inventory;

    /**
     * Constructs a Database instance and initializes in-memory data by loading it from persistent storage.
     */
    public Database() {
        update();
    }

    /**
     * Retrieves all roles from the database.
     *
     * @return a Map where the key is a unique identifier (String) for the role,
     *         and the value is the corresponding Role object.
     */
    public Map<String, Role> getRoles() {
        return roles;
    }

    /**
     * Retrieves all appointments from the database.
     *
     * @return a Map where the key is a unique identifier (String) for the appointment,
     *         and the value is the corresponding Appointment object.
     */
    public Map<String, Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Retrieves the inventory data from the database.
     *
     * @return the Inventory object representing the current inventory data.
     */
    public Inventory getInventory() {
        return inventory.get("");
    }

    /**
     * Retrieves all users with a specific role from the database.
     *
     * @param role the RoleType of the users to retrieve.
     * @return a Map where the key is a unique identifier (String) for the user,
     *         and the value is the corresponding User object.
     */
    public Map<String, User> getAllUsersWithRole(RoleType role) {
        return users.get(role.ordinal());
    }

    /**
     * Updates in-memory data by reloading it from the serialized files.
     * This ensures that the most recent data from persistent storage is loaded into memory.
     */
    public void update() {
        try {
            roles = readFromFile(rolesFile);
            users = new ArrayList<>();
            for (RoleType role : RoleType.values()) {
                if (role != RoleType.None)
                    users.add(readFromFile(usersFilenames[role.ordinal()]));
            }
            inventory = readFromFile(inventoryFile);
            if (inventory.isEmpty()) inventory.put("", new Inventory());
            appointments = readFromFile(appointmentsFile);
        } catch (Exception e) {
            System.out.println("Error in reading files: " + e.getMessage());
        }
    }

    /**
     * Saves the current in-memory data to the serialized files.
     * This ensures that any changes made during runtime are persisted to disk.
     */
    public void save() {
        try {
            writeToFile(rolesFile, roles);
            for (RoleType role : RoleType.values()) {
                if (role != RoleType.None)
                    writeToFile(usersFilenames[role.ordinal()], users.get(role.ordinal()));
            }
            writeToFile(inventoryFile, inventory);
            writeToFile(appointmentsFile, appointments);
        } catch (Exception e) {
            System.out.println("Error in saving files: " + e.getMessage());
        }
    }

    /**
     * Reads a serialized Map from a specified file.
     *
     * @param filename the path to the file to be read.
     * @param <T>      the type of the objects stored in the Map.
     * @return a Map containing the deserialized data, or a new HashMap if the file does not exist.
     * @throws ClassNotFoundException if the serialized class does not match the expected class type.
     */
    private <T> Map<String, T> readFromFile(String filename) throws ClassNotFoundException {
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            return (Map<String, T>) in.readObject();
        } catch (IOException e) {
            return new HashMap<>();
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Saved classes don't match current classes - " + e.getMessage());
        }
    }

    /**
     * Writes a Map to a specified file by serializing the data.
     *
     * @param filename the path to the file where the data will be saved.
     * @param data     the Map containing the data to be serialized.
     * @param <T>      the type of the objects stored in the Map.
     * @throws IOException if there is an error during the writing process.
     */
    private <T> void writeToFile(String filename, Map<String, T> data) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(data);
    }
}