package Data;

import Misc.Appointment;
import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.io.*;
import java.util.*;

/**
 * Implementation of our source of data
 */
public class Database implements DataSource {
    private final String inventoryFile;
    private final String appointmentsFile;
    private final String rolesFile;
    private final String[] usersFilenames;

    private Map<String, Role> roles;
    private ArrayList<Map<String, User>> users;
    private Map<String, Appointment> appointments;
    private Map<String, Inventory> inventory;

    /**
     * Reads all data from files
     * Default file path is SavedData/
     */
    public Database() {
        inventoryFile = "SavedData/Inventory.dat";
        appointmentsFile = "SavedData/Appointments.dat";
        rolesFile = "SavedData/Roles.dat";
        usersFilenames = Arrays.stream(RoleType.values()).filter(role -> role != RoleType.None)
                        .map(role -> "SavedData/" + role.toString() + ".dat").toArray(String[]::new);
        update();
    }

    /**
     * Reads all data from files
     * @param filesPath filePath to read from
     */
    public Database(String filesPath) {
        inventoryFile = filesPath+"/Inventory.dat";
        appointmentsFile = filesPath+"/Appointments.dat";
        rolesFile = filesPath+"/Roles.dat";
        usersFilenames = Arrays.stream(RoleType.values()).filter(role -> role != RoleType.None)
                .map(role -> filesPath+"/" + role.toString() + ".dat").toArray(String[]::new);
        update();
    }

    /**
     * Get roles by userId
     * @return Map of roles by userId
     */
    public Map<String, Role> getRoles() { return roles; }

    /**
     * Get appointments by appointmentId
     * @return map of appointments by appointmentId
     */
    public Map<String, Appointment> getAppointments(){ return appointments; }

    public Inventory getInventory() { return inventory.get(""); }

    /**
     * Get all users with given role
     * @param role role
     * @return Map of Users by userId
     */
    public Map<String,User> getAllUsersWithRole(RoleType role){ return users.get(role.ordinal()); }

    /**
     * Updates states of all objects with states from files
     */
    public void update(){
        try{
            roles = readFromFile(rolesFile);
            users = new ArrayList<>();
            for (RoleType role : RoleType.values()) {
                if(role != RoleType.None)
                    users.add(readFromFile(usersFilenames[role.ordinal()]));
            }
            inventory = readFromFile(inventoryFile);
            if(inventory.isEmpty()) inventory.put("",new Inventory());
            appointments = readFromFile(appointmentsFile);
        } catch(Exception e){
            System.out.println("Error in reading files: " + e.getMessage());
        }
    }

    /**
     * Saves all states of objects to files
     */
    public void save(){
        try{
            writeToFile(rolesFile,roles);
            for (RoleType role : RoleType.values()) {
                if(role != RoleType.None)
                    writeToFile(usersFilenames[role.ordinal()],users.get(role.ordinal()));
            }
            writeToFile(inventoryFile,inventory);
            writeToFile(appointmentsFile,appointments);
        } catch(Exception e){
            System.out.println("Error in saving files: " + e.getMessage());
        }
    }

    private  <T> Map<String,T> readFromFile(String filename) throws ClassNotFoundException {
        try{
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            return (Map<String,T>) in.readObject();
        } catch (IOException e){
            return new HashMap<>();
        } catch (ClassNotFoundException e){
            throw new ClassNotFoundException("Saved classes doesn't match current classes - " + e.getMessage());
        }
    }

    private  <T> void writeToFile(String filename, Map<String,T> data) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(data);
    }
}
