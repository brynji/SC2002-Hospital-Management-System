package Data;

import Misc.Role;
import Users.Doctor;
import Users.Patient;
import Users.User;

import java.util.ArrayList;

public abstract class BaseRepository<T extends User> {
    public static final String rolesFile = "SavedData/Roles.txt";
    public static final String patientFile = "SavedData/Patients.txt";
    public static final String doctorsFile = "SavedData/Doctors.txt";
    public static final String pharmacistFile = "SavedData/Pharmacists.txt";
    public static final String adminFile = "SavedData/Admins.txt";


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

    public abstract T FindById(String userId);

}
