package Service;

import Data.DataSource;
import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.util.Map;

public class LoginService implements ILoginService{
    private final DataSource dataSource;

    public LoginService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private RoleType getRole(String userId) {
        Map<String,Role> roles = dataSource.getRoles();
        if(roles.containsKey(userId)){
            return roles.get(userId).getRole();
        }
        return RoleType.None;
    }

    public RoleType login(String userId, String password) {
        RoleType role = getRole(userId);
        if(RoleType.None.equals(role)){ return RoleType.None; }
        Map<String,User> users = dataSource.getAllUsersWithRole(role);
        if(users.containsKey(userId) && users.get(userId).validateCredentials(userId,password)){
            return role;
        }
        return RoleType.None;
    }
}
