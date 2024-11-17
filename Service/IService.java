package Service;

import Data.BaseRepository;
import Users.User;

/**
 * Generic interface for user-specific service classes.
 * Provides common operations for managing users and accessing their repositories.
 *
 * @param <T> the type of user managed by the service (e.g., Doctor, Patient).
 * @param <U> the type of repository used by the service (e.g., DoctorRepository, PatientRepository).
 */
public interface IService<T extends User, U extends BaseRepository> {

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the current user of type T.
     */
    T getCurrentUser();

    /**
     * Sets the currently logged-in user.
     *
     * @param userId the ID of the user to set as the current user.
     */
    void setCurrentUser(String userId);

    /**
     * Retrieves the repository associated with the service.
     *
     * @return the repository of type U.
     */
    U getRepository();

    /**
     * Changes the password of the current user.
     *
     * @param newPassword the new password to set for the user.
     */
    void changePassword(String newPassword);

    /**
     * Updates the name of the current user.
     *
     * @param newName the new name to set for the user.
     */
    void updateName(String newName);

    /**
     * Updates the email address of the current user.
     *
     * @param newEmail the new email address to set for the user.
     */
    void updateEmail(String newEmail);

    /**
     * Updates the contact number of the current user.
     *
     * @param newContactNumber the new contact number to set for the user.
     */
    void updateContactNumber(String newContactNumber);
}