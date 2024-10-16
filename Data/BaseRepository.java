package Data;

import Misc.Role;
import Misc.RoleType;
import Users.Doctor;
import Users.Patient;
import Users.User;

import java.util.ArrayList;

public class BaseRepository {
    static final String rolesFile = "SavedData/Roles.txt";
    static final String patientFile = "SavedData/Patients.txt";
    static final String doctorsFile = "SavedData/Doctors.txt";
    static final String pharmacistFile = "SavedData/Pharmacists.txt";
    static final String adminFile = "SavedData/Admins.txt";
    static final String[] filenameByRoleType = {"",rolesFile,patientFile,doctorsFile,pharmacistFile,adminFile};

    ArrayList<Role> roles;
    ArrayList<Patient> patients;
    ArrayList<Doctor> doctors;
    //...

    public BaseRepository() {
        try{
            patients = Database.readFromFile(patientFile);
            doctors = Database.readFromFile(doctorsFile);
        } catch(Exception e){
            System.out.println("Error in reading files: " + e.getMessage());
        }
    }

    public void Save(){
        try{
            Database.writeToFile(rolesFile,roles);
            Database.writeToFile(patientFile,patients);
            Database.writeToFile(doctorsFile,doctors);
            //...
        } catch(Exception e){

        }
    }

    public static RoleType GetRole(String userId){
        try {
            ArrayList<Role> roles = Database.readFromFile(rolesFile);
            for (Role role : roles) {
                if (role.getUserId().equals(userId)) {
                    return role.getRole();
                }
            }
        } catch (Exception e){

        }
        return RoleType.None;
    }

    public static RoleType CheckCredentials(String userId, String password) {
        try{
            RoleType role = GetRole(userId);
            if(role == RoleType.None){ return RoleType.None; }
            ArrayList<User> users = Database.readFromFile(filenameByRoleType[role.ordinal()]);
            for(User user : users){
                if(user.validateCredentials(userId, password)){
                    return role;
                }
            }

        } catch (Exception e){

        }
        return RoleType.None;
    }
}
