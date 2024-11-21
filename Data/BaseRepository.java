package Data;

import Misc.Appointment;
import Misc.RoleType;
import Users.*;

import java.util.*;

/**
 * Abstract base class for repository operations.
 * Provides utility methods for managing users, appointments, and IDs.
 */
public abstract class BaseRepository {

    /** The data source used for accessing and managing persistent data. */
    protected final DataSource dataSource;

    /**
     * Constructs a BaseRepository instance with the given data source.
     *
     * @param dataSource the data source to be used for data operations.
     */
    public BaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Saves the current in-memory data to persistent storage.
     * Delegates to the save method of the DataSource.
     */
    public void save() {
        dataSource.save();
    }

    /**
     * Updates the in-memory data by reloading it from persistent storage.
     * Delegates to the update method of the DataSource.
     */
    public void update() {
        dataSource.update();
    }

    /**
     * Finds a user by their ID and role type.
     *
     * @param userId the unique identifier of the user.
     * @param role   the role type of the user.
     * @param <T>    the type of the user object being returned.
     * @return the user object corresponding to the given ID and role, or null if not found.
     */
    @SuppressWarnings("unchecked")
    public <T> T findUserById(String userId, RoleType role) {
        return (T) dataSource.getAllUsersWithRole(role).get(userId);
    }

    /**
     * Retrieves all users with a specific role.
     *
     * @param role the role type of the users to retrieve.
     * @param <T>  the type of the user objects being returned.
     * @return a collection of all users with the specified role.
     */
    @SuppressWarnings("unchecked")
    public <T> Collection<T> getAllUsersWithRole(RoleType role) {
        return dataSource.getAllUsersWithRole(role).values().stream().map(x -> (T) x).toList();
    }

    /**
     * Retrieves a collection of appointments based on their IDs.
     *
     * @param appointmentIds the collection of appointment IDs to retrieve.
     * @return a collection of appointments corresponding to the provided IDs.
     */
    public Collection<Appointment> getAllAppointmentsFromIds(Collection<String> appointmentIds) {
        var apps = dataSource.getAppointments();
        return appointmentIds.stream().map(apps::get).toList();
    }

    /**
     * Determines the RoleType of a given user.
     *
     * @param user the user whose role type is to be determined.
     * @return the RoleType corresponding to the given user, or RoleType.None if not matched.
     */
    public RoleType getRoleTypeFromUser(User user) {
        return switch (user) {
            case Patient _ -> RoleType.Patient;
            case Doctor _ -> RoleType.Doctor;
            case Pharmacist _ -> RoleType.Pharmacist;
            case Administrator _ -> RoleType.Administrator;
            default -> RoleType.None;
        };
    }

    /**
     * Generates a random ID in the format `Abcd-Wxyz-1234-5678`.
     *
     * @return the generated unique ID.
     */
    public String generateID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        // Generate first segment (4 random letters, mixed case)
        for (int i = 0; i < 4; i++) {
            char letter = (char) (random.nextBoolean() ?
                    ('A' + random.nextInt(26)) : // Uppercase
                    ('a' + random.nextInt(26))); // Lowercase
            id.append(letter);
        }
        id.append("-");

        // Generate second segment (4 random letters, mixed case)
        for (int i = 0; i < 4; i++) {
            char letter = (char) (random.nextBoolean() ?
                    ('A' + random.nextInt(26)) :
                    ('a' + random.nextInt(26)));
            id.append(letter);
        }
        id.append("-");

        // Generate third and fourth segments (4 random digits each)
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                int digit = random.nextInt(10);
                id.append(digit);
            }
            if (j == 0) id.append("-");
        }

        return id.toString();
    }
}