package Data;

import Misc.Appointment;
import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.io.*;
import java.util.*;

public class Database implements DataSource {
    private final String inventoryFile = "SavedData/Inventory.txt";
    private final String appointmentsFile = "SavedData/Appointments.txt";
    private final String rolesFile = "SavedData/Roles.txt";
    private final String[] usersFilenames =
            Arrays.stream(RoleType.values()).filter(role -> role != RoleType.None)
                    .map(role -> "SavedData/" + role.toString() + ".txt").toArray(String[]::new);

    private Map<String, Role> roles;
    private ArrayList<Map<String, User>> users;
    private Map<String, Appointment> appointments;
    private Map<String, Inventory> inventory;

    public Database() {
        update();
    }

    public Map<String, Role> getRoles() { return roles; }

    public Map<String, Appointment> getAppointments(){ return appointments; }

    public Inventory getInventory() { return inventory.get(""); }

    public Map<String,User> getAllUsersWithRole(RoleType role){ return users.get(role.ordinal()); }

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

    private  <T> Map<String,T> readFromFile(String filename) {
        try{
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            return (Map<String,T>) in.readObject();
        } catch (IOException e){
            System.out.println("🟥 DATABASE - IOException - "+e.getMessage()+" | Ignoring - creating new map for file "+filename+" 🟥");
            return new HashMap<>();
        } catch (ClassNotFoundException e){
            System.out.println("DATABASE - ClassNotFoundException - "+e.getMessage());
            return new HashMap<>();
        }
    }

    private  <T> void writeToFile(String filename, Map<String,T> data) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(data);
    }
}
