package Data;

import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.util.ArrayList;

public class LoginRepository {
    static final String[] filenameByRoleType = {"",BaseRepository.rolesFile,BaseRepository.patientFile,
            BaseRepository.doctorsFile,BaseRepository.pharmacistFile,BaseRepository.adminFile};

    public static RoleType GetRole(String userId){
        try {
            ArrayList<Role> roles = Database.readFromFile(BaseRepository.rolesFile);
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
