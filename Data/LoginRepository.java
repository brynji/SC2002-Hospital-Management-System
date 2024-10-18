package Data;

import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class LoginRepository {
    static final Map<RoleType,String> filenameByRoleType = Map.of(
            RoleType.Patient,BaseRepository.patientFile,
            RoleType.Doctor,BaseRepository.doctorsFile,
            RoleType.Pharmacist,BaseRepository.pharmacistFile,
            RoleType.Administrator,BaseRepository.adminFile
    );

    public static RoleType GetRole(String userId) throws IOException, ClassNotFoundException {
        ArrayList<Role> roles = Database.readFromFile(BaseRepository.rolesFile);
        for (Role role : roles) {
            if (userId.equals(role.getUserId())) {
                return role.getRole();
            }
        }
        return RoleType.None;
    }

    public static RoleType CheckCredentials(String userId, String password) throws IOException, ClassNotFoundException {
        RoleType role = GetRole(userId);
        if(RoleType.None.equals(role)){ return RoleType.None; }
        ArrayList<User> users = Database.readFromFile(filenameByRoleType.get(role));
        for(User user : users){
            if(user.validateCredentials(userId, password)){
                return role;
            }
        }
        return RoleType.None;
    }
}
