package Data;

import Misc.Appointment;
import Misc.RoleType;
import Users.*;

import java.util.*;

/**
 * Base class for all repositories, contains commonly used methods
 */
public abstract class BaseRepository{
    protected final DataSource dataSource;

    /**
     * Source of data
     * @param dataSource any implementation of DataSource
     */
    public BaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Saves current state of all objects
     */
    public void save(){
        dataSource.save();
    }

    /**
     * Updates state of all objects with state that is saved
     */
    public void update(){
        dataSource.update();
    }

    /**
     * Used to get User classes
     * @param userId userID to find
     * @param role expected role of user
     * @return User class that matches userId and role
     * @param <T> expected user Class
     */
    public <T> T findUserById(String userId, RoleType role){
        return (T) dataSource.getAllUsersWithRole(role).get(userId);
    }

    /**
     * Get all users with given role
     * @param role role
     * @return Collection of all Users with given role
     * @param <T> expected user Class
     */
    public <T> Collection<T> getAllUsersWithRole(RoleType role){
        return dataSource.getAllUsersWithRole(role).values().stream().map(x->(T)x).toList();
    }

    /**
     * Get Collection of appointments with given ids
     * @param appointmentIds ids to find
     * @return Collection of appointments with given ids
     */
    public Collection<Appointment> getAllAppointmentsFromIds(Collection<String> appointmentIds) {
        var apps = dataSource.getAppointments();
        return appointmentIds.stream().map(apps::get).toList();
    }

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
     * Generates random ID, doesn't check if it is unique
     * @return random ID
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
