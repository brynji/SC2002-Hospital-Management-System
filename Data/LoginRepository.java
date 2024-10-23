package Data;

import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.io.IOException;
import java.util.Map;

public class LoginRepository {
    public static RoleType GetRole(String userId) throws ClassNotFoundException {
        Map<String,Role> roles = Database.readFromFile(BaseRepository.rolesFile);
        if(roles.containsKey(userId)){
            return roles.get(userId).getRole();
        }
        return RoleType.None;
    }

    public static RoleType CheckCredentials(String userId, String password) throws IOException, ClassNotFoundException {
        RoleType role = GetRole(userId);
        if(RoleType.None.equals(role)){ return RoleType.None; }
        Map<String,User> users = Database.readFromFile(BaseRepository.usersFilenames[role.ordinal()]);
        if(users.containsKey(userId) && users.get(userId).validateCredentials(userId,password)){
            return role;
        }
        return RoleType.None;
    }
}
