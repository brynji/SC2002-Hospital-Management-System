package Data;

import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.*;

import java.util.*;

public abstract class BaseRepository{
    public static final String inventoryFile = "SavedData/Inventory.txt";
    public static final String rolesFile = "SavedData/Roles.txt";
    public static final String[] usersFilenames =
            Arrays.stream(RoleType.values()).filter(role -> role != RoleType.None)
            .map(role -> "SavedData/" + role.toString() + ".txt").toArray(String[]::new);

    Map<String,Role> roles;
    ArrayList<Map<String,User>> users;
    Map<String,Inventory> inventory;

    public BaseRepository() {
        update();
    }

    public void save(){
        try{
            Database.writeToFile(rolesFile,roles);
            for (RoleType role : RoleType.values()) {
                if(role != RoleType.None)
                    Database.writeToFile(usersFilenames[role.ordinal()],users.get(role.ordinal()));
            }
            Database.writeToFile(inventoryFile,inventory);
        } catch(Exception e){
            System.out.println("Error in saving files: " + e.getMessage());
        }
    }

    public void update(){
        try{
            roles = Database.readFromFile(rolesFile);
            users = new ArrayList<>();
            for (RoleType role : RoleType.values()) {
                if(role != RoleType.None)
                    users.add(Database.readFromFile(usersFilenames[role.ordinal()]));
            }
            inventory = Database.readFromFile(inventoryFile);
        } catch(Exception e){
            System.out.println("Error in reading files: " + e.getMessage());
        }
    }


    public <T> T findUserById(String userId, RoleType role){
        return (T) users.get(role.ordinal()).get(userId);
    }

    public Collection<User> getAllUsers(RoleType role){
        return users.get(role.ordinal()).values();
    }

    protected RoleType getRoleTypeFromUser(User user){
        return switch (user) {
            case Patient _ -> RoleType.Patient;
            case Doctor _ -> RoleType.Doctor;
            case Pharmacist _ -> RoleType.Pharmacist;
            case Administrator _ -> RoleType.Administrator;
            default -> throw new IllegalArgumentException("User doesnt have any role");
        };
    }
}
