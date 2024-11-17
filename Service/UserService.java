package Service;

import Data.BaseRepository;
import Users.User;

/**
 * Abstract base class for user-specific services.
 * Provides common operations for managing user information and integrates with a repository for data persistence.
 *
 * @param <T> the type of user managed by the service (e.g., Doctor, Patient).
 * @param <U> the type of repository used by the service (e.g., DoctorRepository, PatientRepository).
 */
public abstract class UserService<T extends User, U extends BaseRepository> implements IService<T, U> {

    /**
     * Changes the current user's password and saves the updated data.
     *
     * @param newPassword the new password to set for the user.
     */
    public void changePassword(String newPassword) {
        getCurrentUser().setPassword(newPassword);
        getRepository().save();
    }

    /**
     * Updates the current user's name and saves the updated data.
     *
     * @param newName the new name to set for the user.
     */
    public void updateName(String newName) {
        getCurrentUser().setName(newName);
        getRepository().save();
    }

    /**
     * Updates the current user's email and saves the updated data.
     *
     * @param newEmail the new email address to set for the user.
     */
    public void updateEmail(String newEmail) {
        getCurrentUser().setEmail(newEmail);
        getRepository().save();
    }

    /**
     * Updates the current user's contact number and saves the updated data.
     *
     * @param newContactNumber the new contact number to set for the user.
     */
    public void updateContactNumber(String newContactNumber) {
        getCurrentUser().setContactNumber(newContactNumber);
        getRepository().save();
    }

    /**
     * Retrieves the current user's email address.
     *
     * @return the email address of the current user.
     */
    public String getEmail() {
        return getCurrentUser().getEmail();
    }

    /**
     * Retrieves the current user's contact number.
     *
     * @return the contact number of the current user.
     */
    public String getContactNumber() {
        return getCurrentUser().getContactNumber();
    }

    /**
     * Abstract method to retrieve the currently logged-in user.
     * Must be implemented by subclasses.
     *
     * @return the current user of type T.
     */
    public abstract T getCurrentUser();

    /**
     * Abstract method to retrieve the repository associated with the user type.
     * Must be implemented by subclasses.
     *
     * @return the repository of type U.
     */
    public abstract U getRepository();
}