package Service;

import Data.DataSource;
import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.util.Map;

/**
 * Service class for handling user authentication.
 * Implements the ILoginService interface and provides methods to validate credentials
 * and retrieve the role of a user.
 */
public class LoginService implements ILoginService {

    /** The DataSource instance used for retrieving user and role data. */
    private final DataSource dataSource;

    /**
     * Constructs a LoginService with the given data source.
     *
     * @param dataSource the data source to be used for retrieving user and role information.
     */
    public LoginService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieves the role of a user by their ID.
     *
     * @param userId the ID of the user whose role is to be retrieved.
     * @return the RoleType of the user, or RoleType.None if the user ID is not found.
     */
    private RoleType getRole(String userId) {
        Map<String, Role> roles = dataSource.getRoles();
        if (roles.containsKey(userId)) {
            return roles.get(userId).getRole();
        }
        return RoleType.None;
    }

    /**
     * Authenticates a user by validating their credentials.
     * If the credentials are valid, returns the user's role. Otherwise, returns RoleType.None.
     *
     * @param userId the ID of the user attempting to log in.
     * @param password the password provided by the user.
     * @return the RoleType of the authenticated user, or RoleType.None if authentication fails.
     */
    public RoleType login(String userId, String password) {
        RoleType role = getRole(userId);
        if (RoleType.None.equals(role)) {
            return RoleType.None;
        }
        Map<String, User> users = dataSource.getAllUsersWithRole(role);
        if (users.containsKey(userId) && users.get(userId).validateCredentials(userId, password)) {
            return role;
        }
        return RoleType.None;
    }
}