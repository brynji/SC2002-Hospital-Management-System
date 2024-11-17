package Service;

import Misc.RoleType;

/**
 * Interface for user authentication services.
 * Provides a method to validate user credentials and retrieve their roles.
 */
public interface ILoginService {

    /**
     * Authenticates a user by validating their credentials.
     * If the credentials are valid, returns the user's role. Otherwise, returns RoleType.None.
     *
     * @param userId the ID of the user attempting to log in.
     * @param password the password provided by the user.
     * @return the RoleType of the authenticated user, or RoleType.None if authentication fails.
     */
    RoleType login(String userId, String password);
}